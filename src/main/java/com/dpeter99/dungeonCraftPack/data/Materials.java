package com.dpeter99.dungeonCraftPack.data;

import slimeknights.tconstruct.smeltery.data.Byproduct;

import java.util.ArrayList;
import java.util.List;

public class Materials {

    public static List<Material> materials = new ArrayList<>();


    public static void Init(){

        materials.add(Material.vanilla("iron",1, Byproduct.NICKEL));
        materials.add(Material.vanilla("copper",3.5f, Byproduct.GOLD));
        materials.add(Material.vanilla("gold",1,Byproduct.COPPER));

        materials.add(Material.IE("lead",1,Byproduct.SILVER));
        materials.add(Material.IE("silver",1,Byproduct.SILVER));
        materials.add(Material.IE("nickel",1,Byproduct.SILVER));

    }


}
