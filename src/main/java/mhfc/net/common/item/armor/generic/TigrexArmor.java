package mhfc.net.common.item.armor.generic;

import mhfc.net.common.entity.monster.wip.EntityKirin;
import mhfc.net.common.entity.monster.wip.EntityLagiacrus;
import mhfc.net.common.entity.monster.wip.EntityRathalos;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.index.armor.Material;
import mhfc.net.common.index.armor.Model;
import mhfc.net.common.item.ItemRarity;
import mhfc.net.common.item.armor.ArmorBase;
import mhfc.net.common.util.Assert;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TigrexArmor extends ArmorBase {
	private static final String[] names = { ResourceInterface.armor_tigrex_helm_name,
			ResourceInterface.armor_tigrex_chest_name, ResourceInterface.armor_tigrex_legs_name,
			ResourceInterface.armor_tigrex_boots_name };

	public TigrexArmor(EntityEquipmentSlot type) {
		super(Material.tigrex, ItemRarity.R04, type);
		setUnlocalizedName(names[3 - type.getIndex()]);
		if (this.addBasicInfo != null) {
		this.addBasicInfo.add("+15 Fire Resistance");
		this.addBasicInfo.add("+15 Ice Resistance");
		this.addBasicInfo.add("+10 Water Resistance");
		this.addBasicInfo.add("-10 Thunder Resistance");
		this.addBasicInfo.add(" -5 Dragon Resistance");
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ModelBiped getBipedModel(EntityEquipmentSlot armorSlot) {
		switch (armorSlot) {
		case HEAD:
			return Model.tigrex;
		case LEGS:
			return null;
		case FEET:
			return Model.tigrex;
		case CHEST:
			return Model.tigrex;
		case MAINHAND:
		case OFFHAND:
		default:
			Assert.logUnreachable("Armor can only be equiped on armor slots, got {}", armorSlot);
		}

		return null;
	}


	@Override
	protected void applySetBonus(World world, EntityPlayer player) {
		ItemStack equipement = player.getActiveItemStack();
		if (equipement != null && equipement.getItem() instanceof ItemFood) {
			// int maxUseDuration = equipement.getItem().getMaxItemUseDuration(equipement);
			// int currentDuration = player.getItemInUseCount();
			// TODO: whatever was planned for tigrex armor
		}
	}

	@Override
	public ArmorProperties getProperties(
			EntityLivingBase player,
			ItemStack armor,
			DamageSource source,
			double damage,
			int slot) {
		/**
		 * The initial absorption for monsters that are not included in the armors element excemption.
		 **/
		if (source.getSourceOfDamage() instanceof EntityMHFCBase) {
			return new ArmorProperties(1, 0.20, 320);
		} else if (source.getSourceOfDamage() instanceof EntityKirin
				&& source.getSourceOfDamage() instanceof EntityLagiacrus) {
			// +1 BECAUSE THIS IS PER PIECE!
			return new ArmorProperties(1, 0.25 - 0.10, 320);
		} else if (source.getSourceOfDamage() instanceof EntityRathalos) {
			return new ArmorProperties(1, 0.25 + 0.15, 320);
		}
		return new ArmorProperties(1, 100, 270);

	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (slot == 0) {
			return 1;
		} else if (slot == 1) {
			return 1;
		} else if (slot == 2) {
			return 1;
		}
		return 1;
	}

	@Override
	protected String addHeadInfo() {
		return "Made of Tigrex materials. Proof of conquering a despot who terrorized the land.";
	}

	@Override
	protected String addChestInfo() {
		return "Made of Tigrex materials. Armor worn by those who've exorcised its evil curse.";
	}

	@Override
	protected String addLegsInfo() {
		return "Made of Tigrex materials. The fiendish, impervious armor of a brutal tyrant.";
	}

	@Override
	protected String addBootsInfo() {
		return "Made of Tigrex material. Cut with the memory of a tyrant, the earth cries underfoot.";
	}

	@Override
	protected int setInitialDefenseValue() {
		return 200;
	}

	@Override
	protected int setFinalDefenseValue() {
		return 320;
	}
}
