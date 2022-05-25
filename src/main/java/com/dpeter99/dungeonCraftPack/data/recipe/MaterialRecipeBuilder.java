package com.dpeter99.dungeonCraftPack.data.recipe;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.crafting.builders.CrusherRecipeBuilder;
import com.dpeter99.dungeonCraftPack.DungeonCraftPack;
import com.dpeter99.dungeonCraftPack.data.Material;
import com.dpeter99.dungeonCraftPack.data.create.recipe.CrushingRecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class MaterialRecipeBuilder extends RecipeProvider {

    private Material mat;

    public MaterialRecipeBuilder(DataGenerator generatorIn, Material mat){
        super(generatorIn);


        this.mat = mat;
    }

    public void save(Consumer<FinishedRecipe> consumer){

        CrushingRecipeGen.stoneOre(mat.oreStone,mat.raw,1,1).register(consumer);
        CrushingRecipeGen.deepslateOre(mat.oreDeep,mat.raw,1,1).register(consumer);

        CrushingRecipeGen.rawOre(mat.raw, mat.crushed, 2).register(consumer);

        CrusherRecipeBuilder.builder(mat.raw.get().asItem())
                .addInput(mat.oreStone.get())
                .addResult(mat.raw.get())
                .addSecondary(Items.STONE,1)
                .setEnergy(200)
                .build(consumer, ResourceLocation.tryParse(ImmersiveEngineering.MODID+":crusher/ore_"+mat.name));

        CrusherRecipeBuilder.builder(mat.crushed.get().asItem())
                .addInput(mat.raw.get())
                .setEnergy(200)
                .build(consumer, ResourceLocation.tryParse(ImmersiveEngineering.MODID+":crusher/raw_ore_"+mat.name));
    }



}
