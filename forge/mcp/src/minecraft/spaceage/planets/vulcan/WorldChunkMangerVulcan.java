package spaceage.planets.vulcan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import spaceage.planets.general.BiomeList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkMangerVulcan extends WorldChunkManager {
private GenLayer myGenBiomes;
private GenLayer myBiomeIndexLayer;
private BiomeCache myBiomeCache;
private List<BiomeGenBase> myBiomesToSpawnIn;

protected WorldChunkMangerVulcan() {
	this.myBiomeCache = new BiomeCache(this);
	this.myBiomesToSpawnIn = new ArrayList<BiomeGenBase>();
	this.myBiomesToSpawnIn.add(BiomeList.vulcanMain);
	this.myBiomesToSpawnIn.add(BiomeList.vulcanVolcano);
}

public WorldChunkMangerVulcan(long seed, WorldType worldtype) {
	this();
	GenLayer[] agenlayer = GenLayerVulcan.makeTheWorld(seed);
	this.myGenBiomes = agenlayer[0];
	this.myBiomeIndexLayer = agenlayer[1];
}

public WorldChunkMangerVulcan(World world) {
	this(world.getSeed(), world.provider.terrainType);
}

/**
* Gets the list of valid biomes for the player to spawn in.
*/
public List<BiomeGenBase> getBiomesToSpawnIn() {
	return this.myBiomesToSpawnIn;
}

/**
* Returns the BiomeGenBase related to the x, z position on the world.
*/
public BiomeGenBase getBiomeGenAt(int x, int z) {
	BiomeGenBase biome = this.myBiomeCache.getBiomeGenAt(x, z);
	if (biome == null) {
		return BiomeList.vulcanMain;
	}

	return biome;
}

/*IntCache.resetIntCache();

if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
{
par1ArrayOfFloat = new float[par4 * par5];
}

int[] aint = this.myBiomeIndexLayer.getInts(par2, par3, par4, par5);

for (int i1 = 0; i1 < par4 * par5; ++i1)
{
float f = (float) BiomeGenBase.biomeList[aint[i1]].getIntRainfall() / 65536.0F;

if (f > 1.0F) {
f = 1.0F;
}

par1ArrayOfFloat[i1] = f;
}

return par1ArrayOfFloat;
}*/

/**
* Returns an array of biomes for the location input.
*/
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
		IntCache.resetIntCache();

		if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}

		int[] aint = this.myGenBiomes.getInts(par2, par3, par4, par5);

		for (int i = 0; i < par4 * par5; ++i) {
			if (aint[i] >= 0) {
				par1ArrayOfBiomeGenBase[i] = BiomeGenBase.biomeList[aint[i]];
			} else {
			//Change this to a biome
				par1ArrayOfBiomeGenBase[i] = BiomeList.vulcanMain;
			}
		}
		return par1ArrayOfBiomeGenBase;
	}

/**
* Returns biomes to use for the blocks and loads the other data like
* temperature and humidity onto the WorldChunkManager Args: oldBiomeList,
* x, z, width, depth
*/
public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5) {
	return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
}

/**
* Return a list of biomes for the specified blocks. Args: listToReuse, x,
* y, width, length, cacheFlag (if false, don't check biomeCache to avoid
* infinite loop in BiomeCacheBlock)
*/
public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int x, int y, int width, int length, boolean cacheFlag) {
IntCache.resetIntCache();

if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < width * length) {
par1ArrayOfBiomeGenBase = new BiomeGenBase[width * length];
}

if (cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (y & 15) == 0) {
BiomeGenBase[] abiomegenbase1 = this.myBiomeCache.getCachedBiomes(x, y);
System.arraycopy(abiomegenbase1, 0, par1ArrayOfBiomeGenBase, 0, width * length);
return par1ArrayOfBiomeGenBase;
} else {
int[] aint = this.myBiomeIndexLayer.getInts(x, y, width, length);

for (int i = 0; i < width * length; ++i) {
if (aint[i] >= 0) {
par1ArrayOfBiomeGenBase[i] = BiomeGenBase.biomeList[aint[i]];
} else {
//Change this to a biome
par1ArrayOfBiomeGenBase[i] = BiomeList.vulcanMain;
}
}

return par1ArrayOfBiomeGenBase;
}
}

/**
* checks given Chunk's Biomes against List of allowed ones
*/
public boolean areBiomesViable(int par1, int par2, int par3, List par4List) {
IntCache.resetIntCache();
int l = par1 - par3 >> 2;
int i1 = par2 - par3 >> 2;
int j1 = par1 + par3 >> 2;
int k1 = par2 + par3 >> 2;
int l1 = j1 - l + 1;
int i2 = k1 - i1 + 1;
int[] aint = this.myGenBiomes.getInts(l, i1, l1, i2);

for (int j2 = 0; j2 < l1 * i2; ++j2) {
BiomeGenBase biomegenbase = BiomeGenBase.biomeList[aint[j2]];

if (!par4List.contains(biomegenbase)) {
return false;
}
}

return true;
}

/**
* Finds a valid position within a range, that is in one of the listed
* biomes. Searches {par1,par2} +-par3 blocks. Strongly favors positive y
* positions.
*/
public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random) {
IntCache.resetIntCache();
int l = par1 - par3 >> 2;
int i1 = par2 - par3 >> 2;
int j1 = par1 + par3 >> 2;
int k1 = par2 + par3 >> 2;
int l1 = j1 - l + 1;
int i2 = k1 - i1 + 1;
int[] aint = this.myGenBiomes.getInts(l, i1, l1, i2);
ChunkPosition chunkposition = null;
int j2 = 0;

for (int k2 = 0; k2 < l1 * i2; ++k2) {
int l2 = l + k2 % l1 << 2;
int i3 = i1 + k2 / l1 << 2;
BiomeGenBase biomegenbase = BiomeGenBase.biomeList[aint[k2]];

if (par4List.contains(biomegenbase) && (chunkposition == null || par5Random.nextInt(j2 + 1) == 0)) {
chunkposition = new ChunkPosition(l2, 0, i3);
++j2;
}
}

return chunkposition;
}

/**
* Calls the WorldChunkManager's biomeCache.cleanupCache()
*/
public void cleanupCache()
{
this.myBiomeCache.cleanupCache();
}
}