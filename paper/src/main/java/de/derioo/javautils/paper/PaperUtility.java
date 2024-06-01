package de.derioo.javautils.paper;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class PaperUtility {

    public Material getRandomMaterial(Material[] materials) {
        List<Material> list = new ArrayList<>(List.of(materials));
        Collections.shuffle(list);
        return list.stream().findAny().orElse(null);
    }

    public Material getRandomMaterial() {
        return getRandomMaterial(Material.values());
    }

}

