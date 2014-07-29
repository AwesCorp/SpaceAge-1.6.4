package spaceage.planets.vulcan;

import java.util.Random;

import spaceage.common.SpaceAgeCore;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;

public class WorldGenGlowstoneTree extends WorldGenerator
{
	public WorldGenGlowstoneTree(boolean par1)
	{
		super();
	}

	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int l = par2Random.nextInt(3) + 5;
		boolean flag = true;

		if (par4 >= 1 && par4 + l + 1 <= 256)
		{
			int i1;
			int j1;
			int k1;
			int l1;

			for (i1 = par4; i1 <= par4 + 1 + l; ++i1)
			{
				byte b0 = 1;

				if (i1 == par4)
				{
					b0 = 0;
				}

				if (i1 >= par4 + 1 + l - 2)
				{
					b0 = 2;
				}

				for (j1 = par3 - b0; j1 <= par3 + b0 && flag; ++j1)
				{
					for (k1 = par5 - b0; k1 <= par5 + b0 && flag; ++k1)
					{
						if (i1 >= 0 && i1 < 256)
						{
							l1 = par1World.getBlockId(j1, i1, k1);

							Block block = Block.blocksList[l1];
							
					          int blockId = SpaceAgeCore.vulcanSurface.blockID;
					          boolean properBlockId = (par1World.getBlockId(j1, i1, k1) == (blockId));
					          
					          int blockMeta = 0;
					          boolean properBlockMeta = (par1World.getBlockMetadata(j1, i1, k1) == (blockMeta));

							if (l1 != 0 && block != null && ((properBlockId == false/*block.isLeaves(par1World, par3, par4 + var16, par5)*/) && (properBlockMeta == false)))
							//if (l1 != 0 && (block != null && !block.isLeaves(par1World, j1,  i1, k1)))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				i1 = par1World.getBlockId(par3, par4 - 1, par5);

				boolean isValidSoil = (i1 == Block.netherrack.blockID);

				if (isValidSoil && par4 < 256 - l - 1)
				{
					//soil.onPlantGrow(par1World, par3, par4 - 1, par5, par3, par4, par5);
					int i2;

					for (i2 = par4 - 3 + l; i2 <= par4 + l; ++i2)
					{
						j1 = i2 - (par4 + l);
						k1 = 1 - j1 / 2;

						for (l1 = par3 - k1; l1 <= par3 + k1; ++l1)
						{
							int j2 = l1 - par3;

							for (int k2 = par5 - k1; k2 <= par5 + k1; ++k2)
							{
								int l2 = k2 - par5;

								if (Math.abs(j2) != k1 || Math.abs(l2) != k1 || par2Random.nextInt(2) != 0 && j1 != 0)
								{
									int i3 = par1World.getBlockId(l1, i2, k2);
									Block block = Block.blocksList[i3];

									if (block == null || block.canBeReplacedByLeaves(par1World, l1, i2, k2))
									{
										this.setBlockAndMetadata(par1World, l1, i2, k2, SpaceAgeCore.vulcanSurface.blockID, 0);
									}
								}
							}
						}
					}

					for (i2 = 0; i2 < l; ++i2)
					{
						j1 = par1World.getBlockId(par3, par4 + i2, par5);

						Block block = Block.blocksList[j1];
						
				          int blockId = SpaceAgeCore.vulcanSurface.blockID;
				          boolean properBlockId = (par1World.getBlockId(par3, par4 + i2, par5) == (blockId));
				          
				          int blockMeta = 0;
				          boolean properBlockMeta = (par1World.getBlockMetadata(par3, par4 + i2, par5) == (blockMeta));

						if (j1 == 0 || block == null || ((properBlockId == true/*block.isLeaves(par1World, par3, par4 + var16, par5)*/) && (properBlockMeta == true)))
						{
							this.setBlockAndMetadata(par1World, par3, par4 + i2, par5, SpaceAgeCore.vulcanSurface.blockID, 1);
						}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}
}