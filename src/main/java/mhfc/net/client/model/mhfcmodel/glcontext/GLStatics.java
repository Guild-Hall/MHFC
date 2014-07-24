package mhfc.net.client.model.mhfcmodel.glcontext;

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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Static Helper for common methods that {@link IGLHelper}s might use.
 *
 * @author WorldSEnder
 *
 */
public class GLStatics {
	public static ByteBuffer getShaderSource(InputStream is) throws IOException {
		byte[] buffer = new byte[4096];
		int bytesRead, bufferSize = 0;
		ByteBuffer target = createDirectBuffer(0);
		while ((bytesRead = is.read(buffer)) > 0) {
			bufferSize += bytesRead;
			ByteBuffer newTarget = createDirectBuffer(bufferSize);
			newTarget.put(target);
			newTarget.put(buffer, 0, bytesRead);
			newTarget.rewind();
			target = newTarget;
		}
		return target;
	}

	public static ByteBuffer createDirectBuffer(int size) {
		return ByteBuffer.allocateDirect(size);
	}

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

	public static int createProgramSafe() {
		int programName = glCreateProgram();
		if (programName == 0) {
			throw new IllegalStateException(
					"GL Error: Created Program is 0. Can't proceed.");
		}
		return programName;
	}

	public static int getShader(int program, int searchedType) {
		int shaderCount = glGetProgrami(program, GL_ATTACHED_SHADERS);
		IntBuffer attachedShaders = createDirectBuffer(shaderCount * 4)
				.asIntBuffer();
		IntBuffer count = createDirectBuffer(4).asIntBuffer();
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

	public static void linkProgramSafe(int program) {
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			int errorLength = glGetProgrami(program, GL_INFO_LOG_LENGTH);
			String error = glGetProgramInfoLog(program, errorLength);
			throw new IllegalStateException(error);
		}
	}
}
