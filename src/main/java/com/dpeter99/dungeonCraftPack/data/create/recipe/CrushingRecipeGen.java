package com.dpeter99.dungeonCraftPack.data.create.recipe;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

public class CrushingRecipeGen extends ProcessingRecipeGen {


	static public GeneratedRecipe stoneOre(Supplier<ItemLike> ore, Supplier<ItemLike> raw, float expectedAmount,
		int duration) {
		return ore(Blocks.COBBLESTONE, ore, raw, expectedAmount, duration);
	}

	static public GeneratedRecipe deepslateOre(Supplier<ItemLike> ore, Supplier<ItemLike> raw, float expectedAmount,
		int duration) {
		return ore(Blocks.COBBLED_DEEPSLATE, ore, raw, expectedAmount, duration);
	}

	static public GeneratedRecipe netherOre(Supplier<ItemLike> ore, Supplier<ItemLike> raw, float expectedAmount,
		int duration) {
		return ore(Blocks.NETHERRACK, ore, raw, expectedAmount, duration);
	}

	static public GeneratedRecipe mineralRecycling(AllPaletteStoneTypes type, Supplier<ItemLike> crushed,
		Supplier<ItemLike> nugget, float chance) {
		return mineralRecycling(type, b -> b.duration(250)
			.output(chance, crushed.get(), 1)
			.output(chance, nugget.get(), 1));
	}

	static public GeneratedRecipe mineralRecycling(AllPaletteStoneTypes type,
		UnaryOperator<ProcessingRecipeBuilder<ProcessingRecipe<?>>> transform) {
		create(AllRecipeTypes.CRUSHING,Lang.asId(type.name()) + "_recycling", b -> transform.apply(b.require(type.materialTag)));
		return create(AllRecipeTypes.CRUSHING, type.getBaseBlock()::get, transform);
	}

	static public GeneratedRecipe ore(ItemLike stoneType, Supplier<ItemLike> ore, Supplier<ItemLike> raw,
		float expectedAmount, int duration) {
		return create(AllRecipeTypes.CRUSHING, ore, b -> {
			ProcessingRecipeBuilder<ProcessingRecipe<?>> builder = b.duration(duration)
				.output(raw.get(), Mth.floor(expectedAmount));
			float extra = expectedAmount - Mth.floor(expectedAmount);
			if (extra > 0)
				builder.output(extra, raw.get(), 1);
			builder.output(.75f, AllItems.EXP_NUGGET.get(), 1);
			return builder.output(.125f, stoneType);
		});
	}

	static public GeneratedRecipe rawOre(Supplier<ItemLike> input, Supplier<ItemLike> result, int amount) {
		return create(AllRecipeTypes.CRUSHING, input, b -> b.duration(400)
			.output(result.get(), amount)
			.output(.75f, AllItems.EXP_NUGGET.get(), amount));
	}

	static public GeneratedRecipe moddedRawOre(CompatMetals metal, Supplier<ItemLike> result, int amount) {
		String name = metal.getName();
		return create(AllRecipeTypes.CRUSHING, "raw_" + name + (amount == 1 ? "_ore" : "_block"), b -> {
			String prefix = amount == 1 ? "raw_ores/" : "raw_blocks/";
			return b.duration(400)
				.withCondition(new NotCondition(new TagEmptyCondition("forge", prefix + name)))
				.require(AllTags.forgeItemTag(prefix + name))
				.output(result.get(), amount)
				.output(.75f, AllItems.EXP_NUGGET.get(), amount);
		});
	}


	/*
	public static AllRecipeTypes getRecipeType() {
		return AllRecipeTypes.CRUSHING;
	}
	*/
}
