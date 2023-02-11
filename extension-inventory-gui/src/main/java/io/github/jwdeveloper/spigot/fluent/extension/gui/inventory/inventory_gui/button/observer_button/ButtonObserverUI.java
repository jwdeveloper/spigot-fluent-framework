package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button;


import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.FluentButtonObserver;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.ButtonUI;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.observers.ButtonNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.observers.ButtonObservable;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.observers.ButtonObserver;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import core.api.managers.buttons.ButtonManager;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

@Getter
@Setter
public class ButtonObserverUI extends ButtonUI {
    @Singular
    protected Set<ButtonObservable<?>> observers = new LinkedHashSet<>();

    public void addObserver(ButtonObserver<?> observer) {
        observer.setButtonUI(this);
        observers.add(observer);
    }

    public <T> void addObserver(Observer<T> observable, ButtonNotifier<T> buttonNotifier) {
        var observer = new ButtonObserver<>(observable, buttonNotifier);
        observer.setButtonUI(this);
        observers.add(observer);
    }

    public <T> void addObserver(Supplier<Observer<T>> observable, ButtonNotifier<T> buttonNotifier) {
        var observer = new FluentButtonObserver<>(observable, buttonNotifier, this);
        observers.add(observer);
    }

    @Override
    public ItemStack getItemStack() {
        for (var observable : observers) {
            observable.refresh();
        }
        return super.getItemStack();
    }

    public void onClick(Player player, ButtonManager buttonManager) {
        super.click(player);
        for (var observable : observers) {
            observable.click(player);
            buttonManager.refreshButton(observable.getButtonUI());
        }
        //EventsListenerInventoryUI.refreshAllAsync(inventoryUI.getClass(), inventoryUI);
    }

    public void onRightClick(Player player, ButtonManager buttonManager) {
        super.rightClick(player);
        for (var observable : observers) {
            observable.rightClick(player);
            buttonManager.refreshButton(observable.getButtonUI());
        }
      //  EventsListenerInventoryUI.refreshAllAsync(inventoryUI.getClass(), inventoryUI);
    }

    public static ButtonObserverUIBuilder builder() {
        return new ButtonObserverUIBuilder();
    }
}
