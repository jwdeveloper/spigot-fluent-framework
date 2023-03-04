package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;
import core.implementation.button.ButtonUI;
import core.implementation.button.observer_button.observers.ButtonNotifier;
import core.implementation.button.observer_button.observers.ButtonObservable;
import core.implementation.button.observer_button.observers.ButtonObserverEvent;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

public class FluentButtonObserver<T> implements ButtonObservable<T> {
    private final Supplier<Observer<T>> provider;
    private final ButtonNotifier<T> buttonNotifier;
    private final ButtonUI buttonUI;
    private Observer<T> currentObserver;


    public FluentButtonObserver(Supplier<Observer<T>> observable,
                                ButtonNotifier<T> buttonNotifier,
                                ButtonUI buttonUI) {
        this.provider = observable;
        this.buttonNotifier = buttonNotifier;
        this.buttonUI = buttonUI;
    }

    @Override
    public ButtonUI getButtonUI() {
        return buttonUI;
    }


    public void click(Player player) {
        if (!validateButton())
            return;
        buttonNotifier.onLeftClick(new ButtonObserverEvent<>(player, buttonUI, this, currentObserver.get()));
    }

    public void rightClick(Player player) {
        if (!validateButton())
            return;
        buttonNotifier.onRightClick(new ButtonObserverEvent<>(player, buttonUI, this, currentObserver.get()));
    }

    public void refresh() {
        if (buttonUI == null || !buttonUI.isActive())
            return;

        var observer = provider.get();
        if (observer == null) {
            return;
        }
        if (currentObserver != null && observer != currentObserver) {
            currentObserver.unsubscribe(this::onChangeEvent);

            currentObserver = observer;
            currentObserver.subscribe(this::onChangeEvent);
        }
        if (currentObserver == null) {
            currentObserver = observer;
            currentObserver.subscribe(this::onChangeEvent);
        }
        buttonNotifier.onValueChanged(new ButtonObserverEvent<>(null, buttonUI, this, currentObserver.get()));
    }


    public void onChangeEvent(T value) {
        if (!validateButton())
            return;
        buttonNotifier.onValueChanged(new ButtonObserverEvent(null, buttonUI, this, value));
    }


    public void setValue(T value) {
        if (currentObserver == null) {
            return;
        }
        currentObserver.set(value);
    }

    public T getValue() {
        if (currentObserver == null) {
            return null;
        }
        return currentObserver.get();
    }


    private boolean validateButton() {
        if (currentObserver == null) {
            FluentLogger.LOGGER.warning("OBSERVER NOT INITIALIZED YET", this.getButtonUI().getTitle(), this.toString());
            return false;
        }

        return buttonUI != null && buttonUI.isActive();
    }
}
