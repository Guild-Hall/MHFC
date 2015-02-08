package mhfc.net.common.weapon.melee;

import java.util.List;
import java.util.UUID;

import mhfc.net.MHFCMain;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.Cooldown;
import mhfc.net.common.weapon.ComponentMelee;
import mhfc.net.common.weapon.IItemWeapon;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/*For future basing of every Weapon Class of MHFC .
 * Will remove extending the ItemSword soon to make it more stable.
 *
 * Easy Class for making weapons with effects.
 */
public abstract class WeaponMelee extends ItemSword implements IItemWeapon {

	protected ComponentMelee weapon;
	protected String des1, des2, des3; // <--- Shorten the handles
	protected float damage, damageincrease, matDamage, matReduction, knockback;
	protected int attackdelay, rarity, meta, amplified, getcooldown;
	protected Item.ToolMaterial weaponMat;
	protected boolean poisontype, firetype, enableCooldownDisplay;

	public WeaponMelee(ComponentMelee weaponf) {
		super((weaponf.weaponMaterial == null
				? ToolMaterial.WOOD
				: weaponf.weaponMaterial));
		setCreativeTab(MHFCMain.mhfctabs);
		setFull3D();
		weapon = weaponf;
		weapon.setItem(this);
		weapon.setThisItemProperties();
	}

	public void elementalType(boolean poison, boolean fire) {
		poisontype = poison;
		firetype = fire;
	}

	public float getEntityDamage() {
		if (weaponMat == null)
			return 0;
		return matDamage;
	}

	@Deprecated
	// will rework soon
	public void getWeaponDescription(String title) {
		des1 = title;
	}

	public void getWeaponDescriptionWithMeta(String second, int rarerty,
			int metaData) {
		des2 = second;
		rarity = rarerty;
		meta = metaData;
	}

	public void getWeaponDescription(String second, int rarerty) {
		des2 = second;
		rarity = rarerty;
	}

	public float updateDamageAmount(float damageX) {
		return damage = damageX;
	}

	public void getWeaponTable(float defaultDamageReduction, int aDelay,
			float kBack) {
		matReduction = defaultDamageReduction;
		attackdelay = aDelay;
		knockback = kBack;
	}

	public void updateDamaging(EntityLivingBase target,
			EntityLivingBase player, float damageincrease2) {
		damage = matDamage;
		damageincrease = damageincrease2;
		DamageSource dmgSource = DamageSource
				.causePlayerDamage((EntityPlayer) player);
		updateDamageAmount(damage);
		target.attackEntityFrom(dmgSource, damage);

	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add(ColorSystem.gold + des1);
		par3List.add(ColorSystem.dark_green + des2);
		par3List.add(ColorSystem.dark_blue + "Rarity: " + rarity);
		if (enableCooldownDisplay)
			Cooldown.displayCooldown(par1ItemStack, par3List, getcooldown);
	}

	@Override
	public float func_150931_i() {
		return weapon.getEntityDamageMaterialPart();
	}

	@Override
	public float func_150893_a(ItemStack itemstack, Block block) {
		return weapon.getBlockDamage(itemstack, block);
	}

	@Override
	public boolean func_150897_b(Block block) {
		return weapon.canHarvestBlock(block);
	}

	@Override
	public boolean hitEntity(ItemStack itemstack,
			EntityLivingBase entityliving, EntityLivingBase attacker) {
		return weapon.hitEntity(itemstack, entityliving, attacker);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world,
			Block block, int j, int k, int l, EntityLivingBase entityliving) {
		return weapon.onBlockDestroyed(itemstack, world, block, j, k, l,
				entityliving);
	}

	@Override
	public int getItemEnchantability() {
		return weapon.getItemEnchantability();
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return weapon.getItemUseAction(itemstack);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return weapon.getMaxItemUseDuration(itemstack);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player,
			Entity entity) {
		return weapon.onLeftClickEntity(itemstack, player, entity);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world,
			EntityPlayer entityplayer) {
		return weapon.onItemRightClick(itemstack, world, entityplayer);
	}

	@Override
	public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer,
			int count) {
		weapon.onUsingTick(itemstack, entityplayer, count);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world,
			EntityPlayer entityplayer, int i) {
		weapon.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
	}

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entity,
			int i, boolean flag) {
		weapon.onUpdate(itemstack, world, entity, i, flag);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		weapon.addItemAttributeModifiers(multimap);
		return multimap;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldRotateAroundWhenRendering() {
		return weapon.shouldRotateAroundWhenRendering();
	}

	@Override
	public final UUID getUUID() {
		return field_111210_e;
	}

	@Override
	public ComponentMelee getMeleeComponent() {
		return weapon;
	}

}
