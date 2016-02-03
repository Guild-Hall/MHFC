package mhfc.net.common.world.gen;

import java.io.IOException;
import java.io.InputStream;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.SchematicReader;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
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

public abstract class AreaTypeSchematic implements IAreaType{

	private static WorldData forgeData = LegacyWorldData.getInstance();
	protected Clipboard areaInformation;
	protected Vector absoluteMinimum;
	protected Region clipboardRegion;
	
	public AreaTypeSchematic(ResourceLocation schematicLocation) throws IOException {
		try(InputStream instream = Utilities.inputStream(schematicLocation);NBTInputStream nbtIn = new NBTInputStream(instream)){
			areaInformation = new SchematicReader(nbtIn).read(forgeData);
		}
		Vector origin = areaInformation.getOrigin();
		Vector clipLowerLeft = areaInformation.getMinimumPoint();
		absoluteMinimum = Vector.getMinimum(origin, clipLowerLeft);
		clipboardRegion = new CuboidRegion(absoluteMinimum, areaInformation.getMaximumPoint());
	}
	
	@Override
	public final IArea populate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration) throws WorldEditException {
		DisplacedView view = new DisplacedView(lowerLeftCorner, configuration, world);
		WorldDisplacedView displacedWorld = new WorldDisplacedView(view);
		
		ForwardExtentCopy copyOp = new ForwardExtentCopy(areaInformation, clipboardRegion, displacedWorld, Vector.ZERO);
		copyOp.resume(new RunContext());
		
		return onPopulate(world, lowerLeftCorner, configuration);
	}
	
	@Override
	public AreaConfiguration configForNewArea() {
		Vector absoluteSize = areaInformation.getMaximumPoint().subtract(absoluteMinimum);
		return new AreaConfiguration((absoluteSize.getBlockX()+15)/16, (absoluteSize.getBlockZ()+15)/16);
	}
	
	protected abstract IArea onPopulate(World world, CornerPosition lowerLeftCorner, AreaConfiguration configuration); 

}
