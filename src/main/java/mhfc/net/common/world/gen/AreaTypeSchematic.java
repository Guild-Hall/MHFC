package mhfc.net.common.world.gen;

import java.io.BufferedInputStream;
import java.io.IOException;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.operation.RunContext;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.registry.LegacyWorldData;
import com.sk89q.worldedit.world.registry.WorldData;

import mhfc.net.common.util.Utilities;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.DisplacedView;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.controller.CornerPosition;
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
			areaInformation = format.getReader(instream).read(forgeData);
		}

		Vector origin = areaInformation.getOrigin();
		Vector clipLowerLeft = areaInformation.getMinimumPoint();
		absoluteMinimum = Vector.getMinimum(origin, clipLowerLeft);
		clipboardRegion = new CuboidRegion(areaInformation.getMinimumPoint(), areaInformation.getMaximumPoint());

	}

	@Override
	public final IArea populate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration)
			throws WorldEditException {
		DisplacedView view = new DisplacedView(lowerLeftCorner, configuration, world);
		WorldDisplacedView displacedWorld = new WorldDisplacedView(view);

		ForwardExtentCopy copyOp = new ForwardExtentCopy(
				areaInformation,
				clipboardRegion,
				displacedWorld,
				areaInformation.getMinimumPoint().subtract(absoluteMinimum));
		RunContext def = new RunContext();
		Operations.completeLegacy(copyOp.resume(def));
		Operations.completeLegacy(displacedWorld.commit());

		return onPopulate(world, lowerLeftCorner, configuration);
	}

	@Override
	public AreaConfiguration configForNewArea() {
		Vector absoluteSize = areaInformation.getMaximumPoint().subtract(absoluteMinimum);
		return new AreaConfiguration((absoluteSize.getBlockX() + 15) / 16, (absoluteSize.getBlockZ() + 15) / 16);
	}

	protected abstract IArea onPopulate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration);

}
