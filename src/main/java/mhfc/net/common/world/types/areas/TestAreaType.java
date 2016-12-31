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

public class TestAreaType extends AreaTypeSchematic {

	public static final ResourceLocation schematicLocation = new ResourceLocation("mhfc:schematics/Test.schematic");

	public static final TestAreaType INSTANCE = new TestAreaType();

	private static class Area extends EmptyArea {
		public Area(World world, AreaConfiguration config) {
			super(world, config);
		}

		@Override
		public void teleportToSpawn(EntityPlayer player) {
			double posX = 3;
			double posY = 8;
			double posZ = 3;
			worldView.moveEntityTo(player, posX, posY, posZ);
		}

		@Override
		public SpawnInformation constructDefaultSpawnInformation(Spawnable entity) {
			return new SpawnInformation(entity, 10, 9, 10);
		}
	}

	private TestAreaType() {
		super(TestAreaType.schematicLocation);
	}

	@Override
	public String getUnlocalizedName() {
		return MHFCReference.area_test_name;
	}

	@Override
	public IArea provideForLoading(World world, AreaConfiguration config) {
		return new Area(world, config);
	}

	@Override
	public IExtendedConfiguration configForLoading() {
		return IExtendedConfiguration.EMPTY;
	}

}
