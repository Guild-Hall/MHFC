package mhfc.net.client.model.mhfcmodel.glcontext;

import mhfc.net.client.model.mhfcmodel.IRenderInformation;
import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import mhfc.net.client.model.mhfcmodel.animation.IAnimation;
import mhfc.net.client.model.mhfcmodel.data.ModelDataBasic;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1;

import com.google.common.base.Predicate;

public class GLHelperBasic extends GLHelper {
	private ModelDataBasic modelData;

	@Override
	public void loadFrom(RawDataV1 datav1) {
		if (modelData != null)
			modelData.free();
		modelData = new ModelDataBasic(datav1);
	}

	@Override
	public void render(IAnimatedObject entity, float subFrame) {
		if (this.modelData == null) // Not loaded correctly
			return;
		Predicate<String> filter = entity.getPartPredicate(subFrame);
		IRenderInformation info = entity.getRenderInformation();
		IAnimation currAnim = info.getCurrentAnimation();
		int frame = info.getCurrentFrame();
		this.modelData.renderFiltered(filter, currAnim, frame, subFrame);
	}
}
