package inventory_gui.implementation.list_ui.content_manger;

public interface FilterContentEvent<T>
{
     boolean execute(T input);
}
