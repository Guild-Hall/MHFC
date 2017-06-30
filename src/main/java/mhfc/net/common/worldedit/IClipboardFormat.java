package mhfc.net.common.worldedit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;

public interface IClipboardFormat {

	ClipboardReader getReader(InputStream inputStream) throws IOException;

	ClipboardWriter getWriter(OutputStream outputStream) throws IOException;

}
