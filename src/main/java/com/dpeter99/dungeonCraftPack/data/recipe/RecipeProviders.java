package com.dpeter99.dungeonCraftPack.data.recipe;

import com.dpeter99.dungeonCraftPack.data.Materials;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.function.Consumer;

public class RecipeProviders extends RecipeProvider {

    public RecipeProviders(DataGenerator generatorIn) {
        super(generatorIn);

    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        Materials.Init();

        for (var mat : Materials.materials) {
            MaterialRecipeBuilder builder = new MaterialRecipeBuilder(generator,mat);
            builder.save(consumer);
        }
    }
}