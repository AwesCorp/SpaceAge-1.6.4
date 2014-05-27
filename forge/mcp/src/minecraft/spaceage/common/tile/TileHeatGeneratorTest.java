package spaceage.common.tile;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import resonant.lib.network.IPacketReceiver;
import resonant.lib.network.Synced;
import resonant.lib.prefab.vector.Cuboid;
import spaceage.common.SpaceAgeCore;
import universalelectricity.api.energy.EnergyStorageHandler;

public class TileHeatGeneratorTest extends TileEnergyDistribution implements IFluidHandler, ISidedInventory, IPacketReceiver {
	
	@Synced
	public final static FluidTank waterTank = new FluidTank(SpaceAgeCore.FLUIDSTACK_WATER.copy(), FluidContainerRegistry.BUCKET_VOLUME * 5);
	
	@Synced
	public int timer = 0;
	
	@SideOnly(Side.CLIENT)
	public static Icon sside, bottom;
	
	public TileHeatGeneratorTest()
	{
		super(Material.iron);
		energy = new EnergyStorageHandler(SpaceAgeCore.HEAT_ENERGY * 20);
		ioMap = 728;
		textureName = "heat_top";
		bounds = new Cuboid(0, 0, 0, 1, 0.3f, 1);
		isOpaqueCube = false;
		normalRender = false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconReg)
	{
		sside = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "heat_side");
		bottom = iconReg.registerIcon(SpaceAgeCore.modid + ":" + "heat_bottom");
		super.registerIcons(iconReg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		switch(side) {
			case 0: return bottom;
			default: return sside;
			}
	/*{
		if (side == 0)
		{
			return solarPanel_bottom;
		}
		else if (side == 1)
		{
			return getIcon();
		}

		return solarPanel_side;*/
	}

	@Override
	public void updateEntity() {
		boolean powered = worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == Block.lavaStill.blockID;
		
		if(powered == true) {
			this.energy.receiveEnergy(SpaceAgeCore.HEAT_ENERGY, true);
			markDistributionUpdate |= produce() > 0;
				}
		
		super.updateEntity();	
		
		if (getStackInSlot(1) != null)

        {
            if (FluidContainerRegistry.isFilledContainer(getStackInSlot(1)))

            {
                FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1));


                if (liquid.isFluidEqual(SpaceAgeCore.FLUIDSTACK_WATER))

                {
                    if (this.fill(ForgeDirection.UNKNOWN, liquid, false) > 0)

                    {
                        ItemStack resultingContainer = getStackInSlot(1).getItem().getContainerItemStack(getStackInSlot(1));


                        if (resultingContainer == null && getStackInSlot(1).stackSize > 1)

                        {
                            getStackInSlot(1).stackSize--;

                        }
                        else

                        {
                            setInventorySlotContents(1, resultingContainer);

                        }


                        this.waterTank.fill(liquid, true);

                    }
                }

            }
        }
		
	}
	
	@Override
	public void onReceivePacket(ByteArrayDataInput data, EntityPlayer player, Object... extra) {
		try {
			this.waterTank.setFluid(new FluidStack(SpaceAgeCore.FLUIDSTACK_WATER.fluidID, data.readInt()));
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
    public Packet getDescriptionPacket()

    {
        return SpaceAgeCore.PACKET_TILE.getPacket(this, this.timer, SpaceAgeCore.getFluidAmount(this.waterTank.getFluid()));

    }


    public void sendDescPack()

    {
        if (!this.worldObj.isRemote)

        {
            for (EntityPlayer player : this.getPlayersUsing())

            {
                PacketDispatcher.sendPacketToPlayer(getDescriptionPacket(), (Player) player);

            }
        }

    }


    // Check all conditions and see if we can start smelting
    public boolean waterInTank()

    {
        if (this.waterTank.getFluid() != null)

        {
            if (this.waterTank.getFluid().amount >= FluidContainerRegistry.BUCKET_VOLUME)

            {
                if (getStackInSlot(1) != null)

                {       
                            return true;

                }
            }

        }


        return false;

    }

    /** Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack. */


    /** Reads a tile entity from NBT. */

    @Override
    public void readFromNBT(NBTTagCompound nbt)

    {
        super.readFromNBT(nbt);

        //this.timer = nbt.getInteger("shiJian");


        NBTTagCompound waterCompound = nbt.getCompoundTag("water");

        this.waterTank.setFluid(FluidStack.loadFluidStackFromNBT(waterCompound));

    }


    /** Writes a tile entity to NBT. */
    @Override

    public void writeToNBT(NBTTagCompound nbt)

    {
        super.writeToNBT(nbt);

        //nbt.setInteger("shiJian", this.timer);


        if (this.waterTank.getFluid() != null)

        {
            NBTTagCompound compound = new NBTTagCompound();

            this.waterTank.getFluid().writeToNBT(compound);

            nbt.setTag("water", compound);

        }

    }


    /** Tank Methods */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)

    {
        if (SpaceAgeCore.FLUIDSTACK_WATER.isFluidEqual(resource))

        {
            return this.waterTank.fill(resource, doFill);

        }


        return 0;

    }

    @Override

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)

    {
        return null;

    }


    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)

    {
        return SpaceAgeCore.FLUIDSTACK_WATER.fluidID == fluid.getID();

    }


    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)

    {
        return false;

    }


    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)

    {
        return new FluidTankInfo[]

        { this.waterTank.getInfo()};

    }


    /** Inventory */
    @Override

    public boolean isItemValidForSlot(int slotID, ItemStack itemStack) {
        
    	return false;

    }

    @Override

    public int[] getAccessibleSlotsFromSide(int side)

    {
        return null;

    }


    @Override
    public boolean canInsertItem(int slotID, ItemStack itemStack, int side)

    {
        return this.isItemValidForSlot(slotID, itemStack);

    }


    @Override
    public boolean canExtractItem(int slotID, ItemStack itemstack, int j)

    {
        return slotID == 2;

    }


    @Override
    public long onExtractEnergy(ForgeDirection from, long extract, boolean doExtract)

    {
        return 0;

    }

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}
}