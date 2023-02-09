package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers;

import lombok.Getter;

import java.util.UUID;

public class NotifierOptions
{
    @Getter
    private String id = UUID.randomUUID().toString();
}
