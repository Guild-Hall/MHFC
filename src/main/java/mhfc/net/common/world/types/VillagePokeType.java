package mhfc.net.common.world.types;

import java.io.IOException;

import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.controller.CornerPosition;
import mhfc.net.common.world.gen.AreaTypeSchematic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class VillagePokeType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/village_poke.schematic");

	public VillagePokeType() throws IOException {
		super(schematicLocation);
	}

	@Override
	public IArea provideForLoading(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AreaConfiguration configForLoading() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IArea onPopulate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration) {
		// TODO Auto-generated method stub
		return null;
	}

}
