package mhfc.net.common.system;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class DamageSystem extends EntityDamageSource {

	/**
	 * A renowned delayed plan. Im going to try to tackle on this as it was the new.
	 * 
	 * @Heltrato
	 */

	public Entity attacker = null;

	public DamageSystem(String name, Entity damagingEntity, Entity attacker) {
		super(name, damagingEntity);
	}

	/** This makes repel the other mods and may realize youtuber that our entites are not for mob battles. **/
	public static DamageSource antimod = new DamageSource("anti").setDamageBypassesArmor().setDamageIsAbsolute();

	@Override
	public Entity getEntity() {
		return this.attacker;
	}

	/** Gets the death message that is displayed when the player dies **/
	@SuppressWarnings("deprecation")
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
		ITextComponent itextcomponent;
		ItemStack itemstack = ItemStack.EMPTY;
		if (this.attacker == null && this.damageSourceEntity == null) {
			itextcomponent = entityLivingBaseIn.getDisplayName();
		} else {
			itextcomponent = this.attacker == null
					? this.damageSourceEntity.getDisplayName()
					: this.attacker.getDisplayName();
			itemstack = this.attacker instanceof EntityLivingBase
					? ((EntityLivingBase) this.attacker).getHeldItemMainhand()
					: ItemStack.EMPTY;
		}
		String s = "death.attack." + this.getDamageType();
		String s1 = s + ".item";
		return !itemstack.isEmpty() && itemstack.hasDisplayName() && I18n.canTranslate(s1)
				? new TextComponentTranslation(
						s1,
						new Object[] { entityLivingBaseIn.getDisplayName(), itextcomponent,
								itemstack.getTextComponent() })
				: new TextComponentTranslation(s, new Object[] { entityLivingBaseIn.getDisplayName(), itextcomponent });
	}

}
