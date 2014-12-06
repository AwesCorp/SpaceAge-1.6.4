package spaceage.planets.ontarine;

import java.util.Random;

import spaceage.common.SpaceAgeCore;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBarrierReef extends WorldGenerator {
	
	int numberOfBlocks;
	
	public WorldGenBarrierReef(boolean par1, int numberOfBlocksSuper) {
		super(par1);
		this.numberOfBlocks = numberOfBlocksSuper;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		float piFloat = random.nextFloat() * 3.141593F;
		double d1 = (double)((float)(x + 8) + MathHelper.sin(piFloat) * (float)numberOfBlocks / 8.0F);
		double d2 = (double)((float)(x + 8) - MathHelper.sin(piFloat) * (float)numberOfBlocks / 8.0F);
		double d3 = (double)((float)(z + 8) + MathHelper.cos(piFloat) * (float)numberOfBlocks / 8.0F);
		double d4 = (double)((float)(z + 8) - MathHelper.cos(piFloat) * (float)numberOfBlocks / 8.0F);
		double d5 = (double)(y + random.nextInt(3) + 2);
		double d6 = (double)(y + random.nextInt(3) + 2);

		for(int i = 0; i <= numberOfBlocks; i++) {
			double d7 = d1 + (d2 - d1) * (double)i / (double)numberOfBlocks;
			double d8 = d5 + (d6 - d5) * (double)i / (double)numberOfBlocks;
			double d9 = d3 + (d4 - d3) * (double)i / (double)numberOfBlocks;
			double d10 = random.nextDouble() * (double)numberOfBlocks / 16.0D;
			double d11 = (double)(MathHelper.sin((float)i * 3.141593F / (float)numberOfBlocks) + 1.0F) * d10 + 1.0D;
			double d12 = (double)(MathHelper.sin((float)i * 3.141593F / (float)numberOfBlocks) + 1.0F) * d10 + 1.0D;

			for(int j = (int)(d7 - d11 / 2.0D); j <= (int)(d7 + d11 / 2.0D); ++j) {
				for(int k = (int)(d8 - d12 / 2.0D); k <= (int)(d8 + d12 / 2.0D); ++k) {
					for(int m = (int)(d9 - d11 / 2.0D); m <= (int)(d9 + d11 / 2.0D); ++m) {
						double d13 = ((double)j + 0.5D - d7) / (d11 / 2.0D);
						double d14 = ((double)k + 0.5D - d8) / (d12 / 2.0D);
						double d15 = ((double)m + 0.5D - d9) / (d11 / 2.0D);
						if(d13 * d13 + d14 * d14 + d15 * d15 < 1.0D && (world.getBlockId(j, k - 1, m) == Block.sand.blockID || world.getBlockId(j, k - 1, m) == Block.dirt.blockID)
						&& (world.getBlockId(j, k + 1, m) == Block.waterStill.blockID || world.getBlockId(j, k + 1, m) == Block.waterMoving.blockID)) {
							
							setBlock(j, k, m, 2, random, world); //
							setBlock(j + 1, k, m, 2, random, world); //2
							setBlock(j, k, m + 1, 2, random, world); //3
							setBlock(j + 1, k, m + 1, 2, random, world); //4
							//setBlock(j, k + random.nextInt(2), m, 2, random, world); //5
							//setBlock(j + 1, k + random.nextInt(2), m, 2, random, world); //6
							//setBlock(j, k + random.nextInt(2), m + 1, 2, random, world);
							//setBlock(j + 1, k + random.nextInt(2), m + 1, 2, random, world);
							
							for(int x2 = 0; x2 < numberOfBlocks; x2++) {
								for(int z2 = 0; z2 < numberOfBlocks; z2++) {
									if(random.nextInt(2) == 0) {
										if(world.getBlockId(x2, k + 1, z2) == Block.waterStill.blockID) {
											setBlock(x2, k + 1, z2, 2, random, world);
											
											if(random.nextInt(4) == 0) {
												if(world.getBlockId(x2, k + 2, z2) == Block.waterStill.blockID) {
													setBlock(x2, k + 2, z2, 2, random, world);
													
													if(random.nextInt(8) == 0) {
														if(world.getBlockId(x2, k + 3, z2) == Block.waterStill.blockID) {
															setBlock(x2, k + 3, z2, 2, random, world);
															
															if(random.nextInt(16) == 0) {
																if(world.getBlockId(x2, k + 4, z2) == Block.waterStill.blockID) {
																	setBlock(x2, k + 4, z2, 2, random, world);
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
							
							//world.setBlockWithNotify(j, k, m, coralID); 1
							//world.setBlockWithNotify(j + 1, k, m, coralID);2
							//world.setBlockWithNotify(j, k, m + 1, coralID);3
							//world.setBlockWithNotify(j + 1, k, m + 1, coralID);4
							//world.setBlockWithNotify(j, k + random.nextInt(2), m, coralID);5
							//world.setBlockWithNotify(j + 1, k + random.nextInt(2), m, coralID);6
							//world.setBlockWithNotify(j, k + random.nextInt(2), m + 1, coralID);7
							//world.setBlockWithNotify(j + 1, k + random.nextInt(2), m + 1, coralID);8
						}
					}
				}
			}
		}

		return true;
	}

	public void setBlock(int x, int y, int z, int flag, Random random, World world) {
		switch(random.nextInt(4)) {
			case 0:
				world.setBlock(x, y, z, SpaceAgeCore.coralID, 0, flag);//BRAIN
			case 1:
				world.setBlock(x, y, z, SpaceAgeCore.coralID, 1, flag);//STAGHORN
			case 2:
				world.setBlock(x, y, z, SpaceAgeCore.coralID, 2, flag);//PILLAR
			case 3:
				world.setBlock(x, y, z, SpaceAgeCore.coralID, 3, flag);//MAZE
		}
	}
}
