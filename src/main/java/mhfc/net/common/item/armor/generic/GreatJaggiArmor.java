package mhfc.net.common.item.armor.generic;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GreatJaggiArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_greatjaggi_helm_name,
			ResourceInterface.armor_greatjaggi_chest_name, ResourceInterface.armor_greatjaggi_legs_name,
			ResourceInterface.armor_greatjaggi_boots_name };

	public GreatJaggiArmor(EntityEquipmentSlot type) {
		super(Material.greatjaggi, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.jaggi;
		case LEGS:
			return null;
		case FEET:
			return Model.jaggi;
		case CHEST:
			return Model.jaggi;
		case MAINHAND:
		case OFFHAND:
		default:
			Assert.logUnreachable("Armor can only be equiped on armor slots, got {}", armorSlot);
		}

		return null;
	}
}
