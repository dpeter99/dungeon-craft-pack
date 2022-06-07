package com.dpeter99.dungeonCraftPack.data.item

import com.dpeter99.dungeonCraftPack.DungeonCraftPack
import com.dpeter99.dungeonCraftPack.item.*
import com.dpeter99.dungeonCraftPack.item.DungeonRegistry
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.RegistryObject


class ItemModelProviders(generator: DataGenerator?, existingFileHelper: ExistingFileHelper?) : ItemModelProvider(generator,DungeonCraftPack.MODID,existingFileHelper) {

    override fun registerModels() {

        generatedItem(ZERO);
        generatedItem(ONE);
        generatedItem(TWO);
        generatedItem(THREE);
    }

    fun generatedItem(a: RegistryObject<*>) {
        val name = name(a)
        singleTexture(
            "item/$name",  // destination
            mcLoc("item/generated"),  // "parent": ###
            "layer0",  // ###: TEXTURE
            modLoc("item/$name")
        ) // LAYER: ###
    }

    fun generatedItem(a: RegistryObject<*>, texture: String) {
        val name = name(a)
        singleTexture(
            "item/$name",  // destination
            mcLoc("item/generated"),  // "parent": ###
            "layer0",  // ###: TEXTURE
            modLoc("item/$texture")
        ) // LAYER: ###
    }

    fun name(a: RegistryObject<*>): String {
        return DungeonRegistry.getName(a).getPath()
    }

}