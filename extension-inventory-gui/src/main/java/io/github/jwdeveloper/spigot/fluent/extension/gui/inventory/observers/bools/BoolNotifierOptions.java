package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.bools;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.NotifierOptions;
import lombok.Setter;
import org.bukkit.Material;

@Setter
public class BoolNotifierOptions extends NotifierOptions {
    private Material enableMaterial = Material.LIME_CONCRETE;
    private Material disableMaterial = Material.RED_CONCRETE;

    private String enabled;

    private String disabled;

    private String prefix;

    Material getDisableMaterial() {
        return disableMaterial;
    }

    Material getEnableMaterial() {
        return enableMaterial;
    }

    String getEnable() {
        return enabled;
    }

    String getDisable() {
        return disabled;
    }

    String getPrefix() {
        return prefix;
    }
}
