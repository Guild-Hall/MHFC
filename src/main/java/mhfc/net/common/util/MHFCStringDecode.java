package mhfc.net.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.DelayedJob;
import mhfc.net.common.eventhandler.MHFCJobHandler;
import net.minecraft.util.StatCollector;

public class MHFCStringDecode {
	public static class TimeTickDecoderFactory implements StringDecoderFactory {

		@Override
		public StringDecoder getStringDecoder(DynamicString dynString, String key) {
			return new TimeTickDecoder();
		}

	}

	public static class LocalizationDecoderFactory implements StringDecoderFactory {

		@Override
		public StringDecoder getStringDecoder(DynamicString dynString, String key) {
			return new LocalizationDecoder();
		}

	}

	public interface StringDecoderFactory {

		StringDecoder getStringDecoder(DynamicString dynString, String key);

	}

	public static interface StringDecoder {
		public int getUpdateDelay();

		public String getDecoded(String key, String value);
	}

	public static class TimeTickDecoder implements StringDecoder {

		private int ticksPassed;

		@Override
		public String getDecoded(String key, String value) {
			if (value == null) {
				return null;
			}
			long delta = Long.parseLong(value) - ticksPassed;
			delta /= MHFCJobHandler.ticksPerSecond;
			String ret = "" + (delta >= 3600 ? delta / 3600 + "h " : "")
					+ (delta >= 60 ? (delta % 3600) / 60 + "min " : "") + (delta >= 0 ? delta % 60 : delta) + "s";
			ticksPassed += getUpdateDelay();
			return ret;
		}

		@Override
		public int getUpdateDelay() {
			return MHFCJobHandler.ticksPerSecond;
		}

	}

	public static class LocalizationDecoder implements StringDecoder {

		@Override
		public int getUpdateDelay() {
			return -1;
		}

		@Override
		public String getDecoded(String key, String value) {
			if (value == null) {
				return null;
			}
			return StatCollector.translateToLocal(value);
		}

	}

	public static interface StringElement {
		public String stringValue();

		public void remove();
	}

	public static class CompositeString implements StringElement {
		protected CompositeString parent;
		protected List<StringElement> parts;

		public CompositeString(String toBreak) {
			parts = new LinkedList<>();
			parts.addAll(breakApart(toBreak));
			for (StringElement s : parts) {
				if (s instanceof CompositeString) {
					((CompositeString) s).setParent(this);
				}
			}
		}

		public void setParent(CompositeString s) {
			this.parent = s;
		}

		@Override
		public String stringValue() {
			return decode(parts);
		}

		@Override
		public void remove() {
			for (StringElement e : parts) {
				e.remove();
			}
		}

		public void childUpdated(CompositeString dynamicString) {

		}

	}

	public static class StaticString implements StringElement {

		public StaticString(String str) {
			this.str = str;
		}

		private String str;

		@Override
		public String stringValue() {
			return this.str;
		}

		@Override
		public void remove() {}
	}

	public static class DynamicString extends CompositeString implements DelayedJob {

		protected Map<String, StringDecoder> personalDecoderMap;
		protected String stringValue;
		protected int delay;

		public DynamicString(String str) {
			super(str);
			personalDecoderMap = new HashMap<>();
			executeJob(Phase.START);
		}

		@Override
		public void executeJob(Phase tickPhase) {
			if (tickPhase != Phase.START) {
				return;
			}
			String superValue = super.stringValue();
			String[] split = superValue.split(":", 2);
			if (split.length == 1) {
				this.stringValue = split[0];
				delay = -1;
			} else {
				stringValue = findReplacement(superValue);
				delay = personalDecoderMap.get(split[0]).getUpdateDelay();
			}
			if (this.delay >= 0) {
				MHFCJobHandler.instance().insert(this, getInitialDelay());
			}
			if (parent != null) {
				parent.childUpdated(this);
			}
		}

