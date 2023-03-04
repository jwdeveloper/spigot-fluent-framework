package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.bools.BoolNotifierOptions;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.bools.FluentBoolNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.enums.EnumNotifierOptions;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.enums.FluentEnumNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.ints.FluentBarIntNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.ints.FluentIntNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.ints.IntNotifierOptions;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.list.FluentListNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.list.ListNotifierOptions;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.list.checkbox.CheckBox;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.list.checkbox.FluentCheckboxListNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.string.StringNotifierOptions;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.styles.FluentButtonStyle;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.InventoryUI;
import core.implementation.button.observer_button.ButtonObserverUI;
import core.implementation.button.observer_button.ButtonObserverUIBuilder;
import core.implementation.button.observer_button.observers.ButtonObserverBuilder;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.implementation.chest_ui.ChestUI;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.Observer;
import io.github.jwdeveloper.spigot.fluent.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.implementation.PermissionsUtility;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FluentButtonUIFactory {
    private final FluentButtonStyle fluentButtonStyle;
    private final FluentTranslator lang;

    private final FluentButtonUIBuilder builder;

    public FluentButtonUIFactory(FluentTranslator translator,
                                 FluentButtonStyle style,
                                 FluentButtonUIBuilder builder) {
        fluentButtonStyle = style;
        this.lang = translator;
        this.builder = builder;
    }


    public FluentButtonUIBuilder observeBarInt(Supplier<Observer<Integer>> observer, Consumer<IntNotifierOptions> consumer) {
        var options = new IntNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick(lang.get("gui.base.increase"));
            buttonDescriptionInfoBuilder.setOnRightClick(lang.get("gui.base.decrease"));
        });
        builder.setObserver(observer, new FluentBarIntNotifier(fluentButtonStyle, lang, options));
        return builder;
    }

    public FluentButtonUIBuilder observeInt(Supplier<Observer<Integer>> observer, Consumer<IntNotifierOptions> consumer) {
        var options = new IntNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick("+ " + options.getYield());
            buttonDescriptionInfoBuilder.setOnRightClick("- " + options.getYield());
        });
        builder.setObserver(observer, new FluentIntNotifier(lang, options));
        return builder;
    }

    public <T extends Enum<T>> FluentButtonUIBuilder observeEnum(Supplier<Observer<T>> observer, Consumer<EnumNotifierOptions> consumer) {
        var _class = (Class<T>) observer.get().getValueType();
        var options = new EnumNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick(lang.get("gui.base.next"));
            buttonDescriptionInfoBuilder.setOnRightClick(lang.get("gui.base.previous"));
        });
        builder.setObserver(observer, new FluentEnumNotifier<T>(_class, options));
        return builder;
    }

    public <T extends Enum<T>> FluentButtonUIBuilder observeEnum(Supplier<Observer<T>> observer) {

        return observeEnum(observer, enumNotifierOptions -> {
        });
    }

    public <T> FluentButtonUIBuilder observeList(Supplier<Observer<T>> indexObserver, Supplier<List<T>> values, Consumer<ListNotifierOptions<T>> consumer) {
        var options = new ListNotifierOptions<T>();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick(lang.get("gui.base.next"));
            buttonDescriptionInfoBuilder.setOnRightClick(lang.get("gui.base.previous"));
        });
        builder.setObserver(indexObserver, new FluentListNotifier<>(values, options));
        return builder;
    }

    public <T> FluentButtonUIBuilder observeCheckBoxList(InventoryUI inventoryUI, Supplier<List<CheckBox>> values, Consumer<ListNotifierOptions<CheckBox>> consumer) {
        var options = new ListNotifierOptions<CheckBox>();
        var observerBag = new ObserverBag<CheckBox>(new CheckBox());
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
                    buttonDescriptionInfoBuilder.setOnLeftClick(lang.get("gui.base.next"));
                    buttonDescriptionInfoBuilder.setOnRightClick(lang.get("gui.base.previous"));
                    buttonDescriptionInfoBuilder.setOnShiftClick(lang.get("gui.base.enable-disable"));
                })
                .setOnShiftClick((player, button) ->
                {
                    var observer = observerBag.getObserver();
                    if (observer.get() == null) {
                        return;
                    }

                    var value = observer.get();
                    var permission = value.getPermission();
                    if (!PermissionsUtility.hasOnePermission(player, permission)) {
                        return;
                    }
                    value.getObserver().set(!value.getObserver().get());
                    inventoryUI.refreshButton(button);
                });
        builder.setObserver(observerBag.getObserver(), new FluentCheckboxListNotifier(values, options));
        return builder;
    }

    public FluentButtonUIBuilder observeBool(Supplier<Observer<Boolean>> observer) {
        return observeBool(observer, boolNotifierOptions -> {
        });
    }

    public FluentButtonUIBuilder observeBool(Supplier<Observer<Boolean>> observer, Consumer<BoolNotifierOptions> consumer) {
        var options = new BoolNotifierOptions();
        consumer.accept(options);
        builder.setDescription(buttonDescriptionInfoBuilder ->
        {
            buttonDescriptionInfoBuilder.addObserverPlaceholder(options.getId());
            buttonDescriptionInfoBuilder.setOnLeftClick("Change state");
        });
        builder.setObserver(observer, new FluentBoolNotifier(lang, options));
        return builder;
    }


    public FluentButtonUIBuilder back(InventoryUI inventory) {
        return back(inventory, null);
    }

    public FluentButtonUIBuilder back(InventoryUI inventory, InventoryUI parent) {
        builder.setOnLeftClick((player, button) ->
                {
                    if (parent == null) {
                        inventory.close();
                        return;
                    }
                    parent.open(player);
                })
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    var title = StringUtils.EMPTY;
                    if (parent == null)
                        title = lang.get("gui.base.exit.title");
                    else
                        title = lang.get("gui.base.back.title");
                    buttonDescriptionInfoBuilder.setTitle(title);
                })
                .setMaterial(Material.ARROW)
                .setLocation(inventory.getHeight() - 1, inventory.getWidth() - 1);
        return builder;
    }

    public FluentButtonUIBuilder textInput(Consumer<StringNotifierOptions> consumer, ChestUI chestUI) {
        var options = new StringNotifierOptions();
        consumer.accept(options);
        return builder
                .setOnLeftClick((player, button) ->
                {
                    chestUI.close();
                    var builder = new MessageBuilder();
                    options.getMessage().accept(builder);
                    builder.send(player);
                    FluentTextInput.openTextInput(player, StringUtils.EMPTY, value ->
                    {
                        options.getOnTextInput().accept(new TextInputEvent(player, value));
                    });
                });
    }

    public ButtonObserverUIBuilder intInput(Observer<Integer> observable, ChestUI chestUI) {
        return ButtonObserverUI.builder()
                .setTitle(observable.getFieldName())
                .addObserver(new ButtonObserverBuilder<Integer>()
                        .withObserver(observable)
                        .onClick(event ->
                        {
                            chestUI.close();
                            var message = new MessageBuilder()
                                    .color(ChatColor.GREEN)
                                    .inBrackets("Enter number value").toString();
                            FluentTextInput.openTextInput(event.getPlayer(), message, value ->
                            {
                                if (value.matches("^(0|-*[1-9]+[0-9]*)$"))
                                    event.getObserver().setValue(Integer.valueOf(value));

                                chestUI.open(event.getPlayer());
                            });
                        })
                        .onValueChange(event ->
                        {
                            event.getButton().setDescription(new MessageBuilder().field(lang.get("gui.base.value"), event.getValue()));
                        })
                );
    }
}
