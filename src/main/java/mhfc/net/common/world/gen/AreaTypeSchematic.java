package mhfc.net.common.world.gen;

import java.io.BufferedInputStream;
import java.io.IOException;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.operation.DelegateOperation;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.registry.LegacyWorldData;
import com.sk89q.worldedit.world.registry.WorldData;

import mhfc.net.common.util.Utilities;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.AreaPlanAdapter;
import mhfc.net.common.world.area.DisplacedView;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaPlan;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.worldedit.WorldDisplacedView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AreaTypeSchematic implements IAreaType {

	private static WorldData forgeData = LegacyWorldData.getInstance();
	private static ClipboardFormat format = ClipboardFormat.SCHEMATIC;

	protected Clipboard areaInformation;
	protected Vector absoluteMinimum;
	protected Region clipboardRegion;

	public AreaTypeSchematic(ResourceLocation schematicLocation) throws IOException {
		try (BufferedInputStream instream = Utilities.inputStream(schematicLocation)) {
			areaInformation = AreaTypeSchematic.format.getReader(instream).read(AreaTypeSchematic.forgeData);
		}

		Vector origin = areaInformation.getOrigin();
		Vector clipLowerLeft = areaInformation.getMinimumPoint();
		absoluteMinimum = Vector.getMinimum(origin, clipLowerLeft);
		clipboardRegion = new CuboidRegion(areaInformation.getMinimumPoint(), areaInformation.getMaximumPoint());

	}

	@Override
	public final IAreaPlan populate(World world, AreaConfiguration configuration) {
		DisplacedView view = new DisplacedView(configuration.getPosition(), configuration, world);
		WorldDisplacedView displacedWorld = new WorldDisplacedView(view);

		ForwardExtentCopy copyOp = new ForwardExtentCopy(
				areaInformation,
				clipboardRegion,
				displacedWorld,
				areaInformation.getMinimumPoint().subtract(absoluteMinimum));
		Operation commit = displacedWorld.commit();
		Operation chain = commit == null ? copyOp : new DelegateOperation(commit, copyOp);

		return new AreaPlanAdapter(onPopulate(world, configuration), chain);
	}

	@Override
	public AreaConfiguration configForNewArea() {
		Vector absoluteSize = areaInformation.getMaximumPoint().subtract(absoluteMinimum);
		return new AreaConfiguration(
				(absoluteSize.getBlockX() + 15) / 16,
				(absoluteSize.getBlockZ() + 15) / 16,
				IExtendedConfiguration.EMPTY);
	}

	protected abstract IArea onPopulate(World world, AreaConfiguration configuration);

}
