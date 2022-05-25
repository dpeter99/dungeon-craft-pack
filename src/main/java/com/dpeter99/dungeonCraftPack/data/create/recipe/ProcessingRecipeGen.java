package com.dpeter99.dungeonCraftPack.data.create.recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidAttributes;

public abstract class ProcessingRecipeGen extends CreateRecipeProvider {

	protected static final List<ProcessingRecipeGen> GENERATORS = new ArrayList<>();
	protected static final int BUCKET = FluidAttributes.BUCKET_VOLUME;
	protected static final int BOTTLE = 250;

	/**
	 * Create a processing recipe with a single itemstack ingredient, using its id
	 * as the name of the recipe
	 */
	static public <T extends ProcessingRecipe<?>> GeneratedRecipe create(IRecipeTypeInfo typeInfo, String namespace,
		Supplier<ItemLike> singleIngredient, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		ProcessingRecipeSerializer<T> serializer = getSerializer(typeInfo);
		GeneratedRecipe generatedRecipe = c -> {
			ItemLike iItemProvider = singleIngredient.get();
			transform
				.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(),
					new ResourceLocation(namespace, iItemProvider.asItem()
						.getRegistryName()
						.getPath())).withItemIngredients(Ingredient.of(iItemProvider)))
				.build(c);
		};
		return generatedRecipe;
	}

	/**
	 * Create a processing recipe with a single itemstack ingredient, using its id
	 * as the name of the recipe
	 */
	static public <T extends ProcessingRecipe<?>> GeneratedRecipe create(IRecipeTypeInfo typeInfo, Supplier<ItemLike> singleIngredient,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(typeInfo, Create.ID, singleIngredient, transform);
	}

	static public <T extends ProcessingRecipe<?>> GeneratedRecipe createWithDeferredId(IRecipeTypeInfo typeInfo, Supplier<ResourceLocation> name,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		ProcessingRecipeSerializer<T> serializer = getSerializer(typeInfo);
		GeneratedRecipe generatedRecipe =
			c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
				.build(c);
		return generatedRecipe;
	}

	/**
	 * Create a new processing recipe, with recipe definitions provided by the
	 * function
	 */
	static public <T extends ProcessingRecipe<?>> GeneratedRecipe create(IRecipeTypeInfo typeInfo, ResourceLocation name,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return createWithDeferredId(typeInfo,() -> name, transform);
	}

	/**
	 * Create a new processing recipe, with recipe definitions provided by the
	 * function
	 */
	static public <T extends ProcessingRecipe<?>> GeneratedRecipe create(IRecipeTypeInfo typeInfo, String name,
		UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
		return create(typeInfo,Create.asResource(name), transform);
	}

	static public <T extends ProcessingRecipe<?>> ProcessingRecipeSerializer<T> getSerializer(IRecipeTypeInfo typeInfo) {
		return typeInfo.getSerializer();
	}

	protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
		return () -> {
			ResourceLocation registryName = item.get()
				.asItem()
				.getRegistryName();
			return Create.asResource(registryName.getPath() + suffix);
		};
	}

}
