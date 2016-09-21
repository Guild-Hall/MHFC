package mhfc.net.common.world.types;

import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IExtendedConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DesertPalmType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation(
			"mhfc:schematics/map_desertpalm_1_vanilla.schematic");
	public static final DesertPalmType INSTANCE = new DesertPalmType();

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			BlockPos position = new BlockPos(64, 0, 14);
			position = worldView.getTopSolidOrLiquidBlock(position).up();
			worldView.moveEntityTo(player, position);
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return new SpawnInformation(entity, 50, 54.5, 62);
		}

	}

	private DesertPalmType() {
		super(DesertPalmType.schematicLocation);
	}

	@Override
	public String getUnlocalizedName() {
		return ResourceInterface.area_desertpalm_name;
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
