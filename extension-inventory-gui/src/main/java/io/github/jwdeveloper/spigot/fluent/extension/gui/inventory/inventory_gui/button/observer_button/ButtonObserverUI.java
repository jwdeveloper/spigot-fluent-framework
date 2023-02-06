package inventory_gui.button.observer_button;


import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.LinkedHashSet;

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

    public void onClick(Player player, InventoryUI inventoryUI) {
        super.click(player);
        for (var observable : observers) {
            observable.click(player);
            inventoryUI.refreshButton(observable.getButtonUI());
        }
        EventsListenerInventoryUI.refreshAllAsync(inventoryUI.getClass(), inventoryUI);
    }

    public void onRightClick(Player player, InventoryUI inventoryUI) {
        super.rightClick(player);
        for (var observable : observers) {
            observable.rightClick(player);
            inventoryUI.refreshButton(observable.getButtonUI());
        }
        EventsListenerInventoryUI.refreshAllAsync(inventoryUI.getClass(), inventoryUI);
    }

    public static ButtonObserverUIBuilder builder() {
        return new ButtonObserverUIBuilder();
    }
}
