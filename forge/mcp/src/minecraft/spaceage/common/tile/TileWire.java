package spaceage.common.tile;

import java.util.List;
import java.util.Vector;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import uedevkit.tile.TileCableBase;

public class TileWire extends TileCableBase implements IObscurable {
	public short cableType = 0;
	public short color = 0;

//public byte foamed = 0;
//public byte foamColor = 0; 
	public int[] retextureRefId;
	public int[] retextureRefMeta;
	public int[] retextureRefSide;
	public byte connectivity = 0;
	public byte renderSide = 0;

//private byte prevFoamed = 0;

//public boolean addedToEnergyNet = false;
	private ITickCallback continuousTickCallback = null;
	private static final int EventRemoveConductor = 0;
	
	public TileWire(short type) {
		this.cableType = type;
		}

	public TileWire() {
		}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		//super.readFromNBT(nbttagcompound);
		this.cableType = nbttagcompound.getShort("cableType");
		this.color = nbttagcompound.getShort("color");
  //this.foamColor = nbttagcompound.getByte("foamColor");
  //this.foamed = nbttagcompound.getByte("foamed");

		this.retextureRefId = nbttagcompound.getIntArray("retextureRefId");
		this.retextureRefMeta = nbttagcompound.getIntArray("retextureRefMeta");
		this.retextureRefSide = nbttagcompound.getIntArray("retextureRefSide");

		if (this.retextureRefId.length != 6) this.retextureRefId = null;
		if (this.retextureRefMeta.length != 6) this.retextureRefMeta = null;
		if (this.retextureRefSide.length != 6) this.retextureRefSide = null;
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
  //super.writeToNBT(nbttagcompound);

		nbttagcompound.setShort("cableType", this.cableType);
		nbttagcompound.setShort("color", this.color);
  //nbttagcompound.setByte("foamed", this.foamed);
  //nbttagcompound.setByte("foamColor", this.foamColor);

		if (this.retextureRefId != null) {
			nbttagcompound.setIntArray("retextureRefId", this.retextureRefId);
			nbttagcompound.setIntArray("retextureRefMeta", this.retextureRefMeta);
			nbttagcompound.setIntArray("retextureRefSide", this.retextureRefSide);
		}
	}

	public void onLoaded() {
		//super.onLoaded();
		
		if (!FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

			int newMeta;
			
			switch(meta) {
				case 4:
					newMeta = meta;
					switch(meta) {
						case 4:
							newMeta = 3;
						case 7:
							newMeta = 6;
						case 8:
							newMeta = 6;
					}
				case 7:
					newMeta = meta;
					switch(meta) {
						case 4:
							newMeta = 3;
						case 7:
							newMeta = 6;
						case 8:
							newMeta = 6;
				}
				case 8:
					newMeta = meta;
					switch(meta) {
						case 4:
							newMeta = 3;
						case 7:
							newMeta = 6;
						case 8:
							newMeta = 6;
				}
			}
			
			/*if ((meta == 4) || (meta == 7) || (meta == 8)) {
				int newMeta = meta;
				if (meta == 4) newMeta = 3;
				if ((meta == 7) || (meta == 8)) newMeta = 6;*/

      this.cableType = ((short)newMeta);

      this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, newMeta, 2);
      IC2.network.updateTileEntityField(this, "cableType");
    }

    //MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
    //this.addedToEnergyNet = true;

    onNeighborBlockChange();

  }
}

public void onUnloaded() {
  /*if ((IC2.platform.isSimulating()) && (this.addedToEnergyNet)) {
    MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
    this.addedToEnergyNet = false;
  }*/

  //if (this.continuousTickCallback != null) {
    //IC2.removeContinuousTickCallback(this.worldObj, this.continuousTickCallback);
    //this.continuousTickCallback = null;
  //}

  //super.onUnloaded();
}

public void onNeighborBlockChange() {
  byte newConnectivity = 0;
  byte newRenderSide = 0;

  int mask = 1;

  for (Direction direction : Direction.values()) {
    TileEntity neighbor = EnergyNet.instance.getNeighbor(this, direction.toForgeDirection());

    if ((((neighbor instanceof IEnergyAcceptor)) && (((IEnergyAcceptor)neighbor).acceptsEnergyFrom(this, direction.getInverse().toForgeDirection()))) || (((neighbor instanceof IEnergyEmitter)) && (((IEnergyEmitter)neighbor).emitsEnergyTo(this, direction.getInverse().toForgeDirection())) && (canInteractWith(neighbor))))
    {
      newConnectivity = (byte)(newConnectivity | mask);

      if (((neighbor instanceof TileWire)) && (((TileWire)neighbor).getCableThickness() < getCableThickness())) {
        newRenderSide = (byte)(newRenderSide | mask);
      }
    }

    mask *= 2;
  }

  if (this.connectivity != newConnectivity) {
    this.connectivity = newConnectivity;
    IC2.network.updateTileEntityField(this, "connectivity");
  }

  if (this.renderSide != newRenderSide) {
    this.renderSide = newRenderSide;
    IC2.network.updateTileEntityField(this, "renderSide");
  }
}

