package com.dpeter99.dungeonCraftPack.data

import blusunrize.immersiveengineering.ImmersiveEngineering
import com.simibubi.create.Create
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParams
import net.minecraft.world.phys.Vec3
import net.minecraftforge.registries.ForgeRegistries
import slimeknights.tconstruct.TConstruct
import slimeknights.tconstruct.smeltery.data.Byproduct
import java.util.function.Supplier

class Material(var name: String) {
    lateinit var oreStone: Supplier<ItemLike?>
    lateinit var oreDeep: Supplier<ItemLike?>
    lateinit var raw: Supplier<ItemLike?>
    lateinit var crushed: Supplier<ItemLike?>
    lateinit var dust: Supplier<ItemLike?>

    lateinit var liquid: Supplier<Fluid?>
    lateinit var side: Supplier<Byproduct>

    var base_drop: Float = 0f;
    val fortune3_avg get() = this.base_drop * 2.2f
    val fortune3_max get() = this.base_drop * 4f

    val fortune2_avg get() = this.base_drop * 1.75f
    val fortune2_max get() = this.base_drop * 3f

    fun setOre(loc: ResourceLocation?, deep: ResourceLocation?): Material {
        oreStone = Supplier { ForgeRegistries.ITEMS.getValue(loc) }
        oreDeep = Supplier { ForgeRegistries.ITEMS.getValue(deep) }

        var a = oreStone.get()?.asItem() as BlockItem;


        return this
    }

    fun setRaw(sup: Supplier<ItemLike?>): Material {
        raw = sup
        return this
    }

    fun setRaw(loc: ResourceLocation?): Material {
        raw = Supplier { ForgeRegistries.ITEMS.getValue(loc) }
        return this
    }

    fun setCrushed(loc: ResourceLocation?): Material {
        crushed = Supplier { ForgeRegistries.ITEMS.getValue(loc) }
        return this
    }

    fun setDust(loc: ResourceLocation?): Material {
        dust = Supplier { ForgeRegistries.ITEMS.getValue(loc) }
        return this
    }

    fun setFluid(fluid: Fluid): Material {
        liquid = Supplier { fluid }
        return this
    }

    fun setFluid(loc: ResourceLocation?): Material {
        liquid = Supplier { ForgeRegistries.FLUIDS.getValue(loc) }
        return this
    }

    companion object {
        @JvmStatic
        fun vanilla(name: String, base_drop: Float, byprod: Byproduct): Material {
            val m = Material(name)
            m.base_drop = base_drop;
            m.setOre(
                ResourceLocation.tryParse("minecraft:" + name + "_ore"),
                ResourceLocation.tryParse("minecraft:deepslate_" + name + "_ore")
            )
            m.setRaw(ResourceLocation.tryParse("minecraft:raw_$name"))
            m.setCrushed(Create.asResource("crushed_" + name + "_ore"))
            m.setDust(ImmersiveEngineering.rl("dust_"+name))
            m.setFluid(TConstruct.getResource("molten_"+name))
            m.side = Supplier { byprod };
            return m
        }

        @JvmStatic
        fun IE(name: String, base_drop: Float, byproduct: Byproduct): Material {
            val m = Material(name)
            m.base_drop = base_drop;
            m.setOre(
                ImmersiveEngineering.rl("ore_"+name),
                ImmersiveEngineering.rl("deepslate_ore_"+name)
            )
            m.setRaw(ImmersiveEngineering.rl("raw_"+name))
            m.setCrushed(Create.asResource("crushed_" + name + "_ore"))
            m.setDust(ImmersiveEngineering.rl("dust_"+name))
            m.setFluid(TConstruct.getResource("molten_"+name))
            m.side = Supplier { byproduct };
            return m
        }
    }


}