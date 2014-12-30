package spaceage.common.block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import spaceage.common.SpaceAgeCore;
//import spaceage.common.block.BlockGenerator.Types;
//import spaceage.common.block.BlockHades.Type;
import spaceage.planets.SpaceAgePlanets;
import spaceage.planets.eden.WorldGenEdenTrees;
import spaceage.planets.technoorganic.WorldGen0011Tree;
import spaceage.planets.vulcan.WorldGenGlowstoneTree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockSASapling extends Block {
    public static final String[] WOOD_TYPES = new String[] {"glowQuartz", "techOrganic", "edenTree"};
    //public static int amountOfSubBlocks = 2;
    
	private Type type;
	
	public Random randomG;
	
	public World worldObj;
	
	//private int netherrackID = Block.netherrack.blockID;
    
    @SideOnly(Side.CLIENT)
    private Icon[] saplingIcon;

    public BlockSASapling(int par1) {
        super(par1, Material.plants);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(SpaceAgeCore.tabSA);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
    	ItemStack item = player.getHeldItem();
    	
    	switch(Type.values().length) {
    		case 0:
    	    	if((item.getItem() == SpaceAgeCore.meta) && (item.getItemDamage() == 14)) {
    	    		growTree(world, x, y, z, randomG);
    	    	}
    		case 1:
    			if((item.getItem() == SpaceAgeCore.meta) && (item.getItemDamage() == 15)) {
    				growTree(world, x, y, z, randomG);
    			}
    		case 2:
    			if((item.getItem() == Item.dyePowder) && (item.getItemDamage() == 15)) {
    				growTree(world, x, y, z, randomG);
    			}
    	}
    	
    	return false;
    }
    
    public boolean canThisPlantGrowOnThisBlockID(int blockID) {
    	switch(Type.values().length) {
    		case 0:
    			if(blockID == Block.netherrack.blockID) {
    				return true;
    			}
    		case 1:
    			if(blockID == SpaceAgeCore.T0011SurfaceID) {
    				//if(worldObj.getBlockM)
    				return true;
    			}
    		case 2:
    			if(blockID == Block.dirt.blockID || blockID == Block.grass.blockID) {
    				return true;
    			}
			default:
				return false;
    	}
    }
    
    @Override
    public boolean canBlockStay(World par1World, int x, int y, int z) {
        //Block soil = blocksList[par1World.getBlockId(x, y - 1, z)];
        int soil = par1World.getBlockId(x, y - 1, z);
        int soilMeta = par1World.getBlockMetadata(x, y - 1, z);
        
        int meta = par1World.getBlockMetadata(x, y, z) & 3;
        
        switch(/*Type.values().length*/meta) {
        	case 0:
        		if(par1World.getBlockLightValue(x, y + 1, z) >= 9 || par1World.canBlockSeeTheSky(x, y, z)); {
        			if(Loader.isModLoaded("SpaceAgePlanets")) {
        				if((par1World.provider.dimensionId != SpaceAgePlanets.instance.hadesID)) {
                    		return (soil == Block.netherrack.blockID);
        				}
        			}
            		return (soil == Block.netherrack.blockID);
        		}
        	case 1:
        		if(par1World.getBlockLightValue(x, y + 1, z) >= 9 || par1World.canBlockSeeTheSky(x, y, z)); {
        			if(Loader.isModLoaded("SpaceAgePlanets")) {
        				if((par1World.provider.dimensionId != SpaceAgePlanets.instance.hadesID)) {
        					return (soil == SpaceAgeCore.T0011SurfaceID && soilMeta == 0);   
        				}
        			}
        			return (soil == SpaceAgeCore.T0011SurfaceID && soilMeta == 0);        			
        		}
        	case 2:
        		if(par1World.getBlockLightValue(x, y + 1, z) >= 9 || par1World.canBlockSeeTheSky(x, y, z)); {
        			if(Loader.isModLoaded("SpaceAgePlanets")) {
        				if((par1World.provider.dimensionId != SpaceAgePlanets.instance.hadesID)) {
        					return (soil == Block.dirt.blockID || soil == Block.grass.blockID);  
        				}
        			}
        			return (soil == Block.dirt.blockID || soil == Block.grass.blockID);     			
        		}
    		default:
    			return false;
        }
        //return (par1World.getFullBlockLightValue(x, y, z) >= 8 || par1World.canBlockSeeTheSky(x, y, z)) && 
          //      (soil != null && soil.canSustainPlant(par1World, x, y - 1, z, ForgeDirection.UP, this));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (!par1World.isRemote) {
            super.updateTick(par1World, par2, par3, par4, par5Random);

                this.markOrGrowMarked(par1World, par2, par3, par4, par5Random);
        }
    }

    @SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getIcon(int par1, int par2) {
        par2 &= 3;//par2 = par2 & 3;
        return this.saplingIcon[par2];
    }

    public void markOrGrowMarked(World par1World, int par2, int par3, int par4, Random par5Random) {
        int l = par1World.getBlockMetadata(par2, par3, par4);

        if ((l & 8) == 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, l | 8, 4);
        } else {
            this.growTree(par1World, par2, par3, par4, par5Random);
        }
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World par1World, int par2, int par3, int par4, Random par5Random) {
        //if (!TerrainGen.saplingGrowTree(par1World, par5Random, par2, par3, par4)) 
        	//return; //TODO - unneeded code?

        int meta = par1World.getBlockMetadata(par2, par3, par4) & 3;
        
        Object object = null;
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;
        
        if(object == null) {
        	switch(meta) {
        		case 0:
        			object = new WorldGenGlowstoneTree(true);
        			break;
        		case 1:
        			object = new WorldGen0011Tree(true);
        			break;
        		case 2:
        			object = new WorldGenEdenTrees(true);
        	}
        }
        
        if(object != null) {
        	par1World.setBlockToAir(par2, par3, par4);
        	
        	if(!((WorldGenerator)object).generate(par1World, par5Random, par2, par3, par4)) {
        		par1World.setBlock(par2, par3, par4, this.blockID, meta, 2);
        	}
        }

        /*if (meta == 1) {
            object = new WorldGenTaiga2(true);
        } else if (meta == 2) {
            object = new WorldGenForest(true);
        } else if (meta == 3) {
            for (i1 = 0; i1 >= -1; --i1) {
                for (j1 = 0; j1 >= -1; --j1) {
                    if (this.isSameSapling(par1World, par2 + i1, par3, par4 + j1, 3) && this.isSameSapling(par1World, par2 + i1 + 1, par3, par4 + j1, 3) && this.isSameSapling(par1World, par2 + i1, par3, par4 + j1 + 1, 3) && this.isSameSapling(par1World, par2 + i1 + 1, par3, par4 + j1 + 1, 3)) {
                        object = new WorldGenHugeTrees(true, 10 + par5Random.nextInt(20), 3, 3);
                        flag = true;
                        break;
                    }
                }

                if (object != null) {
                    break;
                }
            }

            if (object == null) {
                j1 = 0;
                i1 = 0;
                object = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
            }
        } else {
            object = new WorldGenTrees(true);

            if (par5Random.nextInt(10) == 0) {
                object = new WorldGenBigTree(true);
            }
        }

        if (flag) {
            par1World.setBlock(par2 + i1, par3, par4 + j1, 0, 0, 4);
            par1World.setBlock(par2 + i1 + 1, par3, par4 + j1, 0, 0, 4);
            par1World.setBlock(par2 + i1, par3, par4 + j1 + 1, 0, 0, 4);
            par1World.setBlock(par2 + i1 + 1, par3, par4 + j1 + 1, 0, 0, 4);
        } else {
            par1World.setBlock(par2, par3, par4, 0, 0, 4);
        }

        if (!((WorldGenerator)object).generate(par1World, par5Random, par2 + i1, par3, par4 + j1)) {
            if (flag) {
                par1World.setBlock(par2 + i1, par3, par4 + j1, this.blockID, meta, 4);
                par1World.setBlock(par2 + i1 + 1, par3, par4 + j1, this.blockID, meta, 4);
                par1World.setBlock(par2 + i1, par3, par4 + j1 + 1, this.blockID, meta, 4);
                par1World.setBlock(par2 + i1 + 1, par3, par4 + j1 + 1, this.blockID, meta, 4);
            } else {
                par1World.setBlock(par2, par3, par4, this.blockID, meta, 4);
            }
        }*/
    }

    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5) {
        return par1World.getBlockId(par2, par3, par4) == this.blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1) {
        return par1 & 3;
    }

    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < WOOD_TYPES.length; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
    }

    @SideOnly(Side.CLIENT)
    @Override
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister) {
        this.saplingIcon = new Icon[WOOD_TYPES.length];

        for (int i = 0; i < this.saplingIcon.length; ++i) {
            this.saplingIcon[i] = par1IconRegister.registerIcon(SpaceAgeCore.modid + ":sapling/" + WOOD_TYPES[i]);
        }
    }
    
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        
        this.checkChange(par1World, par2, par3, par4);
    }
    
	public void checkChange(World world, int x, int y, int z) {
		if(!this.canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			
            world.setBlock(x, y, z, 0, 0, 2);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		checkChange(world, x, y, z);
	}
	
    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType() {
        return 1;
    }
    
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
    }

	public static enum Type {
		GLOW_QUARTZ, TECH_ORGANIC, EDEN_TREE;
	}
}