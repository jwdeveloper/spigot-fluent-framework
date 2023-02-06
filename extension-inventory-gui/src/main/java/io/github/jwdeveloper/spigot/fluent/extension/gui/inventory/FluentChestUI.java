package fluent_ui;
import fluent_ui.styles.FluentButtonStyle;
import fluent_ui.styles.renderer.ButtonStyleRenderer;
import fluent_ui.styles.renderer.CatchButtonStyleRenderer;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.SimpleMessage;
import io.github.jwdeveloper.spigot.fluent.core.translator.api.FluentTranslator;

@Injection
public class FluentChestUI
{
    private final FluentTranslator translator;
    private final FluentButtonStyle style;
    private final CatchButtonStyleRenderer renderer;

    @Inject
    public FluentChestUI(FluentTranslator translator)
    {
        this.translator = translator;
        style = new FluentButtonStyle(translator);
        renderer = new CatchButtonStyleRenderer(translator, style.getColorSet(), new SimpleMessage());
    }

    public FluentTranslator lang()
    {
        return translator;
    }


    public ButtonStyleRenderer renderer()
    {
        return renderer;
    }

    public FluentButtonUIBuilder buttonBuilder()
    {
        return new FluentButtonUIBuilder(translator,renderer);
    }

    public FluentButtonUIFactory buttonFactory()
    {
        return new FluentButtonUIFactory(translator, style, buttonBuilder());
    }
}
