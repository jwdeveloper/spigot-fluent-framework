package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.observers;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import org.bukkit.entity.Player;

public class ButtonObserver<T> implements ButtonObservable<T> {
    private final Observer<T> observable;
    private final ButtonNotifier buttonNotifier;
    public ButtonUI buttonUI;

    public ButtonObserver(Observer<T> observable, ButtonNotifier buttonNotifier)
    {
        this.observable = observable;
        this.buttonNotifier =buttonNotifier;
        this.observable.onChange(value ->
        {
            if(!validateButton())
                return;
            buttonNotifier.onValueChanged(new ButtonObserverEvent(null,buttonUI,this,value));
        });
    }
    public static <T> ButtonObserverBuilder<T> builder()
    {
        return new ButtonObserverBuilder<T>();
    }

    @Override
    public ButtonUI getButtonUI() {
        return buttonUI;
    }

    public void setButtonUI(ButtonUI buttonUI)
    {
        this.buttonUI = buttonUI;
    }

    public void click(Player player)
    {
        if(!validateButton())
            return;
        buttonNotifier.onLeftClick(new ButtonObserverEvent(player, buttonUI,this, observable.get()));
    }

    public void rightClick(Player player)
    {
        if(!validateButton())
            return;
        buttonNotifier.onRightClick(new ButtonObserverEvent(player, buttonUI,this, observable.get()));
    }

    public void refresh()
    {
        if(!validateButton())
            return;
        buttonNotifier.onValueChanged(new ButtonObserverEvent(null, buttonUI,this, observable.get()));
    }

    public void setValue(T value)
    {
        observable.set(value);
    }

    public T getValue()
    {
        return observable.get();
    }

    private boolean validateButton()
    {
        return buttonUI != null && buttonUI.isActive();
    }
}
