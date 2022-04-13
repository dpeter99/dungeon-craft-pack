package com.dpeter99.dungeonCraftPack.data.recipe;

import blusunrize.immersiveengineering.common.register.IEItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.tags.ITag;

import java.util.function.Consumer;

public class RecipeProviders extends RecipeProvider {

    public RecipeProviders(DataGenerator generatorIn) {
        super(generatorIn);
    }


    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        super.buildCraftingRecipes(consumer);

        ShapedRecipeBuilder.shaped(IEItems.Ingredients.CIRCUIT_BOARD, 2)
                .pattern(" X ")
                .pattern("X X")
                .pattern(" X ")
                .define('X', Items.IRON_INGOT)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(consumer);
    }
}