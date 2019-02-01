package fluke.somelikeitdry.configs;

import fluke.somelikeitdry.SomeLikeItDry;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SomeLikeItDry.MODID, category = "")
@Mod.EventBusSubscriber(modid = SomeLikeItDry.MODID)
public class Configs {

	public static GeneralConfigs general = new GeneralConfigs();

	public static class GeneralConfigs 
	{
		@Config.Comment({"Percent of ocean 'tiles' that should be converted to land. Range 0-100", "Default: 60"})
		@Config.RequiresWorldRestart
		public int oceanConversionChance = 60;
		
		@Config.Comment({"Percent chance that lake will not generate. Range 0-100", "Default: 60"})
		@Config.RequiresWorldRestart
		public int skipLakeChance = 60;
		
		@Config.Comment({"Percent chance that water falls (single block water sources) will not generate in caves. Range 0-100", "Default: 60"})
		@Config.RequiresWorldRestart
		public int skipWaterFallChance = 60;
		
		@Config.Comment({"Disable river generation.", "Default: false"})
		@Config.RequiresWorldRestart
		public boolean removeRivers = false;
		
		@Config.Comment({"Biome Size, functions just like Vanilla custom world setting. Larger numbers = larger biomes. Range 1-8", "Default: 4"})
		@Config.RequiresWorldRestart
		public int biomeSize = 4;
		
		@Config.Comment({"River Size, functions just like Vanilla custom world setting. Smaller numbers = more rivers. Range 1-5", "Default: 4"})
		@Config.RequiresWorldRestart
		public int riverSize = 4;
		
	}


	@SubscribeEvent
	public static void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event) 
	{
		if (SomeLikeItDry.MODID.equals(event.getModID()))
			ConfigManager.sync(SomeLikeItDry.MODID, Config.Type.INSTANCE);
	}
}
