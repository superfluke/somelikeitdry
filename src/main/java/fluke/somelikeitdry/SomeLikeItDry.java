package fluke.somelikeitdry;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import org.apache.logging.log4j.Logger;

import fluke.somelikeitdry.configs.Configs;
import fluke.somelikeitdry.proxy.CommonProxy;
import fluke.somelikeitdry.world.DryGenLayers;


@Mod(modid = SomeLikeItDry.MODID, name = SomeLikeItDry.NAME, version = SomeLikeItDry.VERSION)
public class SomeLikeItDry 
{

	public static final String MODID = "somelikeitdry";
	public static final String NAME = "Some Like It Dry";
	public static final String VERSION = "1.1.0";


	@Instance(MODID)
	public static SomeLikeItDry instance;

	@SidedProxy(clientSide = "fluke.somelikeitdry.proxy.ClientProxy", serverSide = "fluke.somelikeitdry.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		logger = event.getModLog();
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		if (Loader.isModLoaded("thebetweenlands"))
			BetweenlandsCompat.setupBetweenBiomeList();
	}
	
	@SubscribeEvent 
	public void onLakeEvent(PopulateChunkEvent.Populate event)
	{
		if(event.getType() == Populate.EventType.LAKE && event.getRand().nextInt(100) < Configs.general.skipLakeChance)
			event.setResult(Result.DENY);
	}

	@SubscribeEvent 
	public void onInitGenLayers(WorldTypeEvent.InitBiomeGens event)
	{	
		if (Loader.isModLoaded("thebetweenlands"))
		{
			int biomeSample = event.getOriginalBiomeGens()[1].getInts(0, 0, 1, 1)[0];
			if(BetweenlandsCompat.isBetweenBiome(biomeSample))
				return;
		}
		String genOptions = "{\"biomeSize\":" + Configs.general.biomeSize + ",\"riverSize\":" + Configs.general.riverSize + "}";
		GenLayer[] newGenLayers;
		newGenLayers = DryGenLayers.initializeAllBiomeGenerators(event.getSeed(), event.getWorldType(), ChunkGeneratorSettings.Factory.jsonToFactory(genOptions).build());
		event.setNewBiomeGens(newGenLayers);
	}
}