package io.github.jwdeveloper.spigot.fluent.core.repository.api;


import io.github.jwdeveloper.spigot.fluent.core.common.java.ObjectUtility;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import lombok.Data;
import org.bukkit.Material;

import java.util.Optional;
import java.util.UUID;

@Data
public abstract class DataModel {
    private UUID uuid = UUID.randomUUID();
    private String name = "";
    private String description = "";
    private Material icon = Material.DIRT;

    public boolean isNull() {
        return uuid == null;
    }


    public <T extends DataModel> Optional<T> copy() {
        try {

            var result = (T) ObjectUtility.copyObjectDeep(this,getClass());
            return Optional.of(result);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Can not copy object for class " + this.getClass().getSimpleName(), e);
        }
        return Optional.empty();
    }
}
