package spaceage.planets.general;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMetaOres extends WorldGenerator {
	
	int minableBlockId;
	int numberOfBlocks;
	int minableBlockMetadata;
	int blockToReplaceId;
	int blockToReplaceMetadata;
	
	/** Metadata Ore Generation
	 * 
	 * @author SkylordJoel
	 * 
	 * @param par1 - the block id of the ore
	 * @param par2 - the number of blocks to generate
	 * @param par3 - the metadata of the ore
	 * @param par4 - the block id of the block to replace
	 * @param par5 - this metadata of the block to replace
	 */
	public WorldGenMetaOres(int par1, int par2, int par3, int par4, int par5) {
		this.minableBlockId = par1;
		this.numberOfBlocks = par2;
		this.minableBlockMetadata = par3;
		this.blockToReplaceId = par4;
		this.blockToReplaceMetadata = par5;
		
	}

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        float f = par2Random.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(par3 + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d1 = (double)((float)(par3 + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d2 = (double)((float)(par5 + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d3 = (double)((float)(par5 + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d4 = (double)(par4 + par2Random.nextInt(3) - 2);
        double d5 = (double)(par4 + par2Random.nextInt(3) - 2);

        for (int l = 0; l <= this.numberOfBlocks; ++l) {
            double d6 = d0 + (d1 - d0) * (double)l / (double)this.numberOfBlocks;
            double d7 = d4 + (d5 - d4) * (double)l / (double)this.numberOfBlocks;
            double d8 = d2 + (d3 - d2) * (double)l / (double)this.numberOfBlocks;
            double d9 = par2Random.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int x = i1; x <= l1; ++x) {
                double d12 = ((double)x + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int y = j1; y <= i2; ++y) {
                        double d13 = ((double)y + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int z = k1; z <= j2; ++z) {
                                double d14 = ((double)z + 0.5D - d8) / (d10 / 2.0D);
                                
                                if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && (par1World.getBlockId(x, y, z) == this.blockToReplaceId) && (par1World.getBlockMetadata(x, y, z) == this.blockToReplaceMetadata)) {
                                    par1World.setBlock(x, y, z, this.minableBlockId, minableBlockMetadata, 2);
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
	
}
