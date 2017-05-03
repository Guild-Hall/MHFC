package mhfc.net.common.worldedit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.jnbt.NBTOutputStream;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;

public enum ExtendedSchematic implements IClipboardFormat {
	INSTANCE {
		@Override
		public ClipboardReader getReader(InputStream inputStream) throws IOException {
			NBTInputStream nbtis = new NBTInputStream(new GZIPInputStream(inputStream));
			return new PortableSchematicReader(nbtis, BlockIdMappingTable.createForgeMappingTable());
		}

		@Override
		public ClipboardWriter getWriter(OutputStream outputStream) throws IOException {
			NBTOutputStream nbtos = new NBTOutputStream(new GZIPOutputStream(outputStream));
			return new PortableSchematicWriter(nbtos, BlockIdMappingTable.createForgeMappingTable());
		}
	}
}
