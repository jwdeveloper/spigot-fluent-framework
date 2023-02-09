import lombok.Getter;

public class FluentPluginMock
{
 /*   private static FluentPluginMock INSTANCE;

    @Getter
    private MockPlugin pluginMock;

    @Getter
    private ServerMock serverMock;

    @Getter
    private WorldMock worldMock;

    public PlayerMock getPlayer()
    {
      return  serverMock.addPlayer();
    }

    @Getter
    private FluentInjectionImpl injection;

    private static boolean enabled;

    public FluentPluginMock(FluentApiExtension extension)
    {
        if(!enabled)
        {
            return;
        }
        if(MockBukkit.isMocked())
        {
            return;
        }
        serverMock = MockBukkit.mock();
        worldMock = serverMock.addSimpleWorld("world");
        serverMock.addWorld(worldMock);

        pluginMock = MockBukkit.createMockPlugin();

        var apiBuilder = FluentApiBuilder.create(pluginMock);
        apiBuilder.useExtension(extension);
        apiBuilder.build();
        injection = (FluentInjectionImpl) FluentApi.container();
    }

    public static FluentPluginMock getInstance()
    {
        enabled = true;
        if(INSTANCE == null)
        {
            INSTANCE = new FluentPluginMock(new FluentApiExtension() {
                @Override
                public void onConfiguration(FluentApiSpigotBuilder builder) {

                }

                @Override
                public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

                }

                @Override
                public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

                }
            });
        }
        return INSTANCE;
    }

    public static FluentPluginMock getInstance(FluentApiExtension extension)
    {
        enabled = true;
        if(INSTANCE == null)
        {
            INSTANCE = new FluentPluginMock(extension);
        }
        return INSTANCE;
    }*/
}
