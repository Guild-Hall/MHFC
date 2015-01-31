package mhfc.heltrato.common.item.materials;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class ItemBullet extends Item {

	public int meta;

	public ItemBullet(int metaData) {
		super();
		meta = metaData;
		setUnlocalizedName( "bullet" + meta );
		setCreativeTab(MHFCMain.mhfctabs);
		setMaxStackSize(12);

	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon( "mhfc:bullet" + meta );
	}
	
	
	public int getBulletType() {
		if(meta == 0){
			return 0;
		}
		return meta;
	}

}