public boolean shouldRefresh(int oldID, int newID, int oldMeta, int newMeta, World world, int x, int y, int z)
{
  if (oldID != newID) {
    return super.shouldRefresh(oldID, newID, oldMeta, newMeta, world, x, y, z);
  }
  return false;
}

public void changeType(short cableType1) {
  this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, cableType1, 7);

  //if (this.addedToEnergyNet) MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
  //this.addedToEnergyNet = false;

  this.cableType = cableType1;

  //MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
  //this.addedToEnergyNet = true;

  //IC2.network.updateTileEntityField(this, "cableType");
}

/*public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side)
{
  return false;
}

public boolean wrenchCanRemove(EntityPlayer entityPlayer)
{
  return false;
}

public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction)
{
  return canInteractWith(emitter);
}

public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction)
{
  return canInteractWith(receiver);
}

public boolean canInteractWith(TileEntity te) {
  if (!(te instanceof IEnergyTile)) return false;

  if ((te instanceof TileWire)) return canInteractWithCable((TileWire)te);
  if ((te instanceof TileEntityLuminator)) return ((TileEntityLuminator)te).canCableConnectFrom(this.xCoord, this.yCoord, this.zCoord);

  return true;
}
*/
public boolean canInteractWithCable(TileWire cable)
{
  return (this.color == 0) || (cable.color == 0) || (this.color == cable.color);
}

public float getCableThickness()
{
  return getCableThickness(this.cableType);
}

public static float getCableThickness(int cableType) {
  float p = 1.0F;

  switch (cableType) { case 0:
    p = 6.0F; break;
  case 1:
    p = 4.0F; break;
  case 2:
    p = 3.0F; break;
  case 3:
    p = 6.0F; break;
  case 4:
    p = 6.0F; break;
  case 5:
    p = 6.0F; break;
  case 6:
    p = 10.0F; break;
  case 7:
    p = 10.0F; break;
  case 8:
    p = 12.0F; break;
  case 9:
    p = 4.0F; break;
  case 10:
    p = 4.0F; break;
  case 11:
    p = 8.0F; break;
  case 12:
    p = 8.0F; break;
  case 13:
    p = 16.0F; break;
  case 14:
    p = 6.0F;
  }
  return p / 16.0F;
}

public double getConductionLoss()
{
  switch (this.cableType) {
  case 0:
    return 0.2D;
  case 1:
    return 0.3D;
  case 2:
    return 0.5D;
  case 3:
    return 0.45D;
  case 4:
    return 0.4D;
  case 5:
    return 1.0D;
  case 6:
    return 0.95D;
  case 7:
    return 0.9D;
  case 8:
    return 0.8D;
  case 9:
    return 0.025D;
  case 10:
    return 0.025D;
  case 11:
    return 0.5D;
  case 12:
    return 0.5D;
  case 14:
    return 0.2D;
  case 13: } return 0.025D;
}

public static int getMaxCapacity(int type)
{
  switch (type) { case 0:
    return 128;
  case 1:
    return 128;
  case 2:
    return 512;
  case 3:
    return 512;
  case 4:
    return 512;
  case 5:
    return 2048;
  case 6:
    return 2048;
  case 7:
    return 2048;
  case 8:
    return 2048;
  case 9:
    return 8192;
  case 10:
    return 32;
  case 11:
    return 8192;
  case 12:
    return 8192;
  case 13:
    return 32;
  case 14:
    return 32; }
  return 0;
}

public int getInsulationEnergyAbsorption()
{
  return getMaxCapacity(this.cableType);
}

public int getInsulationBreakdownEnergy()
{
  return 9001;
}

public int getConductorBreakdownEnergy()
{
  return getMaxCapacity(this.cableType) + 1;
}

public void removeInsulation()
{
}

public void removeConductor()
{
  this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, 0, 0, 7);

  //IC2.network.initiateTileEntityEvent(this, 0, true);
}

public List<String> getNetworkedFields()
{
  List ret = new Vector();

  ret.add("cableType");
  //ret.add("color");
  //ret.add("foamed");
  //ret.add("foamColor");
  ret.add("retextureRefId");
  ret.add("retextureRefMeta");
  ret.add("retextureRefSide");
  ret.add("connectivity");
  ret.add("renderSide");

  ret.addAll(super.getNetworkedFields());

  return ret;
}

public void onNetworkUpdate(String field)
{
	this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

  super.onNetworkUpdate(field);
}

