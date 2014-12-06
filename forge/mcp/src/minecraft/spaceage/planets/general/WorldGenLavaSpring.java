package spaceage.planets.general;

import com.google.common.base.Optional;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLavaSpring extends WorldGenerator {
  private int minableBlockId;
  private int numberOfBlocks;
  private int blockIDToReplace;
  private int blockMetaToReplace;

  public WorldGenLavaSpring(int par1, int par2, int par3) {
    this.minableBlockId = par1;
    this.numberOfBlocks = par2;
    this.blockIDToReplace = par3;
    this.blockMetaToReplace = 0;
  }
  
  public WorldGenLavaSpring(int par1, int par2, int par3, int par4) {
	    this.minableBlockId = par1;
	    this.numberOfBlocks = par2;
	    this.blockIDToReplace = par3;
	    this.blockMetaToReplace = par4;
	    }

  public boolean generate(World par1World, Random par2Random, int inputX, int inputY, int inputZ) {
	  float pi = 3.141593F;
	  
	  float piFloat = par2Random.nextFloat() * pi;
	  double complexDouble1 = inputX + 8 + MathHelper.sin(piFloat) * this.numberOfBlocks / 8.0F;
	  double complexDouble2 = inputX + 8 - MathHelper.sin(piFloat) * this.numberOfBlocks / 8.0F;
	  double complexDouble3 = inputZ + 8 + MathHelper.cos(piFloat) * this.numberOfBlocks / 8.0F;
	  double complexDouble4 = inputZ + 8 - MathHelper.cos(piFloat) * this.numberOfBlocks / 8.0F;
	  double complexDouble5 = inputY + par2Random.nextInt(3) - 2;
	  double complexDouble6 = inputY + par2Random.nextInt(3) - 2;

	  for (int newNumberOfBlocks = 0; newNumberOfBlocks <= this.numberOfBlocks; newNumberOfBlocks++) {
		  double gen2ComplexDouble1 = complexDouble1 + (complexDouble2 - complexDouble1) * newNumberOfBlocks / this.numberOfBlocks;
		  double gen2ComplexDouble2 = complexDouble5 + (complexDouble6 - complexDouble5) * newNumberOfBlocks / this.numberOfBlocks;
		  double gen2ComplexDouble3 = complexDouble3 + (complexDouble4 - complexDouble3) * newNumberOfBlocks / this.numberOfBlocks;
		  double gen2ComplexDouble4 = par2Random.nextDouble() * this.numberOfBlocks / 16.0D;
		  double gen2ComplexDouble5 = (MathHelper.sin(newNumberOfBlocks * pi / this.numberOfBlocks) + 1.0F) * gen2ComplexDouble4 + 1.0D;
		  double gen2ComplexDouble6 = (MathHelper.sin(newNumberOfBlocks * pi / this.numberOfBlocks) + 1.0F) * gen2ComplexDouble4 + 1.0D;
		  int complexInt1 = MathHelper.floor_double(gen2ComplexDouble1 - gen2ComplexDouble5 / 2.0D);
		  int complexInt2 = MathHelper.floor_double(gen2ComplexDouble2 - gen2ComplexDouble6 / 2.0D);
		  int complexInt3 = MathHelper.floor_double(gen2ComplexDouble3 - gen2ComplexDouble5 / 2.0D);
		  int complexInt4 = MathHelper.floor_double(gen2ComplexDouble1 + gen2ComplexDouble5 / 2.0D);
		  int complexInt5 = MathHelper.floor_double(gen2ComplexDouble2 + gen2ComplexDouble6 / 2.0D);
		  int complexInt6 = MathHelper.floor_double(gen2ComplexDouble3 + gen2ComplexDouble5 / 2.0D);

		  for (int x = complexInt1; x <= complexInt4; x++) {
			  double gen3ComplexDouble = (x + 0.5D - gen2ComplexDouble1) / (gen2ComplexDouble5 / 2.0D);

			  if (gen3ComplexDouble * gen3ComplexDouble < 1.0D) {
				  for (int y = complexInt2; y <= complexInt5; y++) {
					  double gen4ComplexDouble = (y + 0.5D - gen2ComplexDouble2) / (gen2ComplexDouble6 / 2.0D);

					  if (gen3ComplexDouble * gen3ComplexDouble + gen4ComplexDouble * gen4ComplexDouble < 1.0D) {
						  for (int z = complexInt3; z <= complexInt6; z++) {
							  double gen5ComplexDouble = (z + 0.5D - gen2ComplexDouble3) / (gen2ComplexDouble5 / 2.0D);

							  if ((gen3ComplexDouble * gen3ComplexDouble + gen4ComplexDouble * gen4ComplexDouble + gen5ComplexDouble * gen5ComplexDouble < 1.0D) && (par1World.getBlockId(x, y, z) == this.blockIDToReplace) && (par1World.getBlockId(x, y, z) == this.blockMetaToReplace)) {
								  par1World.setBlock(x, y, z, this.minableBlockId);
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