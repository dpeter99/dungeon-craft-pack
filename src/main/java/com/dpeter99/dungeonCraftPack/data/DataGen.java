package com.dpeter99.dungeonCraftPack.data;

import com.dpeter99.dungeonCraftPack.DungeonCraftPack;
import com.dpeter99.dungeonCraftPack.data.recipe.RecipeProviders;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import static com.dpeter99.dungeonCraftPack.DungeonCraftPack.LOGGER;

@Mod.EventBusSubscriber(modid = DungeonCraftPack.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    public static final Marker DATAGEN = MarkerManager.getMarker("DATAGEN");

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        LOGGER.info(DATAGEN, "Gathering data providers");
        DataGenerator generator = event.getGenerator();
        if(event.includeServer()){
            LOGGER.info(DATAGEN, "Adding data providers for server data");
            generator.addProvider(new RecipeProviders(generator));
            //generator.addProvider(new MobBloodProvider(generator));
            //generator.addProvider(new AdvancementsProvider(generator));
            //generator.addProvider(new GLMProvider(generator));
            //generator.addProvider(new LootTableProviders(generator));
            //generator.addProvider(new RecipeProviders(generator));
            //BlockTagsProvider blockTags = new BlockTagProviders(generator, event.getExistingFileHelper());
            //generator.addProvider(blockTags);
            //generator.addProvider(new ItemTagProviders(generator, blockTags, event.getExistingFileHelper()));
            //generator.addProvider(new FluidTagsProviders(generator, event.getExistingFileHelper()));
        }

        if(event.includeClient()){
            LOGGER.info(DATAGEN, "Adding data providers for client assets");
            //generator.addProvider(new BlockModelProviders(generator, event.getExistingFileHelper()));
            //generator.addProvider(new ItemModelProviders(generator, event.getExistingFileHelper()));
            //generator.addProvider(new EnUsLangProvider(generator));
            //generator.addProvider(new BlockStateProvider(generator, event.getExistingFileHelper()));
        }
    }

}
