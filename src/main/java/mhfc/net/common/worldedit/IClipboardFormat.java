package mhfc.net.common.worldedit;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IClipboardFormat {

	ClipboardReader getReader(InputStream inputStream) throws IOException;

	ClipboardWriter getWriter(OutputStream outputStream) throws IOException;

}
