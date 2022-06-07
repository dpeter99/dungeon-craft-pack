package com.dpeter99.dungeonCraftPack;

import com.dpeter99.dungeonCraftPack.item.DungeonRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.dpeter99.dungeonCraftPack.DungeonCraftPack.MODID;

@Mod(MODID)
public class DungeonCraftPack {

    public static final String MODID = "dungeon_craft_pack";

    public static final Logger LOGGER = LogManager.getLogger();

    public DungeonCraftPack() {

        MinecraftForge.EVENT_BUS.register(this);

        var a = new DungeonRegistry();
        //LOGGER.error(TestKt.INSTANCE.getTEST());
    }


    public static ResourceLocation loc(String name){
        return ResourceLocation.tryParse(MODID + ":" + name);
    }

    public static ResourceLocation loc(String mod, String name){
        return ResourceLocation.tryParse(mod + ":" + name);
    }

    public static ResourceLocation rl(@NotNull String s) {
        return loc(s);
    }
}
