package mhfc.net.client.model.mhfcmodel.glcontext;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL20.GL_CURRENT_PROGRAM;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL41.GL_FRAGMENT_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_PROGRAM_PIPELINE_BINDING;
import static org.lwjgl.opengl.GL41.GL_TESS_CONTROL_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_TESS_EVALUATION_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_VERTEX_SHADER_BIT;
import static org.lwjgl.opengl.GL41.glBindProgramPipeline;
import static org.lwjgl.opengl.GL41.glGenProgramPipelines;
import static org.lwjgl.opengl.GL41.glUseProgramStages;
import mhfc.net.client.model.mhfcmodel.AnimationInformation;
import mhfc.net.client.model.mhfcmodel.MHFCAttack;
import mhfc.net.client.model.mhfcmodel.data.ModelData40;
import mhfc.net.client.model.mhfcmodel.data.RawModelData;
import mhfc.net.common.entity.type.IMHFCAnimatedEntity;

public class GLHelper40 implements IGLHelper<ModelData40> {
	protected static final int EXTERNAL_SHADER_BITS = GL_FRAGMENT_SHADER_BIT
			| GL_TESS_CONTROL_SHADER_BIT | GL_TESS_EVALUATION_SHADER_BIT;

	private static boolean initiated = false;
	private static int shaderName;
	private static int programName;
	private static int pipelineName;

	private static void init() {
		try {
			if (shaderName == 0)
				shaderName = GLStatics.compileShaderSafe(GL_VERTEX_SHADER,
						GLHelper40.class.getResourceAsStream("default40.vsh"));
			if (programName == 0)
				programName = GLStatics.createProgramSafe();
			glAttachShader(programName, shaderName);
			glLinkProgram(programName);
			if (glGetProgrami(programName, GL_LINK_STATUS) == GL_FALSE) {
				String error = glGetProgramInfoLog(programName, 1024);
				throw new IllegalStateException(error);
			}
			pipelineName = glGenProgramPipelines();
			glUseProgramStages(pipelineName, GL_VERTEX_SHADER_BIT, programName);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Error setting up OpenGL enviroment.", e);
		}
		initiated = true;
	}

	private static void ensureInit() {
		if (!initiated)
			init();
	}

	@Override
	public ModelData40 translate(RawModelData amd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void render(ModelData40 data, IMHFCAnimatedEntity animatedEntity) {
		ensureInit();
		int currProgram = glGetInteger(GL_CURRENT_PROGRAM);
		int currPipeline = glGetInteger(GL_PROGRAM_PIPELINE_BINDING);
		glUseProgramStages(pipelineName, EXTERNAL_SHADER_BITS, currProgram);
		glBindProgramPipeline(pipelineName);
		glUseProgram(0);

		AnimationInformation info = animatedEntity.getAnimInformation();
		String[] renderedParts = null;
		if (info != null) {
			int currFrame = info.getAnimationFrame();
			MHFCAttack currAttk = info.getCurrentAttack();
			if (currAttk != null) {
				// TODO: bind in another way
				currAttk.glBindBoneMatrices(0, currFrame, currFrame);
			}
			renderedParts = info.getPartsToRender();
		}
		if (renderedParts == null) {
			renderAll();
		} else {
			renderOnly(renderedParts);
		}

		glUseProgram(currProgram);
		glBindProgramPipeline(currPipeline);
	}

	protected void renderAll() {
		// TODO: render the actual model
		glDisable(GL_TEXTURE_2D);
		glBegin(GL_TRIANGLES);
		glColor3f(1.0f, 1.0f, 1.0f);
		glVertex3f(0, 0, 0);
		glVertex3f(1, 0, 0);
		glVertex3f(0, 1, 0);
		glEnd();
		glEnable(GL_TEXTURE_2D);
	}

	protected void renderOnly(String... groupNames) {
	}

}
