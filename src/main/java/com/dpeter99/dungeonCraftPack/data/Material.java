package com.dpeter99.dungeonCraftPack.data;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class Material {

    public String name;

    public Supplier<ItemLike> oreStone;
    public Supplier<ItemLike> oreDeep;
    public Supplier<ItemLike> raw;
    public Supplier<ItemLike> crushed;

    public Material(String name){
        this.name = name;
    }

    public Material setOre(ResourceLocation loc, ResourceLocation deep){
        oreStone = ()-> ForgeRegistries.ITEMS.getValue(loc);
        oreDeep = ()-> ForgeRegistries.ITEMS.getValue(deep);

        return this;
    }

    public Material setRaw(Supplier<ItemLike> sup){
        raw = sup;
        return this;
    }

    public Material setRaw(ResourceLocation loc){
        raw = ()->ForgeRegistries.ITEMS.getValue(loc);
        return this;
    }

    public Material setCrushed(ResourceLocation loc){
        crushed = ()->ForgeRegistries.ITEMS.getValue(loc);
        return this;
    }

    public static Material vanilla(String name){
        Material m = new Material(name);
        m.setOre(
                ResourceLocation.tryParse("minecraft:"+name+"_ore"),
                ResourceLocation.tryParse("minecraft:deepslate_"+name+"_ore")
        );
        m.setRaw(ResourceLocation.tryParse("minecraft:raw_"+name));

        m.setCrushed(ResourceLocation.tryParse("create:crushed_"+name+"_ore"));

        return m;
    }

}
