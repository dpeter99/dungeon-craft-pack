package com.dpeter99.dungeonCraftPack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("dungeon_craft_pack")
public class DungeonCraftPack {

    public static final String MODID = "dungeon-craft-pack";

    public static final Logger LOGGER = LogManager.getLogger();

    public DungeonCraftPack() {

        MinecraftForge.EVENT_BUS.register(this);

        //LOGGER.error(TestKt.INSTANCE.getTEST());
    }


}
