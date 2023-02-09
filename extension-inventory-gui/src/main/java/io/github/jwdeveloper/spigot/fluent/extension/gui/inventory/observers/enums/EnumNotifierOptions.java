package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.enums;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.NotifierOptions;
import lombok.Setter;

import java.util.function.Function;

@Setter
public class EnumNotifierOptions extends NotifierOptions
{
    private Function<String,String> onNameMapping;
    public Function<String, String> getOnNameMapping() {
        return onNameMapping;
    }
}
