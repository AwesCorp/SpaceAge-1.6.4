package spaceage.planets.aliens;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import spaceage.planets.aliens.entity.EntityBinary;
import spaceage.planets.aliens.entity.EntityBinaryFemale;
import spaceage.planets.aliens.entity.EntityFish;

public class Aliens {

	public void registerEntities() {
		int binaryColoursBack = 0xeaeae9;//TEMPORARY TODO
		int binaryColoursFore = 0xc99a03;//TEMPORARY TODO
		int fishColoursBack = 0xeaeae9;//TEMPORARY TODO
		int fishColoursFore = 0xc99a03;//TEMPORARY TODO
		
		registerEntity(EntityBinary.class, "Binary", binaryColoursBack, binaryColoursFore); 
		LanguageRegistry.instance().addStringLocalization("entity.Binary.name", "Binary Male");
		
		registerEntity(EntityBinaryFemale.class, "BinaryFemale", binaryColoursBack, binaryColoursFore);
		LanguageRegistry.instance().addStringLocalization("entity.BinaryFemale.name", "Binary Female");
		
		registerEntity(EntityFish.class, "Goldfish", fishColoursBack, fishColoursFore);
		LanguageRegistry.instance().addStringLocalization("entity.Goldfish.name", "Goldfish");
	}
	
	public void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
		int id = EntityRegistry.findGlobalUniqueEntityId();

		EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
		EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
	}

	public void addSpawn(Class<? extends EntityLiving> entityClass, int spawnProb, int min, int max, BiomeGenBase[] biomes) {
		
		if (spawnProb > 0) {
			EntityRegistry.addSpawn(entityClass, spawnProb, min, max, EnumCreatureType.creature, biomes);
		}
	}
}
