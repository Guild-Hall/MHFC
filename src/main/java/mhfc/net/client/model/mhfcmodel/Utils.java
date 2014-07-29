package mhfc.net.client.model.mhfcmodel;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_ATTACHED_SHADERS;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_SHADER_TYPE;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetAttachedShaders;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.util.Arrays;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.lwjgl.opengl.GL20;

import sun.nio.cs.ArrayDecoder;

/**
 * Utility class that has static methods to help loading/encoding, etc.
 *
 * @author WorldSEnder
 *
 */
public class Utils {
	private static CharsetDecoder utf8Decoder = Charset.forName("UTF-8")
			.newDecoder().onMalformedInput(CodingErrorAction.REPORT)
			.onUnmappableCharacter(CodingErrorAction.REPORT);
	/**
	 * Helper method for extending classes to read a null-terminated String in
	 * the BMP-unicode plane. THIS MEANS ONLY UNICODE U+0001 - U+FFFF are
	 * supported.<br>
	 * All characters from U+10000 must be present as surrogate characters
	 * ranging from U+D800 - U+DFFF.<br>
	 * Refer to the Charset.forName("UTF-8")<br>
	 * Decodes the next bytes to a {@link String}. The bytes are interpreted as
	 * valid UTF-8 data. Bytes are read until a (null)-byte occurs (read
	 * inclusively) or the EOF is found.
	 *
	 * @see
	 * @param dis
	 *            the {@link DataInputStream} to read from
	 * @return the read {@link String}
	 * @throws MalformedInputException
	 *             when a byte sequence can't be decoded
	 * @throws UnmappableCharacterException
	 *             when a byte sequence can't be mapped to a character
	 * @throws IOException
	 *             if an IOError occurs that is not an {@link EOFException}
	 */
	public static String readString(DataInputStream dis)
			throws MalformedInputException, UnmappableCharacterException,
			IOException {
		// Read bytes
		byte[] buffer = new byte[64];
		int currLength = 64;
		int offset = 0;
		for (int currByte; (currByte = dis.read()) > 0;) {
			if (offset == currLength) {
				currLength += 64;
				buffer = Arrays.copyOf(buffer, currLength);
			}
			buffer[offset++] = (byte) currByte; // Fill and increase
		}
		// filledBuffer.length == offset
		// Return empty string
		if (offset == 0)
			return "";
		// Could use String(byte[], Charset) here but want some control over
		// fail-actions
		// Prepare
		utf8Decoder.reset();
		int strLen = 0;
		int maxLen = (int) Math.ceil(offset * utf8Decoder.maxCharsPerByte());
		char[] target = new char[maxLen];
		if (utf8Decoder instanceof ArrayDecoder) {
			// If array possible
			strLen = ((ArrayDecoder) utf8Decoder).decode(buffer, 0, offset,
					target);
		} else {
			// Wrap in buffer
			ByteBuffer sourceBuff = ByteBuffer.wrap(buffer, 0, offset);
			CharBuffer targetBuff = CharBuffer.wrap(target);
			// Standard decoder procedure
			try {
				CoderResult cr = utf8Decoder.decode(sourceBuff, targetBuff,
						true);
				if (!cr.isUnderflow())
					cr.throwException();
				cr = utf8Decoder.flush(targetBuff);
				if (!cr.isUnderflow())
					cr.throwException();
			} catch (CharacterCodingException x) {} // Shouldn't happen
			strLen = targetBuff.position();
		}
		return new String(target, 0, strLen);
	}

	/**
	 * Reads a {@link Vector2f} from the datainput
	 *
	 * @param dis
	 *            the {@link DataInputStream} to read from
	 * @return the constructed {@link Vector3f}
	 * @throws EOFException
	 *             when the data ends before 3 floats are read
	 * @throws IOException
	 *             when some IOException occurs in the given
	 *             {@link DataInputStream}
	 */
	public static Vector2f readVector2f(DataInputStream dis)
			throws EOFException, IOException {
		float x = dis.readFloat();
		float y = dis.readFloat();
		return new Vector2f(x, y);
	}

	/**
	 * Reads a {@link Vector3f} from the datainput
	 *
	 * @param dis
	 *            the {@link DataInputStream} to read from
	 * @return the constructed {@link Vector3f}
	 * @throws EOFException
	 *             when the data ends before 3 floats are read
	 * @throws IOException
	 *             when some IOException occurs in the given
	 *             {@link DataInputStream}
	 */
	public static Vector3f readVector3f(DataInputStream dis)
			throws EOFException, IOException {
		float x = dis.readFloat();
		float y = dis.readFloat();
		float z = dis.readFloat();
		return new Vector3f(x, y, z);
	}