public void onNetworkEvent(int event)
{
  switch (event) {
  case 0:
    this.worldObj.playSoundEffect(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, "random.fizz", 0.5F, 2.6F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.8F);

    for (int l = 0; l < 8; l++) {
      this.worldObj.spawnParticle("largesmoke", this.xCoord + Math.random(), this.yCoord + 1.2D, this.zCoord + Math.random(), 0.0D, 0.0D, 0.0D);
    }
    break;
  default:
    IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\n" + "T: " + this + " (" + this.xCoord + "," + this.yCoord + "," + this.zCoord + ")");
  }
}

public float getWrenchDropRate()
{
  return 0.0F;
}

private boolean changeFoam(byte foamed1, boolean duringLoad)
{
  if ((this.foamed == foamed1) && (!duringLoad)) return false;
  if (!IC2.platform.isSimulating()) return true;

  byte prevFoamed1 = this.foamed;
  this.foamed = foamed1;

  if (this.continuousTickCallback != null) {
    IC2.removeContinuousTickCallback(this.worldObj, this.continuousTickCallback);
    this.continuousTickCallback = null;
  }

  if ((foamed1 == 0) || (foamed1 == 1)) {
    if (this.retextureRefId != null) {
      this.retextureRefId = null;
      this.retextureRefMeta = null;
      this.retextureRefSide = null;

      if (!duringLoad) {
        IC2.network.updateTileEntityField(this, "retextureRefId");
        IC2.network.updateTileEntityField(this, "retextureRefMeta");
        IC2.network.updateTileEntityField(this, "retextureRefSide");
      }
    }

    if (this.foamColor != 7) {
      this.foamColor = 7;

      if (!duringLoad) IC2.network.updateTileEntityField(this, "foamColor");
    }

  }

  if (((foamed1 == 0) && (prevFoamed1 != 1)) || (foamed1 == 2))
  {
    BlockCable blockCable = (BlockCable)Block.blocksList[Ic2Items.insulatedCopperCableBlock.itemID];
    blockCable.enableBreakBlock = false;

    if (this.cableType == 14) {
      this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, (this.cableType - 1 + 1) % 16, 7);
      this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, this.cableType - 1, 7);
    } else {
      this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, (this.cableType + 1) % 16, 7);
      this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Ic2Items.insulatedCopperCableBlock.itemID, this.cableType, 7);
    }

    onLoaded();

    blockCable.enableBreakBlock = true;
  } else if (foamed1 == 1) {
    this.continuousTickCallback = new ITickCallback()
    {
      public void tickCallback(World world) {
        if ((world.rand.nextInt(500) == 0) && (world.getBlockLightValue(TileWire.this.xCoord, TileWire.this.yCoord, TileWire.this.zCoord) * 6 >= TileWire.this.worldObj.rand.nextInt(1000)))
        {
          TileWire.this.changeFoam((byte)2);
        }
      }
    };
    IC2.addContinuousTickCallback(this.worldObj, this.continuousTickCallback);
  }

  if (!duringLoad) IC2.network.updateTileEntityField(this, "foamed");

  return true;
}

public boolean retexture(int side, int referencedBlockId, int referencedMeta, int referencedSide)
{
  if (this.foamed != 2) return false;

  boolean ret = false;
  boolean updateAll = false;

  if (this.retextureRefId == null) {
    this.retextureRefId = new int[6];
    this.retextureRefMeta = new int[6];
    this.retextureRefSide = new int[6];
    updateAll = true;
  }

  if ((this.retextureRefId[side] != referencedBlockId) || (updateAll)) {
    this.retextureRefId[side] = referencedBlockId;
    IC2.network.updateTileEntityField(this, "retextureRefId");
    ret = true;
  }

  if ((this.retextureRefMeta[side] != referencedMeta) || (updateAll)) {
    this.retextureRefMeta[side] = referencedMeta;
    IC2.network.updateTileEntityField(this, "retextureRefMeta");
    ret = true;
  }

  if ((this.retextureRefSide[side] != referencedSide) || (updateAll)) {
    this.retextureRefSide[side] = referencedSide;
    IC2.network.updateTileEntityField(this, "retextureRefSide");
    ret = true;
  }

  return ret;
}

public Block getReferencedBlock(int side)
{
  if ((this.retextureRefId != null) && (this.retextureRefId[side] != 0) && (this.retextureRefMeta != null) && (this.retextureRefSide != null))
  {
    return Block.blocksList[this.retextureRefId[side]];
  }
  return null;
}

public void setColorMultiplier(int colorMultiplier)
{
  ((BlockCable)getBlockType()).colorMultiplier = colorMultiplier;
}

public void setRenderMask(int mask)
{
  ((BlockMultiID)getBlockType()).renderMask = mask;
}
}
