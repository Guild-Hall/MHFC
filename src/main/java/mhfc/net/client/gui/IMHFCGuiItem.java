package mhfc.net.client.gui;

import net.minecraft.client.Minecraft;

public interface IMHFCGuiItem extends IMouseInteractable {

	void draw(double mouseX, double mouseY, float partialTick);

	void initializeContext(Minecraft mc);

}
