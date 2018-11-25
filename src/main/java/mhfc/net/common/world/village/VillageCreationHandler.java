package mhfc.net.common.world.village;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;
import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

import java.util.List;
import java.util.Random;

public class VillageCreationHandler implements IVillageCreationHandler {

	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i) {
		return new PieceWeight(getComponentClass(), 12, 1);
	}

	@Override
	public Class<QuestGiverHut> getComponentClass() {
		return QuestGiverHut.class;
	}

	@Override
	public Village buildComponent(
			PieceWeight weight,
			Start start,
			List<StructureComponent> structureComponents,
			Random rand,
			int structureMinX,
			int structureMinY,
			int structureMinZ,
			EnumFacing facing,
			int componentID) {
		BlockPos blockPos = new BlockPos(structureMinX, structureMinY, structureMinZ);
		return QuestGiverHut.create(weight, start, structureComponents, rand, blockPos, facing, componentID);
	}

}
