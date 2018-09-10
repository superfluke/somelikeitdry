package fluke.somelikeitdry;

import java.util.ArrayList;

import net.minecraft.world.biome.Biome;
import thebetweenlands.common.registries.BiomeRegistry;
import thebetweenlands.common.world.biome.BiomeBetweenlands;

public class BetweenlandsCompat 
{
	public static ArrayList<Integer> betweenBiomes = new ArrayList<Integer>();
	
	public static void setupBetweenBiomeList()
	{
		for(BiomeBetweenlands biome : BiomeRegistry.REGISTERED_BIOMES) 
		{
			betweenBiomes.add(Biome.getIdForBiome(biome));
		}
	}
	
	public static boolean isBetweenBiome(int testBiome)
	{
		for(int blandsIDs: betweenBiomes)
		{
			if(blandsIDs == testBiome)
				return true;
		}
		return false;
	}
}
