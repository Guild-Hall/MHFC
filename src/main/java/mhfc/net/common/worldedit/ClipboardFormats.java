package mhfc.net.common.worldedit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.jnbt.NBTOutputStream;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;

public enum ClipboardFormats implements IClipboardFormat {
	EXTENDED_FORGE_SCHEMATIC {
		@SuppressWarnings("resource")
		@Override
		public ClipboardReader getReader(InputStream inputStream) throws IOException {
			final NBTInputStream nbtis = new NBTInputStream(new GZIPInputStream(inputStream));
			return new PortableSchematicReader(nbtis, BlockIdMappingTable.createForgeMappingTable());
		}

		@SuppressWarnings("resource")
		@Override
		public ClipboardWriter getWriter(OutputStream outputStream) throws IOException {
			final NBTOutputStream nbtos = new NBTOutputStream(new GZIPOutputStream(outputStream));
			return new PortableSchematicWriter(nbtos, BlockIdMappingTable.createForgeMappingTable());
		}
	},
	NATIVE_SCHEMATIC {
		@Override
		public ClipboardReader getReader(InputStream inputStream) throws IOException {
			return BuiltInClipboardFormat.MCEDIT_SCHEMATIC.getReader(inputStream);
		}

		@Override
		public ClipboardWriter getWriter(OutputStream outputStream) throws IOException {
			return BuiltInClipboardFormat.MCEDIT_SCHEMATIC.getWriter(outputStream);
		}
	};
}
