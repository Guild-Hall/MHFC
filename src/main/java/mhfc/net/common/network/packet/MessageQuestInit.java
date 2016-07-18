package mhfc.net.common.network.packet;

import java.io.IOException;
import java.util.Objects;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.directors.DirectorDownloadQuests;
import mhfc.net.common.core.directors.DirectorUploadQuests;

public class MessageQuestInit implements IMessage {

	protected QuestDescriptionRegistryData data; // Used by upload
	protected DirectorDownloadQuests downloader; // Used by download

	public MessageQuestInit() {
		data = new QuestDescriptionRegistryData();
	}

	public MessageQuestInit(QuestDescriptionRegistryData dataObject) {
		this.data = Objects.requireNonNull(dataObject);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		buf.retain();
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			downloader = new DirectorDownloadQuests(in);
		} catch (IOException e) {
			Throwables.propagate(e);
		}
		buf.release();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.retain();
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			DirectorUploadQuests uploader = new DirectorUploadQuests(out);
			uploader.construct(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.release();
	}

	public void initialize(QuestDescriptionRegistryData data) {
		downloader.construct(data);
	}
}
