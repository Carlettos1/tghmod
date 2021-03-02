package com.carlettos.roninmod;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carlettos.roninmod.bala.BalaEntity;
import com.carlettos.roninmod.bala.BalaItem;
import com.carlettos.roninmod.bala.BalaRenderer;
import com.carlettos.roninmod.r0n1n.R0n1nEntity;
import com.carlettos.roninmod.r0n1n.R0n1nRender;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("roninmod")
public class RoninMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    
	public static EntityType<R0n1nEntity> RONIN = EntityType.Builder.create(R0n1nEntity::new, EntityClassification.CREATURE).size(0.5f, 1.5f).build("roninmod:ronin");
    public static EntityType<BalaEntity> balaEntity = EntityType.Builder.<BalaEntity>create(BalaEntity::new, EntityClassification.MISC).size(0.25f, 0.25f).trackingRange(4).func_233608_b_(10).build("roninmod:bala");
    
    public static SpawnEggItem webo = new SpawnEggItem(RONIN, 0x085bb6, 0x110301, new Item.Properties().group(ItemGroup.FOOD));
    public static Item arma = new ArmaItem();
    public static BalaItem bala = new BalaItem();

    public RoninMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    	event.enqueueWork(() -> {
    		GlobalEntityTypeAttributes.put(RoninMod.RONIN, R0n1nEntity.attr().create());
    	});
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SuppressWarnings("resource")
	private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    @SubscribeEvent
    public void onBiomeLoadingEvent(BiomeLoadingEvent event) {
    	List<MobSpawnInfo.Spawners> spawners = event.getSpawns().getSpawner(EntityClassification.CREATURE);
    	spawners.add(new MobSpawnInfo.Spawners(RONIN, 20, 3, 4));
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
        

        @SubscribeEvent
        public static void items(RegistryEvent.Register<Item> ire) {
        	webo.setRegistryName(new ResourceLocation("roninmod", "webo"));
        	arma.setRegistryName(new ResourceLocation("roninmod", "arma"));
        	bala.setRegistryName(new ResourceLocation("roninmod", "bala"));

        	ire.getRegistry().register(webo);
        	ire.getRegistry().register(arma);
        	ire.getRegistry().register(bala);
        }
        
        @SubscribeEvent
        public static void entidades(RegistryEvent.Register<EntityType<?>> bre) {
        	RONIN.setRegistryName(new ResourceLocation("roninmod", "ronin"));
        	balaEntity.setRegistryName(new ResourceLocation("roninmod", "bala"));
        	bre.getRegistry().register(RONIN);
        	bre.getRegistry().register(balaEntity);
        }
        
        @SubscribeEvent
        public static void biomas(RegistryEvent.Register<Biome> bre) {
        }
        
        
        @OnlyIn(Dist.CLIENT)
    	@SubscribeEvent
    	public static void clientes(FMLClientSetupEvent event) {
    		RenderingRegistry.registerEntityRenderingHandler(RoninMod.RONIN, R0n1nRender::new);
    		RenderingRegistry.registerEntityRenderingHandler(RoninMod.balaEntity, BalaRenderer::new);
    	}
    }
}
