package mhfc.net.common.world.types;

import java.io.IOException;

import mhfc.net.common.quests.world.SpawnControllerAdapter.SpawnInformation;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.EmptyArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IExtendedConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DesertPalmType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/map_desertpalm_1_vanilla.schematic");

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			double posX = 64;
			double posZ = 14;
			double posY = worldView.getTopSolidOrLiquidBlock((int) posX, (int) posZ) + 1;
			worldView.moveEntityTo(player, posX, posY, posZ);
		}

		@Override
		public String getUnlocalizedDisplayName() {
			return "area.desertpalm.name";
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return new SpawnInformation(entity, 50, 54.5, 62);
		}

	}

	private DesertPalmType() throws IOException {
		super(DesertPalmType.schematicLocation);
	}

	public static DesertPalmType INSTANCE;

	static {
		try {
			DesertPalmType.INSTANCE = new DesertPalmType();
		} catch (IOException e) {
			throw new RuntimeException("Could not load test area", e);
		}
	}

	@Override
	public IArea provideForLoading(World world, AreaConfiguration configuration) {
		return new Area(world, configuration);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

	@Override
	protected IArea areaToPopulate(World world, AreaConfiguration configuration) {
		return new Area(world, configuration);
	}

}
