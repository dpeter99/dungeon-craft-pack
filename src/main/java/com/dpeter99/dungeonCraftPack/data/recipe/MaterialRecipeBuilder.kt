package com.dpeter99.dungeonCraftPack.data.recipe

import blusunrize.immersiveengineering.ImmersiveEngineering
import blusunrize.immersiveengineering.api.crafting.builders.CrusherRecipeBuilder
import com.dpeter99.dungeonCraftPack.data.Material
import com.dpeter99.dungeonCraftPack.data.create.recipe.CrushingRecipeGen
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import java.util.function.Consumer

class MaterialRecipeBuilder(generatorIn: DataGenerator?, private val mat: Material) : RecipeProvider(generatorIn) {
    fun save(consumer: Consumer<FinishedRecipe?>?) {
        CrushingRecipeGen.stoneOre(mat.oreStone, mat.raw, 1f, 1).register(consumer)
        CrushingRecipeGen.deepslateOre(mat.oreDeep, mat.raw, 1f, 1).register(consumer)
        CrushingRecipeGen.rawOre(mat.raw, mat.crushed, 2).register(consumer)


        CrusherRecipeBuilder.builder(ItemStack(mat.raw.get().asItem(),2))
            .addInput(mat.oreStone.get())
            .addSecondary(Items.STONE, 1f)
            .setEnergy(200)
            .build(consumer, ResourceLocation.tryParse(ImmersiveEngineering.MODID + ":crusher/ore_" + mat.name))


        CrusherRecipeBuilder.builder(mat.crushed.get().asItem())
            .addInput(mat.raw.get())
            .setEnergy(200)
            .build(consumer, ResourceLocation.tryParse(ImmersiveEngineering.MODID + ":crusher/raw_ore_" + mat.name))
    }
}