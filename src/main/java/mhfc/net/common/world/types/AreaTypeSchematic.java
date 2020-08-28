package mhfc.net.common.world.types;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.world.FastModeExtent;
import com.sk89q.worldedit.function.operation.DelegateOperation;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.registry.LegacyWorldData;
import com.sk89q.worldedit.world.registry.WorldData;
import mhfc.net.MHFCMain;
import mhfc.net.common.util.ResourceLocations;
import mhfc.net.common.world.area.*;
import mhfc.net.common.worldedit.IClipboardFormat;
import mhfc.net.common.worldedit.RegionSplittingOperation;
import mhfc.net.common.worldedit.WorldDisplacedView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public abstract class AreaTypeSchematic extends IAreaType.Impl {

	private static WorldData forgeData = LegacyWorldData.getInstance();
	// use the portable schematics here
	private static int DIM_SIZE = 8;

	private boolean isInitialized = false;
	private final ResourceLocation schematicLocation;
	private final IClipboardFormat fileformat;

	protected Clipboard areaClipboard;
	protected Vector absoluteMinimum;
	protected CuboidRegion clipboardRegion;

	public AreaTypeSchematic(ResourceLocation schematicLocation, IClipboardFormat fileformat) {
		this.schematicLocation = Objects.requireNonNull(schematicLocation);
		this.fileformat = Objects.requireNonNull(fileformat);
	}

	private void ensureInit() {
		if(isInitialized) {
			return;
		}
		
		try (BufferedInputStream instream = ResourceLocations.openEmbeddedResource(schematicLocation)) {
			areaClipboard = fileformat.getReader(instream).read(AreaTypeSchematic.forgeData);
		} catch (IOException e) {
			MHFCMain.logger().error(
					"Unable to load schematic {}. The area type will be blank instead",
					schematicLocation.getResourcePath());
			areaClipboard = new BlockArrayClipboard(new CuboidRegion(Vector.ZERO, Vector.ZERO));
		}

		final Vector origin = areaClipboard.getOrigin();
		final Vector clipLowerLeft = areaClipboard.getMinimumPoint();
		absoluteMinimum = Vector.getMinimum(origin, clipLowerLeft);
		clipboardRegion = new CuboidRegion(areaClipboard.getMinimumPoint(), areaClipboard.getMaximumPoint());
		isInitialized = true;
	}

	@Override
	public final Operation populate(World world, AreaConfiguration configuration) {
		ensureInit();
		final DisplacedView view = new DisplacedView(configuration.getPosition(), configuration, world);
		final WorldDisplacedView displacedWorld = new WorldDisplacedView(view);
		final FastModeExtent destination = new FastModeExtent(displacedWorld);
		destination.setEnabled(true);

		MHFCMain.logger().debug(
				"Starting to copy {} blocks in chunks of {}",
				clipboardRegion.getArea(),
				DIM_SIZE * DIM_SIZE * DIM_SIZE);
		final Operation copyOp = new RegionSplittingOperation(clipboardRegion, region -> {
			return new ForwardExtentCopy(
					areaClipboard,
					region,
					destination,
					region.getMinimumPoint().subtract(absoluteMinimum));
		}, DIM_SIZE);
		final Operation commit = displacedWorld.commit();
		final Operation chain = commit == null ? copyOp : new DelegateOperation(commit, copyOp);

		return chain;
	}

	@Override
	public AreaConfiguration configForNewArea() {
		ensureInit();
		final Vector absoluteSize = areaClipboard.getMaximumPoint().subtract(absoluteMinimum);
		return new AreaConfiguration(
				(absoluteSize.getBlockX() + 15) / 16,
				(absoluteSize.getBlockZ() + 15) / 16,
				IExtendedConfiguration.EMPTY);
	}

	@Override
	public abstract IArea provideForLoading(World world, AreaConfiguration configuration);

}