	/**
	 * Reads a {@link Matrix4f} from the given {@link DataInputStream}. A
	 * matrix4 is basically 4 {@link Vector4f} so 16 floats will be read.<br>
	 * When isAffine is specified the matrix's last <b>row</b> will be assumed
	 * to be (0, 0, 0, 1). In this case only 12 floats will be read.<br>
	 * When rowsFirst is set the read Vectors will be taken as the rows of the
	 * matrix else as the matrix's columns.
	 *
	 * @param dis
	 *            the {@link DataInputStream} to read from
	 * @param isAffine
	 *            whether the Matrix to be read is affine
	 * @param rowsFirst
	 *            whether to interpret the read {@link Vector4f} as the rows or
	 *            columns of the matrix
	 * @return the newly constructed matrix
	 * @throws EOFException
	 *             when the data ends before 12/16 floats are read
	 * @throws IOException
	 *             when some IOException occurs in the given
	 *             {@link DataInputStream}
	 */
	public static Matrix4f readMatrix(DataInputStream dis, boolean isAffine,
			boolean rowsFirst) throws EOFException, IOException {
		float v0 = dis.readFloat();
		float v1 = dis.readFloat();
		float v2 = dis.readFloat();
		float v3 = dis.readFloat();
		float v4 = dis.readFloat();
		float v5 = dis.readFloat();
		float v6 = dis.readFloat();
		float v7 = dis.readFloat();
		float v8 = dis.readFloat();
		float v9 = dis.readFloat();
		float v10 = dis.readFloat();
		float v11 = dis.readFloat();
		if (isAffine) {
			if (rowsFirst) {
				// Matrix is rowsFirst
				return new Matrix4f(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9,
						v10, v11, 0.0f, 0.0f, 0.0f, 1.0f);
			}
			return new Matrix4f(v0, v3, v6, v9, v1, v4, v7, v10, v2, v5, v8,
					v11, 0.0f, 0.0f, 0.0f, 1.0f);
		}
		float v12 = dis.readFloat();
		float v13 = dis.readFloat();
		float v14 = dis.readFloat();
		float v15 = dis.readFloat();
		if (rowsFirst) {
			// Matrix is rowsFirst
			return new Matrix4f(v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, v10,
					v11, v12, v13, v14, v15);
		}
		return new Matrix4f(v0, v4, v8, v12, v1, v5, v9, v13, v2, v6, v10, v14,
				v3, v7, v11, v15);
	}

