package mhfc.net.common.world.types;

import java.io.BufferedInputStream;
import java.io.IOException;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.operation.DelegateOperation;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.registry.LegacyWorldData;
import com.sk89q.worldedit.world.registry.WorldData;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.Utilities;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.DisplacedView;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.worldedit.RegionSplittingOperation;
import mhfc.net.common.worldedit.WorldDisplacedView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class AreaTypeSchematic implements IAreaType {

	private static WorldData forgeData = LegacyWorldData.getInstance();
	private static ClipboardFormat format = ClipboardFormat.SCHEMATIC;
	private static int DIM_SIZE = 16;

	protected Clipboard areaInformation;
	protected Vector absoluteMinimum;
	protected CuboidRegion clipboardRegion;

	public AreaTypeSchematic(ResourceLocation schematicLocation) {
		try (BufferedInputStream instream = Utilities.openEmbeddedResource(schematicLocation)) {
			areaInformation = AreaTypeSchematic.format.getReader(instream).read(AreaTypeSchematic.forgeData);
		} catch (IOException e) {
			MHFCMain.logger().error(
					"Unable to load schematic {}. The area type will be blank instead",
					schematicLocation.getResourcePath());
			areaInformation = new BlockArrayClipboard(new CuboidRegion(Vector.ZERO, Vector.ZERO));
		}

		Vector origin = areaInformation.getOrigin();
		Vector clipLowerLeft = areaInformation.getMinimumPoint();
		absoluteMinimum = Vector.getMinimum(origin, clipLowerLeft);
		clipboardRegion = new CuboidRegion(areaInformation.getMinimumPoint(), areaInformation.getMaximumPoint());

	}

	@Override
	public final Operation populate(World world, AreaConfiguration configuration) {
		DisplacedView view = new DisplacedView(configuration.getPosition(), configuration, world);
		WorldDisplacedView displacedWorld = new WorldDisplacedView(view);

		MHFCMain.logger().debug(
				"Starting to copy {} blocks in chunks of {}",
				clipboardRegion.getArea(),
				DIM_SIZE * DIM_SIZE * DIM_SIZE);
		Operation copyOp = new RegionSplittingOperation(clipboardRegion, region -> {
			return new ForwardExtentCopy(
					areaInformation,
					region,
					displacedWorld,
					region.getMinimumPoint().subtract(absoluteMinimum));
		}, DIM_SIZE);
		Operation commit = displacedWorld.commit();
		Operation chain = commit == null ? copyOp : new DelegateOperation(commit, copyOp);

		return chain;
	}

	@Override
	public AreaConfiguration configForNewArea() {
		Vector absoluteSize = areaInformation.getMaximumPoint().subtract(absoluteMinimum);
		return new AreaConfiguration(
				(absoluteSize.getBlockX() + 15) / 16,
				(absoluteSize.getBlockZ() + 15) / 16,
				IExtendedConfiguration.EMPTY);
	}

	@Override
	public abstract IArea provideForLoading(World world, AreaConfiguration configuration);

}
