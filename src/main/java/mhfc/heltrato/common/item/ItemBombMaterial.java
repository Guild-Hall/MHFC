package mhfc.heltrato.common.item;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegPotion;
import mhfc.heltrato.common.entity.projectile.EntityRathalosFireball;
import mhfc.heltrato.common.util.Cooldown;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBombMaterial extends Item{
	public static final int COOLDOWN = 200; //200 - cooldown in ticks. 1 sec - 20 ticks.
	
	public ItemBombMaterial(){
		super();
		setUnlocalizedName("bombmaterial");
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
	        this.itemIcon = par1IconRegister.registerIcon("mhfc:bombmaterial");
	}
	
	
	/*public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityRathalosFireball(world, player, 2D, 2D, 2D));
        }

        return itemstack;
    }*/
}
