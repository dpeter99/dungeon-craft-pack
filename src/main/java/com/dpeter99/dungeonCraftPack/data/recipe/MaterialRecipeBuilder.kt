package com.dpeter99.dungeonCraftPack.data.recipe

import blusunrize.immersiveengineering.ImmersiveEngineering
import blusunrize.immersiveengineering.api.crafting.builders.CrusherRecipeBuilder
import com.dpeter99.dungeonCraftPack.DungeonCraftPack
import com.dpeter99.dungeonCraftPack.data.Material
import com.dpeter99.dungeonCraftPack.data.create.recipe.CreateRecipeProvider
import com.dpeter99.dungeonCraftPack.data.create.recipe.CrushingRecipeGen
import com.dpeter99.dungeonCraftPack.data.create.recipe.ProcessingRecipeGen
import com.simibubi.create.AllItems
import com.simibubi.create.AllRecipeTypes
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder
import net.minecraft.advancements.critereon.EntityPredicate
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.minecraftforge.common.Tags
import net.minecraftforge.common.crafting.ConditionalRecipe
import net.minecraftforge.common.crafting.conditions.FalseCondition
import slimeknights.tconstruct.fluids.TinkerFluids
import slimeknights.tconstruct.library.recipe.FluidValues
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer.OreRateType
import slimeknights.tconstruct.smeltery.data.Byproduct
import slimeknights.tconstruct.smeltery.data.SmelteryRecipeProvider
import java.util.function.Consumer
import java.util.function.Supplier
import kotlin.math.roundToInt

class MaterialRecipeBuilder(generatorIn: DataGenerator?, private val mat: Material) : RecipeProvider(generatorIn) {
    fun save(consumer: Consumer<FinishedRecipe?>) {
        //#################
        //Ore -> Raw
        //#################
        //Create crushing
        oreToRaw(consumer);

        //Remove immersive hammer recipe
        RemoveRecipe(consumer, ImmersiveEngineering.rl("crafting/hammercrushing_" + mat.name))

        mat.liquid.get()?.let {
            SmelteryRecipeProvider(generator).oreMelting(
                consumer,
                it,
                FluidValues.INGOT * 6,
                "ores/" + mat.name,
                Tags.Items.ORE_RATES_DENSE,
                4.5f,
                "smeltery/melting/metal/"+mat.name+"/ore_dense",
                false,
                OreRateType.METAL,
                6f,
                mat.side.get()
            )

            SmelteryRecipeProvider(generator).oreMelting(
                consumer,
                it,
                FluidValues.INGOT * Math.round(mat.fortune2_avg),
                "ores/" + mat.name,
                Tags.Items.ORE_RATES_SINGULAR,
                2.5f,
                "smeltery/melting/metal/"+mat.name+"/ore_singular",
                false,
                OreRateType.METAL,
                2.0f,
                mat.side.get()
            )
        }



        //#################
        //Raw -> Crushed
        //#################
        //Create
        CrushingRecipeGen.rawOre(mat.raw, mat.crushed, 2).register(consumer)

        //IE
        CrusherRecipeBuilder.builder(ItemStack(mat.crushed.get()?.asItem()!!,2))
            .addInput(mat.raw.get())
            .addSecondary(mat.crushed.get(),0.1f)
            .setEnergy(200)
            .build(consumer, ImmersiveEngineering.rl("crusher/raw_ore_" + mat.name))

        mat.liquid.get()?.let {
            SmelteryRecipeProvider(generator).oreMelting(
                consumer,
                it,
                FluidValues.INGOT * 2,
                "raw_materials/" + mat.name,
                null,
                4.5f,
                "smeltery/melting/metal/"+mat.name+"/raw",
                false,
                OreRateType.METAL,
                6f,
                mat.side.get()
            )
        }

        //#################
        //Crushed -> Dust
        //#################
        CrusherRecipeBuilder.builder(ItemStack(mat.dust.get()?.asItem()!!,2))
            .addInput(mat.crushed.get())
            .setEnergy(200)
            .build(consumer, DungeonCraftPack.rl("crusher/dust_" + mat.name))

        RemoveRecipe(consumer,ImmersiveEngineering.rl("crafting/plate_"+mat.name+"_hammering"))


/*
        mat.liquid.get()?.let {
            SmelteryRecipeProvider(generator).oreMelting(
                consumer,
                it,
                FluidValues.INGOT * 2,
                "raw_materials/" + mat.name,
                null,
                4.5f,
                "smeltery/melting/metal/"+mat.name+"/crushed",
                false,
                OreRateType.METAL,
                6f,
                mat.side.get()
            )
        }
 */


    }

    fun oreToRaw(consumer: Consumer<FinishedRecipe?>?){
        createOreToRaw(mat.oreStone,Items.COBBLESTONE).register(consumer);
        createOreToRaw(mat.oreDeep,Items.COBBLED_DEEPSLATE).register(consumer);

        IEOreToRaw(mat.oreStone)
            .build(consumer,ImmersiveEngineering.rl("crusher/ore_" + mat.name));
        IEOreToRaw(mat.oreDeep)
            .build(consumer,ImmersiveEngineering.rl("crusher/deep_ore_" + mat.name));
    }

    fun createOreToRaw(
        ore: Supplier<ItemLike?>?,
        stoneType: ItemLike?
    ): CreateRecipeProvider.GeneratedRecipe {
        return ProcessingRecipeGen.create(AllRecipeTypes.CRUSHING, ore)
        { b: ProcessingRecipeBuilder<ProcessingRecipe<*>?> ->

            val builder = b.duration(1)
                .output(mat.raw.get(), 2)
                .output(0.5f, mat.raw.get(), 2)
            builder.output(.75f, AllItems.EXP_NUGGET.get(), 1)
            builder.output(.125f, stoneType)
        }
    }

    fun IEOreToRaw(
        ore: Supplier<ItemLike?>
    ): CrusherRecipeBuilder {
        return CrusherRecipeBuilder
            .builder(ItemStack(mat.raw.get()!!.asItem(), mat.fortune3_avg.roundToInt()))
            .addInput(ore.get())
            .addSecondary(ItemStack(mat.raw.get()!!.asItem(),mat.fortune3_avg.roundToInt()/2), 0.75f)
            .addSecondary(ItemStack(mat.raw.get()!!.asItem(),4), 0.25f)
            .setEnergy(200)

    }



    private fun RemoveRecipe(consumer: Consumer<FinishedRecipe?>, location: ResourceLocation) {
        ConditionalRecipe.builder()
            .addCondition(FalseCondition.INSTANCE)
            .addRecipe {

                ShapelessRecipeBuilder
                    .shapeless(Items.ACACIA_BOAT)
                    .requires(Items.ACACIA_BOAT)
                    .unlockedBy(
                        "fa", RecipeUnlockedTrigger.TriggerInstance(
                            EntityPredicate.Composite.ANY,
                            ResourceLocation("", "")
                        )
                    )
                    .save(it, location);
            }
            .build(consumer, location)
    }
}