	/**
	 * Reads a {@link Quat4f} from the given {@link InputStream}.
	 *
	 * @param dis
	 *            the {@link DataInputStream} to read from
	 * @throws EOFException
	 *             when the data ends before 4 floats are read
	 * @throws IOException
	 *             when some IOException occurs in the given
	 *             {@link DataInputStream}
	 */
	public static Quat4f readQuat(DataInputStream dis) throws EOFException,
			IOException {
		float x = dis.readFloat();
		float y = dis.readFloat();
		float z = dis.readFloat();
		float w = dis.readFloat();
		return new Quat4f(x, y, z, w);
	}
	/**
	 * Asks for a new directly backed ByteBuffer
	 *
	 * @param size
	 *            the number of buffer elements (bytes)
	 * @return a new direct byte buffer
	 */
	public static ByteBuffer directByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size);
	}
	/**
	 * Asks for a new directly backed IntBuffer
	 *
	 * @param size
	 *            the number of buffer elements (ints)
	 * @return a new direct int buffer
	 */
	public static IntBuffer directIntBuffer(int size) {
		if (size > Integer.MAX_VALUE / 4)
			throw new IllegalStateException("Asked for a too big ByteBuffer");
		return ByteBuffer.allocateDirect(size * 4).asIntBuffer();
	}
	/**
	 * Asks for a new directly backed FloatBuffer
	 *
	 * @param size
	 *            the number of buffer elements (floats)
	 * @return a new direct float buffer
	 */
	public static FloatBuffer directFloatBuffer(int size) {
		if (size > Integer.MAX_VALUE / 4)
			throw new IllegalStateException("Asked for a too big ByteBuffer");
		return ByteBuffer.allocateDirect(size * 4).asFloatBuffer();
	}
	/**
	 * Asks for a new directly backed DoubleBuffer
	 *
	 * @param size
	 *            the number of buffer elements (floats)
	 * @return a new direct float buffer
	 */
	public static DoubleBuffer directDoubleBuffer(int size) {
		if (size > Integer.MAX_VALUE / 4)
			throw new IllegalStateException("Asked for a too big ByteBuffer");
		return ByteBuffer.allocateDirect(size * 4).asDoubleBuffer();
	}
	/**
	 * Gets the source in the {@link InputStream} as a direct byte buffer which
	 * can be supplied to OpenGL in {@link GL20#glShaderSource(int, ByteBuffer)}
	 * to compile a shader
	 *
	 * @param is
	 *            the {@link InputStream} to read the shader source from
	 * @return a direct {@link ByteBuffer} containing the source
	 * @throws IOException
	 */
	public static ByteBuffer getShaderSource(InputStream is) throws IOException {
		byte[] buffer = new byte[4096];
		int bytesRead, bufferSize = 0;
		ByteBuffer target = directByteBuffer(0);
		while ((bytesRead = is.read(buffer)) > 0) {
			bufferSize += bytesRead;
			ByteBuffer newTarget = directByteBuffer(bufferSize);
			newTarget.put(target);
			newTarget.put(buffer, 0, bytesRead);
			newTarget.rewind();
			target = newTarget;
		}
		return target;
	}
	/**
	 * Compiles a new shader of tpye shaderTpye using the {@link InputStream} as
	 * shader source and returns the name of the newly created shader.<br>
	 * Returning 0 means that no shader could be created.
	 *
	 * @param shaderType
	 *            the GL_TYPE of the shader like {@link GL20#GL_VERTEX_SHADER}
	 * @param is
	 *            the {@link InputStream} to read the shader source from
	 * @return the shadername
	 * @throws IOException
	 * @throws IllegalStateException
	 *             when compiling of the shader fails.
	 */
	public static int compileShaderSafe(int shaderType, InputStream is)
			throws IOException {
		int shaderName = glCreateShader(shaderType);
		if (shaderName == 0) {
			throw new IllegalStateException(
					"GL Error: Created Shader is 0. Can't proceed.");
		}
		ByteBuffer shaderSource = getShaderSource(is);
		glShaderSource(shaderName, shaderSource);
		glCompileShader(shaderName);
		if (glGetShaderi(shaderName, GL_COMPILE_STATUS) == GL_FALSE) {
			String error = glGetShaderInfoLog(shaderName, 1024);
			glDeleteShader(shaderName); // Delete first as it will be out of
										// scope
			throw new IllegalStateException(error);
		}
		return shaderName;
	}
	/**
	 * Creates a new GLprogram and returns it's name
	 *
	 * @return the new program's name
	 * @throws IllegalStateException
	 *             when no program could be created
	 */
	public static int createProgramSafe() {
		int programName = glCreateProgram();
		if (programName == 0) {
			throw new IllegalStateException(
					"GL Error: Created Program is 0. Can't proceed.");
		}
		return programName;
	}
	/**
	 * Searches for a shader with the specified type on the given program
	 * object. Returns 0 when no such shader could be found
	 *
	 * @param program
	 *            the programName to search
	 * @param searchedType
	 *            the GL-type-constant to search for
	 * @return the found shader
	 */
	public static int getShader(int program, int searchedType) {
		int shaderCount = glGetProgrami(program, GL_ATTACHED_SHADERS);
		IntBuffer attachedShaders = directIntBuffer(shaderCount);
		IntBuffer count = directIntBuffer(1);
		glGetAttachedShaders(program, count, attachedShaders);
		assert count.get() == shaderCount;
		for (int i = 0; i < shaderCount; ++i) {
			int shaderCandidate = attachedShaders.get();
			if (searchedType == glGetShaderi(shaderCandidate, GL_SHADER_TYPE)) {
				return shaderCandidate;
			}
		}
		return 0;
	}
	/**
	 * Links the specified program and throws when the linking fails
	 *
	 * @param program
	 *            the program to link
	 * @throws IllegalStateException
	 *             when the linking fails
	 */
	public static void linkProgramSafe(int program) {
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			int errorLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
			String error = glGetProgramInfoLog(program, errorLength);
			throw new IllegalStateException(error);
		}
	}
}
