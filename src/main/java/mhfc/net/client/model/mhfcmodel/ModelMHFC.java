package mhfc.net.client.model.mhfcmodel;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL20.GL_ATTACHED_SHADERS;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_CURRENT_PROGRAM;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_SHADER_TYPE;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetAttachedShaders;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL41.GL_FRAGMENT_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_PROGRAM_PIPELINE_BINDING;
import static org.lwjgl.opengl.GL41.GL_TESS_CONTROL_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_TESS_EVALUATION_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_VERTEX_SHADER_BIT;
import static org.lwjgl.opengl.GL41.glBindProgramPipeline;
import static org.lwjgl.opengl.GL41.glGenProgramPipelines;
import static org.lwjgl.opengl.GL41.glUseProgramStages;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import mhfc.net.client.model.PartTickModelBase;
import mhfc.net.common.entity.type.IMHFCAnimatedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;

import org.lwjgl.opengl.GLContext;
/**
 *
 * @author WorldSEnder
 *
 */
public class ModelMHFC extends PartTickModelBase implements IModelCustom {

	protected static int shaderName;
	protected static int programName;

	public static final boolean supportsPipelines = GLContext.getCapabilities().GL_ARB_separate_shader_objects;
	/** Only set if {@link #supportsPipelines} */
	protected static final int EXTERNAL_SHADER_BITS = GL_FRAGMENT_SHADER_BIT
			| GL_TESS_CONTROL_SHADER_BIT | GL_TESS_EVALUATION_SHADER_BIT;;
	/** Only set if {@link #supportsPipelines} */
	protected static int pipelineName;
	/** The location of the bonematrices */
	protected static int boneMatricesLocation;
	static {
		shaderName = glCreateShader(GL_VERTEX_SHADER);
		if (shaderName == 0) {
			throw new IllegalStateException(
					"GL Error: Created Shader is 0. Can't proceed.");
		}
		programName = glCreateProgram();
		if (programName == 0) {
			throw new IllegalStateException(
					"GL Error: Created Program is 0. Can't proceed.");
		}
		try {
			ByteBuffer shaderSource = getShaderSource();
			glShaderSource(shaderName, shaderSource);
			if (glGetShaderi(shaderName, GL_COMPILE_STATUS) == 0) {
				String error = glGetShaderInfoLog(shaderName, 1024);
				throw new IllegalStateException(error);
			}
			glAttachShader(programName, shaderName);
			glLinkProgram(programName);
			if (glGetProgrami(programName, GL_LINK_STATUS) == 0) {
				String error = glGetProgramInfoLog(programName, 1024);
				throw new IllegalStateException(error);
			}
			if (supportsPipelines) {
				pipelineName = glGenProgramPipelines();
				glUseProgramStages(pipelineName, GL_VERTEX_SHADER_BIT,
						programName);
			}
		} catch (Exception e) {
			throw new IllegalStateException(
					"Error setting up OpenGL enviroment.", e);
		}
	}

	private static ByteBuffer getShaderSource() throws IOException {
		InputStream is = ModelMHFC.class.getResourceAsStream("default.vsh");
		byte[] buffer = new byte[4096];
		int bytesRead, bufferSize = 0;
		ByteBuffer target = createDirectBuffer(0);
		while ((bytesRead = is.read(buffer)) > 0) {
			bufferSize += bytesRead;
			target = (ByteBuffer) createDirectBuffer(bufferSize).put(target)
					.put(buffer).rewind();
		}
		return target;
	}

	private static ByteBuffer createDirectBuffer(int size) {
		return ByteBuffer.allocateDirect(size);
	}

	private static int getShader(int program, int searchedType) {
		int shaderCount = glGetProgrami(programName, GL_ATTACHED_SHADERS);
		IntBuffer attachedShaders = createDirectBuffer(shaderCount * 4)
				.asIntBuffer();
		IntBuffer count = createDirectBuffer(4).asIntBuffer();
		glGetAttachedShaders(programName, count, attachedShaders);
		assert count.get() == shaderCount;
		for (int i = 0; i < shaderCount; ++i) {
			int shaderCandidate = attachedShaders.get();
			if (searchedType == glGetShaderi(shaderCandidate, GL_SHADER_TYPE)) {
				return shaderCandidate;
			}
		}
		return 0;
	}

