package com.dpeter99.dungeonCraftPack.item

import com.dpeter99.dungeonCraftPack.DungeonCraftPack
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraftforge.registries.RegistryObject
import java.util.*
import java.util.function.Supplier


private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DungeonCraftPack.MODID)

val ZERO: RegistryObject<Item> = ITEMS.register("zero") { Item(Item.Properties().rarity(Rarity.RARE).tab(DungeonRegistry.TAB)) }
val ONE: RegistryObject<Item> = ITEMS.register("one") { Item(Item.Properties().rarity(Rarity.RARE).tab(DungeonRegistry.TAB)) }
val TWO: RegistryObject<Item> = ITEMS.register("two") { Item(Item.Properties().rarity(Rarity.RARE).tab(DungeonRegistry.TAB)) }
val THREE: RegistryObject<Item> = ITEMS.register("three") { Item(Item.Properties().rarity(Rarity.RARE).tab(DungeonRegistry.TAB)) }

internal class DungeonRegistry {
    init {
        ITEMS.register(FMLJavaModLoadingContext.get().modEventBus)
    }

    companion object{
        fun <T : IForgeRegistryEntry<*>> getName(type: T): ResourceLocation? {
            return Objects.requireNonNull(type.getRegistryName())
        }

        fun <T : IForgeRegistryEntry<*>> getName(supplier: Supplier<T>): ResourceLocation? {
            return getName(supplier.get())
        }

        fun getName(a: RegistryObject<*>): ResourceLocation {
            return a.id;
        }

        val TAB: CreativeModeTab = object : CreativeModeTab("arcane_rituals") {
            override fun makeIcon(): ItemStack {
                return ItemStack(ZERO.get())
            }
        }
    }




}