		public int getInitialDelay() {
			return delay;
		}

		protected String findReplacement(String descriptor) {
			String split[] = descriptor.split(":", 2);
			String identifier = split[0];
			if (split.length == 1) {
				return identifier;
			}
			if (!personalDecoderMap.containsKey(identifier)) {
				personalDecoderMap.put(identifier, MHFCStringDecode.getNewDecoderFor(this, identifier));
			}
			StringDecoder decoder = personalDecoderMap.get(identifier);
			if (decoder == null) {
				return "No decoder for " + identifier;
			}
			String replacement = decoder.getDecoded(identifier, split[1]);
			return replacement == null ? "unknown Descriptor " + descriptor : replacement;
		}

		@Override
		public String stringValue() {
			if (stringValue == null) {
				MHFCMain.logger().debug("String of dynamic string %s ended up as null", this.toString());
			}
			return stringValue;
		}

		@Override
		public void remove() {
			MHFCJobHandler.instance().remove(this);
		}

		@Override
		public void childUpdated(CompositeString dynamicString) {
			MHFCJobHandler.instance().remove(this);
			executeJob(Phase.START);
		}

	}

	private static Map<String, StringDecoderFactory> stringDecoderMap;

	static {
		stringDecoderMap = new HashMap<>();
		registerDecoder("time", new TimeTickDecoderFactory());
		registerDecoder("unlocalized", new LocalizationDecoderFactory());
	}

	public static void init() {}

	public static boolean registerDecoder(String key, StringDecoderFactory decoder) {
		if (stringDecoderMap.containsKey(key)) {
			return false;
		}
		stringDecoderMap.put(key, decoder);
		return true;
	}

	/**
	 * Removes all mappings to a decoder
	 *
	 * @param decoder
	 * @return If something was removed
	 */
	public static boolean removeDecoder(StringDecoderFactory decoder) {
		if (!stringDecoderMap.containsValue(decoder)) {
			return false;
		}
		List<String> toRemove = new LinkedList<>();
		for (String s : stringDecoderMap.keySet()) {
			if (stringDecoderMap.get(s) == null) {
				toRemove.add(s);
			}
		}
		for (String s : toRemove) {
			stringDecoderMap.remove(s);
		}
		return !toRemove.isEmpty();
	}

	/**
	 * Removes the string decoder mapping for a key
	 *
	 * @param key
	 * @return If something was removed
	 */
	public static boolean removeKey(String key) {
		if (stringDecoderMap.containsKey(key)) {
			stringDecoderMap.remove(key);
			return true;
		}
		return false;
	}

	public static StringDecoder getNewDecoderFor(DynamicString dynString, String key) {
		return stringDecoderMap.get(key).getStringDecoder(dynString, key);
	}

	private static List<StringElement> breakApart(String str) {
		List<StringElement> list = new ArrayList<>(20);
		if (str == null) {
			return list;
		}
		boolean dynamic = true;
		String firstSplit[] = str.split("\\{");
		List<String> secondSplit = new ArrayList<>();
		// "foo{{st}other}bar{irr}" turns to foo {st}other bar irr
		// Algorithm?
		for (String part : firstSplit) {
			for (String small : part.split("\\}")) {
				secondSplit.add(small);
			}
		}
		for (String part : secondSplit) {
			dynamic = !dynamic;
			if (part.equals("")) {
				continue;
			}
			if (dynamic) {
				CompositeString s = getCompositeFor(part);
				list.add(s);
			} else {
				list.add(new StaticString(part));
			}
		}
		return list;
	}

	private static CompositeString getCompositeFor(String part) {
		return new DynamicString(part);
	}

	private static String decode(List<StringElement> elements) {
		if (elements == null) {
			return "NETD";
		}
		String output = "";
		for (int i = 0; i < elements.size(); i++) {
			StringElement e = elements.get(i);
			if (e != null) {
				output += e.stringValue();
			}
		}
		return output;
	}

}
