package mhfc.net.common.world.village;

import java.util.List;
import java.util.Random;

import mhfc.net.common.entity.quests.EntityQuestGiver;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import net.minecraft.world.gen.structure.StructureVillagePieces.Village;

public class QuestGiverHut extends StructureVillagePieces.Village {

	private static StructureBoundingBox getBoundingBox(BlockPos pos, EnumFacing facing, Random rand) {
		assert facing.getAxis().isHorizontal();
		BlockPos endPos = pos.offset(facing, 5);
		endPos = endPos.offset(facing.rotateY(), 10);
		endPos = endPos.offset(EnumFacing.UP, 5);
		return new StructureBoundingBox(pos, endPos);
	}

	public QuestGiverHut() {}

	private QuestGiverHut(
			StructureVillagePieces.Start start,
			int type,
			Random rand,
			StructureBoundingBox bounds,
			EnumFacing facing) {
		super(start, type);
		this.setCoordBaseMode(facing);
		this.boundingBox = bounds;
	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		if (this.averageGroundLvl < 0) {
			this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

			if (this.averageGroundLvl < 0) {
				return true;
			}

			this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
		}

		EntityQuestGiver questGiver = new EntityQuestGiver(worldIn);
		double posX = getXWithOffset(0, 0) + 0.5D;
		int posY = getYWithOffset(0);
		double posZ = getZWithOffset(0, 0) + 0.5D;
		StructureBoundingBox structureBox = getBoundingBox();
		worldIn.setBlockState(
				new BlockPos(structureBox.minX, structureBox.minY, structureBox.minZ),
				Blocks.OBSIDIAN.getDefaultState());
		questGiver.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
		worldIn.spawnEntity(questGiver);
		return true;
	}

	public static Village create(
			PieceWeight weight,
			Start start,
			List<StructureComponent> structureComponents,
			Random rand,
			BlockPos blockPos,
			EnumFacing facing,
			int componentID) {
		StructureBoundingBox bounds = getBoundingBox(blockPos, facing, rand);
		if (bounds == null || StructureComponent.findIntersecting(structureComponents, bounds) != null) {
			return null;
		}
		return new QuestGiverHut(start, componentID, rand, bounds, facing);
	}
}
