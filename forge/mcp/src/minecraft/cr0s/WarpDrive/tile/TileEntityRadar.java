package cr0s.WarpDrive.tile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cr0s.WarpDrive.WarpDrive;
import cr0s.WarpDrive.WarpDriveConfig;
import cr0s.WarpDrive.client.gui.GUIRadar;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.ILuaContext;
import dan200.computer.api.IPeripheral;
import net.minecraftforge.common.ForgeDirection;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import uedevkit.tile.TileElectricBase;
import uedevkit.util.MathHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityRadar extends TileElectricBase implements IPeripheral {
	
	public TileEntityRadar() {
		super(capacity, maxReceive, 0);
	}
	
	//public boolean isDrawingRed = false;
	//public boolean isDrawingYellow = false;
	//public boolean isDrawingForeground = false;
	//public boolean addedToEnergyNet = false;
	static int capacity = WarpDriveConfig.i.WR_MAX_ENERGY_VALUE * 250;
	static int maxReceive = capacity / 5;

	int currentEnergyValue = this.getPowerInt();

	private String[] methodsArray =
	{
		"scanRay",		// 0
		"scanRadius",		// 1
		"getResultsCount",	// 2
		"getResult",		// 3
		"getEnergyLevel",	// 4
		"pos"			// 5
	};

	private ArrayList<TileEntityReactor> results;

	private int scanRadius = 0;
	private int cooldownTime = 0;

	private boolean isEnergyEnoughForScanRadiusW(int radius) {
		int needEnergy = (radius * radius);
		return ((getCurrentEnergyValue() - needEnergy) > 0);
	}

	@Override
	public void updateEntity() {
		/*if (!addedToEnergyNet && !this.tileEntityInvalid)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			addedToEnergyNet = true;
		}*/

		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			return;
		}

		try {
			if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 2) {
				if (cooldownTime++ > (20 * ((scanRadius / 1000) + 1))) {
					//System.out.println("Scanning...");
					WarpDrive.instance.registry.removeDeadCores();
					results = WarpDrive.instance.registry.searchWarpCoresInRadius(xCoord, yCoord, zCoord, scanRadius);
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 + 2);
					cooldownTime = 0;
					
					
					 
					scanAndDraw();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		//this.currentEnergyValue = tag.getInteger("energy");
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		//tag.setInteger("energy", this.getCurrentEnergyValue());
	}

	// IPeripheral methods implementation
	@Override
	public String getType()
	{
		return "radar";
	}

	@Override
	public String[] getMethodNames()
	{
		return methodsArray;
	}
	
	public boolean scanRadius(int radius) {
		if (radius <= 0 || radius > 10000) {
			scanRadius = 0;
			return false;
		} if (radius != 0 && isEnergyEnoughForScanRadiusW(radius)) {
			// Consume energy
			//this.currentEnergyValue -= radius * radius;
			this.energy.extractEnergy(radius * radius, true);
			// Begin searching
			scanRadius = radius;
			cooldownTime = 0;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 1 + 2);
			
			return true;
		} else {
			results = null;
			System.out.println("Radius: " + radius + " | Enough energy: " + isEnergyEnoughForScanRadiusW(radius));
			return false;
		}
		//return false;
	}
	
	public int getResultCount() {
		if(results != null) {
			return results.size();
		}
		return 0;
	}
	
	public String getResultFrequency(int index2) {
		if (results != null) {
			int index = index2;
			if (index > -1 && index < results.size()) {
				TileEntityReactor res = results.get(index);
				if (res != null) {
					int yAddition = (res.worldObj.provider.dimensionId == WarpDrive.instance.spaceDimID) ? 256 : (res.worldObj.provider.dimensionId == WarpDrive.instance.hyperSpaceDimID) ? 512 : 0;
					//return res.coreFrequency, res.xCoord, res.yCoord + yAddition, res.zCoord;
					return res.coreFrequency;//new Object[] { (String)res.coreFrequency, (Integer)res.xCoord, (Integer)res.yCoord + yAddition, (Integer)res.zCoord };
				}
			}
		}
		return "FAIL";//new Object[] { (String)"FAIL", 0, 0, 0 };
	}
	
	public String getResultX(int index2) {
		if (results != null) {
			int index = index2;
			if (index > -1 && index < results.size()) {
				TileEntityReactor res = results.get(index);
				if (res != null) {
					int yAddition = (res.worldObj.provider.dimensionId == WarpDrive.instance.spaceDimID) ? 256 : (res.worldObj.provider.dimensionId == WarpDrive.instance.hyperSpaceDimID) ? 512 : 0;
					//return res.coreFrequency, res.xCoord, res.yCoord + yAddition, res.zCoord;
					return String.valueOf(res.xCoord);//new Object[] { (String)res.coreFrequency, (Integer)res.xCoord, (Integer)res.yCoord + yAddition, (Integer)res.zCoord };
				}
			}
		}
		return "0";//new Object[] { (String)"FAIL", 0, 0, 0 };
	}
	
	public String getResultY(int index2) {
		if (results != null) {
			int index = index2;
			if (index > -1 && index < results.size()) {
				TileEntityReactor res = results.get(index);
				if (res != null) {
					int yAddition = (res.worldObj.provider.dimensionId == WarpDrive.instance.spaceDimID) ? 256 : (res.worldObj.provider.dimensionId == WarpDrive.instance.hyperSpaceDimID) ? 512 : 0;
					//return res.coreFrequency, res.xCoord, res.yCoord + yAddition, res.zCoord;
					return String.valueOf(res.xCoord + yAddition);//new Object[] { (String)res.coreFrequency, (Integer)res.xCoord, (Integer)res.yCoord + yAddition, (Integer)res.zCoord };
				}
			}
		}
		return "0";//new Object[] { (String)"FAIL", 0, 0, 0 };
	}
	
	public String getResultZ(int index2) {
		if (results != null) {
			int index = index2;
			if (index > -1 && index < results.size()) {
				TileEntityReactor res = results.get(index);
				if (res != null) {
					int yAddition = (res.worldObj.provider.dimensionId == WarpDrive.instance.spaceDimID) ? 256 : (res.worldObj.provider.dimensionId == WarpDrive.instance.hyperSpaceDimID) ? 512 : 0;
					//return res.coreFrequency, res.xCoord, res.yCoord + yAddition, res.zCoord;
					return String.valueOf(res.zCoord);//new Object[] { (String)res.coreFrequency, (Integer)res.xCoord, (Integer)res.yCoord + yAddition, (Integer)res.zCoord };
				}
			}
		}
		return "0";//new Object[] { (String)"FAIL", 0, 0, 0 };
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch (method) {
			case 0: // scanRay (toX, toY, toZ)
				return new Object[] { -1 };
			case 1: // scanRadius (radius)
				if (arguments.length == 1) {
					int radius = ((Double)arguments[0]).intValue();
					if (radius <= 0 || radius > 10000) {
						scanRadius = 0;
						return new Boolean[] { false };
					} if (radius != 0 && isEnergyEnoughForScanRadiusW(radius)) {
						// Consume energy
						//this.currentEnergyValue -= radius * radius;
						this.energy.extractEnergy(radius * radius, true);
						// Begin searching
						scanRadius = radius;
						cooldownTime = 0;
						worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 1 + 2);
					} else {
						results = null;
						System.out.println("Radius: " + radius + " | Enough energy: " + isEnergyEnoughForScanRadiusW(radius));
						return new Boolean[] { false };
					}
				}
				else
					return new Boolean[] { false };
				return new Boolean[] { true };

			case 2: // getResultsCount
				if (results != null)
					return new Integer[] { results.size() };
				return new Integer[] { 0 };
			case 3: // getResult
				if (arguments.length == 1 && (results != null)) {
					int index = ((Double)arguments[0]).intValue();
					if (index > -1 && index < results.size())
					{
						TileEntityReactor res = results.get(index);
						if (res != null)
						{
							int yAddition = (res.worldObj.provider.dimensionId == WarpDrive.instance.spaceDimID) ? 256 : (res.worldObj.provider.dimensionId == WarpDrive.instance.hyperSpaceDimID) ? 512 : 0;
							return new Object[] { (String)res.coreFrequency, (Integer)res.xCoord, (Integer)res.yCoord + yAddition, (Integer)res.zCoord };
						}
					}
				}
				return new Object[] { (String)"FAIL", 0, 0, 0 };
			case 4: // getEnergyLevel
				return new Integer[] { getCurrentEnergyValue() };
			case 5: // Pos
				return new Integer[] { xCoord, yCoord, zCoord };
		}

		return null;
	}

	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}

	@Override
	public void attach(IComputerAccess computer) {
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 1 + 2);
	}

	@Override
	public void detach(IComputerAccess computer) {
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 1 + 2);
	}

	// IEnergySink methods implementation TODO
	/*@Override
	public double demandedEnergyUnits()
	{
		return (WarpDriveConfig.i.WR_MAX_ENERGY_VALUE - currentEnergyValue);
	}*/

	/*@Override TODO
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount)
	{
		double leftover = 0;
		currentEnergyValue += Math.round(amount);

		if (getCurrentEnergyValue() > WarpDriveConfig.i.WR_MAX_ENERGY_VALUE)
		{
			leftover = (getCurrentEnergyValue() - WarpDriveConfig.i.WR_MAX_ENERGY_VALUE);
			currentEnergyValue = WarpDriveConfig.i.WR_MAX_ENERGY_VALUE;
		}

		return leftover;
	}*/

	/*@Override TODO
	public int getMaxSafeInput()
	{
		return Integer.MAX_VALUE;
	}*/

	/*@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
	{
		return true;
	}*/
	
    public EnumSet<ForgeDirection> getInputDirections()
    {
        return EnumSet.allOf(ForgeDirection.class);
    }

	/**
	 * @return the currentEnergyValue
	 */
	public int getCurrentEnergyValue()
	{
		return currentEnergyValue;
	}

	@Override
	public void onChunkUnload()
	{
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	public String getInvName() {
		return "radar.name";
	}

	/*@Override
	public void invalidate()
	{
		super.invalidate();
	}*/
	
	/*public void colorScreen(int color) {
	for (int a = 2; a > w-1; a++) {
		for(int b = 1; b < h; b++) {
			paintutils.drawPixel(a,b,color);
		}
	}
}*/
	
    int w = 124;
    int h = 68;
    		 	
	int radius = 500;
	int scale = 25;
	
	public int translateX(int oldX) {
		int x = xCoord - oldX;
		
		x = x / (radius / scale);
		
		x = x + (w / 2);
		
		x = MathHelper.floor(x);
		
		return x;
	}
	
	public int translateZ(int oldZ) {
		int x = yCoord - oldZ;
		
		x = x / (radius / scale);
		
		x = x + (h / 2);
		
		x = MathHelper.floor(x);
		
		return x;
	}
    		 
	public void drawContact(int x, int y, int z, String name, int color) {
		int newX = translateX(x);
		int newZ = translateZ(z);
		
		int contactX = 176;
		int contactZ = 80;
		
		this.drawTexturedModalRect(newX, newZ, contactX, contactZ, 1 + color, 1);
		//paintutils.drawPixel(newX, newZ, color);
		write(name, newX - 3, newZ + 1, 4210752);
		//textOut(newX - 3, newZ + 1, "[" + name + "]", colors.white, colors.black);
	}
    		 
	public int scanAndDraw() {
		if (getCurrentEnergyValue() < radius * radius) {
			int hh = MathHelper.floor(h / 2);
			int hw = MathHelper.floor(w / 2);
    		   
			drawLine(hw - 5, hh - 1, hw + 5, hh - 1, 9843760);
			drawLine(hw - 5, hh, hw + 5, hh, 9843760);
	    
			write("Insufficient Energy", hw-4/*TEST - DEBUG*/, hh/*TEST - DEBUG*/, 14540253);//(hw - 4, hh, "LOW POWER", 14540253, 9843760);
	    
			drawLine(hw - 5, hh + 1, hw + 5, hh + 1, 9843760);
    		   
			return 0;
		} else {
			scanRadius(radius);
			redraw();
			
			int numResults = getResultCount();
			
			if ((numResults < 1) || (numResults > -1)) {
				for (int i = 0; i < numResults-1; i++) {
					//freq, cx, cy, cz = radar.getResult(i);
					String freq = getResultFrequency(i);
					int cx = Integer.parseInt(getResultX(i));
					int cy = Integer.parseInt(getResultY(i));
					int cz = Integer.parseInt(getResultZ(i));
					
					drawContact(cx, cy, cz, freq, 0);
					
					}
				}
			}
		
		drawContact(xCoord/* radarX*/, yCoord/*radarY*/, zCoord/*radarZ*/, "RAD", 1);
		
		
	}
	
	public void drawPixelRed(int x, int y) {
		this.drawTexturedModalRect(x, y, 176, 80, 1, 1);
		//this.isDrawingRed = true;
	}
	
	public void drawPixelYellow(int x, int y) {
		this.drawTexturedModalRect(x, y, 177, 80, 1, 1);
		//this.isDrawingYellow = true;
	}
	
	public void drawPixelForeground(int x, int y) {
		this.drawTexturedModalRect(x, y, 2, 1, 1, 1);
		//this.isDrawingForeground = true;
	}
	
	public void drawLine(int startX, int startY, int endX, int endY, int nColour) {
		//later additions
		int minY;
		int maxX;
		int maxY;
		
		int startX2 = MathHelper.floor(startX);
		int startY2 = MathHelper.floor(startY);
		int endX2 = MathHelper.floor(endX);
		int endY2 = MathHelper.floor(endY);
		
		if(startX2 == endX2 && startY2 == endY2) {
			drawPixelForeground(startX2, startY2);
			return;
		}
		
		int minX = startX2 - endX2;
		
		if(minX == startX2) {
			minY = startY2;
			maxY = endY2;
			maxX = endX2;
		} else {
			minY = endY2;
			maxY = startY2;
			maxX = startX2;
		}
		
		int xDiff = maxX - minX;
		int yDiff = maxY - minY;
		
		if(xDiff > yDiff) {
			int y = minY;
			int dy = yDiff / xDiff;
			
			for(int x = minX; x < maxX; x++) {
				drawPixelForeground(x, MathHelper.floor(y + 0.5));
				
				int y2 = y + dy;
			}
		}
		int x = minX;
		int dx = xDiff / yDiff;
		
		if(maxY >= minY) {
			for(int y = minX; y < maxY; y++) {
				drawPixelForeground(MathHelper.floor(x + 0.5), y);
				
				int x2 = x + dx;
			} 
			
			for(int y = minY; y > maxY; y--) {
				drawPixelForeground(MathHelper.floor(x + 0.5), y);
				
				int x2 = x + dx;	
			}
		}
	}
    		  
    		 
	public void redraw() {
		//shell.run("clear");
    		   //colorScreen(3491355);
    		   
		drawLine(1, 1, w, 1, 1644054);
    		   
		//textOut(h, 1, "= W-Radar v0.1 =", 14540253, 1644054);
    		   
		//textOut(w - 3/*cursor x*/, 1/*cursor y*/, "[X]"/*text*/, 14540253/*text colour*/, 9843760);
    	write("[X]", /*TEST FOR DEBUG*/w-3, /*TEST FOR DEBUG*/1, 14540253);
		
		drawLine(1, h, w, h, 1644054);
	}  		   
    		//textOut(4, h, "Energy: " + furnaceInventory.getEnergyLevel() + " Eu | Scan radius: " + radius, colors.white, colors.black);
}