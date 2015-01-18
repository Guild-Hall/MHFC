package mhfc.heltrato.common.item.weapon;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.helper.MHFCWeaponClassingHelper;
import mhfc.heltrato.common.helper.system.MHFCColorHelper;
import mhfc.heltrato.common.util.Attributes;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


/*For future basing of every Weapon Class of MHFC . 
 * Will remove extending the ItemSword soon to make it more stable.
 * 
 * Easy Class for making weapons with effects.
 */
public class WeaponMelee extends ItemSword{

	public String des1, des2 , des3 , weaponicon ; // <--- Shorten the handles
	public float damage , damageincrease , matDamage, matReduction , knockback;
	public Random rand ;
	public int attackdelay, rarity , meta, amplified;
	public Item.ToolMaterial weaponMat ;
	public MHFCWeaponClassingHelper clazz;
	public MHFCReference ref;
	public boolean poisontype, firetype;
	
	
	public WeaponMelee(ToolMaterial getType) {
		super(getType);
		setCreativeTab(MHFCMain.mhfctabs);
		setFull3D();
		rand = new Random();
	}
	
	public void elementalType(boolean poison, boolean fire){
		poisontype = poison; firetype = fire;
	}
	
	
	public float getEntityDamage() {
		if	(weaponMat == null)	 return 0;		return matDamage;
	}
	
	@Deprecated // will rework soon
	public void getWeaponDescription(String title) {
		des1 = title;
	}
	
	public void getWeaponDescriptionWithMeta(String second, int rarerty, int metaData){
		 des2 = second;  rarity = rarerty; meta = metaData;
	}
	
	public void getWeaponDescription(String second, int rarerty){
		 des2 = second;  rarity = rarerty;
	}
	
	public void getWeaponTextureloc(String textureloc) {
		weaponicon = textureloc;
	}
	
	public float updateDamageAmount(float damageX){
		return damage = damageX;
	}
	
	
	public void getWeaponTable(float defaultDamageReduction, int aDelay, float kBack){
		matReduction = defaultDamageReduction; attackdelay = aDelay; knockback = kBack;
	}
	
	public void updateDamaging(EntityLivingBase target, EntityLivingBase player, float damageincrease2){
		damage = matDamage;
		damageincrease = damageincrease2;
		DamageSource dmgSource = DamageSource.causePlayerDamage((EntityPlayer) player);
		updateDamageAmount(damage);
		target.attackEntityFrom(dmgSource, damage);
		
	}
	
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase ent, EntityLivingBase player)  {
		if( ent.hurtResistantTime == ent.maxHurtResistantTime){
			ent.hurtResistantTime += getAttackDelay(stack, ent, player);
		}
		if(poisontype) ent.addPotionEffect(new PotionEffect(Potion.poison.id, 30, amplified));
		if(firetype)  ent.setFire(1);
		updateDamaging(ent, player, updateDamageAmount(damage));
		return true;
	}
	
	public int getAttackDelay(ItemStack stack, EntityLivingBase living, EntityLivingBase attacker){
		return attackdelay;
	}
	
	
	@Override
	public void registerIcons(IIconRegister par1IconRegister){
		itemIcon = par1IconRegister.registerIcon(weaponicon);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add(MHFCColorHelper.gold + des1);
		par3List.add(MHFCColorHelper.dark_green + des2);
		par3List.add(MHFCColorHelper.dark_blue + "Rarity: " + rarity);
	}
	
	public void addAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
		float dmg = getEntityDamage(); {
			if(dmg > 0 ){
				multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(getUUID(), "Weapon modifier", dmg, 0));
			}
			multimap.put(Attributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(getUUID(), "Weapon attack speed modifier", -attackdelay, 0));
		}
	}
	
	public Multimap<String, AttributeModifier> getItemAttributeModifier(){
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		addAttributeModifiers(multimap);
		return multimap;
		
	}
	
	public UUID getUUID() {
		return field_111210_e;
	}
	
	
	
	

}
