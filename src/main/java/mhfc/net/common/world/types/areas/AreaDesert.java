package mhfc.net.common.world.types.areas;

import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.world.types.AreaTypeSchematic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AreaDesert extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/Desert.schematic");
	public static final AreaDesert INSTANCE = new AreaDesert();

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			double posX = 98;
			double posZ = 85;
			double posY = worldView.getTopSolidOrLiquidBlock((int) posX, (int) posZ) + 1;
			worldView.moveEntityTo(player, posX, posY, posZ);
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return new SpawnInformation(entity, 54, 20.5, 40);
		}

	}

	private AreaDesert() {
		super(AreaDesert.schematicLocation);
	}

	@Override
	public String getUnlocalizedName() {
		return MHFCReference.area_desert_name;
	}

	@Override
	public IArea provideForLoading(World world, AreaConfiguration configuration) {
		return new Area(world, configuration);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

}