	private boolean isLoaded = false;

	/**
	 * Creates a new ModelMHFC.
	 *
	 * @param resource
	 *            - The resource to load from. Refer to the wiki on
	 *            https://github.com/Heltrato/MHFC/wiki/API-overview to get
	 *            detail about the modelformat
	 * @throws ModelFormatException
	 *             - if the resource given cannot be read from or the file is
	 *             malformed
	 */
	public ModelMHFC(ResourceLocation resource) throws ModelFormatException {
		try (InputStream is = Minecraft.getMinecraft().getResourceManager()
				.getResource(resource).getInputStream()) {
			this.loadFromRes(is);
		} catch (NullPointerException npe) {
			throw new ModelFormatException("File can't be null.", npe);
		} catch (IOException ioe) {
			throw new ModelFormatException(String.format(
					"Can't read from resource given (%s).", resource), ioe);
		} catch (ModelFormatException mfe) {
			throw new ModelFormatException(
					String.format(
							"A model format excpetion occured when reading from file %s",
							resource), mfe);
		}
		this.isLoaded = true;
	}
	private void loadFromRes(InputStream inputStream)
			throws ModelFormatException {

	}

	@Override
	public void render(Entity entity, float uLimbSwing,
			float interpolatedSwing, float uRotfloat, float headYaw,
			float interpolatedPitch, float size) {
		if (!(entity instanceof IMHFCAnimatedEntity))
			throw new IllegalArgumentException(
					String.format(
							"Entity rendered must be an IMHFCAnimatedEntity. EntityId %d",
							entity.getEntityId()));
		IMHFCAnimatedEntity animatedEntity = (IMHFCAnimatedEntity) entity;

		int currProgram = glGetInteger(GL_CURRENT_PROGRAM);
		int currVertexShader = 0;
		int currPipeline = 0;
		if (supportsPipelines) {
			currPipeline = glGetInteger(GL_PROGRAM_PIPELINE_BINDING);
			glUseProgramStages(pipelineName, EXTERNAL_SHADER_BITS, currProgram);
			glBindProgramPipeline(pipelineName);
			glUseProgram(0);
		} else if (currProgram == 0) {
			glUseProgram(programName);
		} else {
			currVertexShader = getShader(currProgram, GL_VERTEX_SHADER);
			// if (currVertexShader != shaderName) { // Will not occur as
			// owned by us
			if (currVertexShader != 0) {
				glDetachShader(currProgram, currVertexShader);
			}
			glAttachShader(currProgram, shaderName);
			glLinkProgram(currProgram);
			// }
		}
		AnimationInformation info = animatedEntity.getAnimInformation();
		String[] renderedParts = null;
		if (info != null) {
			int currFrame = info.getAnimationFrame();
			MHFCAttack currAttk = info.getCurrentAttack();
			if (currAttk != null) {
				currAttk.glBindBoneMatrices(this.boneMatricesLocation,
						currFrame, currFrame);
				renderedParts = info.getPartsToRender();
			}
		}
		if (renderedParts == null) {
			renderAll();
		} else {
			renderOnly(renderedParts);
		}
		if (!supportsPipelines && currProgram != 0) {
			glDetachShader(currProgram, shaderName);
			if (currVertexShader != 0) {
				glAttachShader(currProgram, currVertexShader);
			}
			glLinkProgram(currProgram);
		}
		glUseProgram(currProgram);
		glBindProgramPipeline(currPipeline);
	}

	@Override
	public ModelRenderer getRandomModelBox(Random r) {
		// FIXME For some reason we cannot return null here
		return null;
	}

	// Will not use this method
	@Override
	public TextureOffset getTextureOffset(String boxName) {
		return new TextureOffset(0, 0);
	}

	@Override
	public String getType() {
		return "MHFC";
	}

	@Override
	public void renderAll() {
		// TODO: renderAll()
	}

	@Override
	public void renderOnly(String... groupNames) {

	}

	@Override
	public void renderPart(String partName) {
		// Will we need this one?
	}

	@Override
	public void renderAllExcept(String... excludedGroupNames) {
		// Don't think this will be used
	}
}
