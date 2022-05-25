package com.dpeter99.dungeonCraftPack.data;

import java.util.ArrayList;
import java.util.List;

public class Materials {

    public static List<Material> materials = new ArrayList<>();


    public static void Init(){

        materials.add(Material.vanilla("iron"));
        materials.add(Material.vanilla("copper"));
        materials.add(Material.vanilla("gold"));

    }


}
