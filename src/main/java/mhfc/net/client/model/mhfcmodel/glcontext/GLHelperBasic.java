package mhfc.net.client.model.mhfcmodel.glcontext;

import mhfc.net.client.model.mhfcmodel.Animation;
import mhfc.net.client.model.mhfcmodel.data.ModelDataBasic;
import mhfc.net.client.model.mhfcmodel.data.RawDataV1;
import mhfc.net.common.entity.type.IMHFCAnimatedObject;

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
	public void render(IMHFCAnimatedObject entity, float subFrame) {
		if (this.modelData == null) // Not loaded correctly
			return;
		Predicate<String> filter = entity.getPartPredicate(subFrame);
		Animation currAnim = entity.getRenderInformation()
				.getCurrentAnimation();
		this.modelData.renderFiltered(filter, currAnim, subFrame);
	}
}
