package io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.bools;

import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.observers.FluentButtonNotifier;
import io.github.jwdeveloper.spigot.fluent.extension.gui.inventory.inventory_gui.button.observer_button.observers.ButtonObserverEvent;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;
public class FluentBoolNotifier extends FluentButtonNotifier<Boolean> {


    private record SelectBoolNotifierMessages(String enableMessage, String disableMessage) {
    }

    private final BoolNotifierOptions options;

    private final FluentTranslator translator;

    private SelectBoolNotifierMessages messages;

    public FluentBoolNotifier(FluentTranslator translator, BoolNotifierOptions options) {
        super(options);
        this.options = options;
        this.translator = translator;
    }

    @Override
    public void onLeftClick(ButtonObserverEvent<Boolean> event) {
        event.getObserver().setValue(!event.getValue());
    }

    @Override
    protected void onInitialize(ButtonObserverEvent<Boolean> event) {
        messages = createMessages(translator);
    }

    @Override
    protected void onUpdate(ButtonObserverEvent<Boolean> event) {
        final var button = event.getButton();
        if (event.getValue()) {
            button.setHighlighted(true);
            button.setMaterial(options.getEnableMaterial());
            button.updateDescription(getDescriptionIndex(), messages.enableMessage());
            return;
        }

        button.setHighlighted(false);
        button.setMaterial(options.getDisableMaterial());
        button.updateDescription(getDescriptionIndex(), messages.disableMessage());
    }


    private SelectBoolNotifierMessages createMessages(FluentTranslator translator) {
        if (StringUtils.isNullOrEmpty(options.getEnable())) {
            options.setEnabled(translator.get("gui.base.active"));
        }
        if (StringUtils.isNullOrEmpty(options.getDisable())) {
            options.setDisabled(translator.get("gui.base.inactive"));
        }
        if (StringUtils.isNullOrEmpty(options.getPrefix())) {
            options.setPrefix(translator.get("gui.base.state"));
        }

        return new SelectBoolNotifierMessages(new MessageBuilder().field(options.getPrefix(), options.getEnable()).toString(),
                new MessageBuilder().field(options.getPrefix(), options.getDisable()).toString());
    }

}
