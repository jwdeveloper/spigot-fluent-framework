package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.list.checkbox;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.ObserverBag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckBox
{
    private String name = StringUtils.EMPTY;
    private Observer<Boolean> observer = new ObserverBag<Boolean>(false).getObserver();
    private String permission = StringUtils.EMPTY;

    public CheckBox()
    {

    }
}
