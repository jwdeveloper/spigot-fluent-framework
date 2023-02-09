package io.github.jwdeveloper.spigot.fluent.extension.websocket;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.core.api.FluentWebsocketPacket;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.api.FluentWebsocket;
import io.github.jwdeveloper.spigot.fluent.extension.websocket.api.WebsocketOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

public class WebsocketExtension implements FluentApiExtension {

    private boolean runServer;
    private Consumer<WebsocketOptions> consumer;

    public WebsocketExtension(Consumer<WebsocketOptions> optionsConsumer) {
        consumer = optionsConsumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var logger = builder.logger();
        var config = builder.config();
        var runProperty = runProperty();
        runServer = config.getOrCreate(runProperty);
        if (!runServer) {
            logger.info("Websocket is disabled in order to enable change piano.websocket.run to true in config.yml");
            return;
        }
        builder.container().registerSigleton(FluentWebsocket.class, container ->
        {
            try {
                var options = new WebsocketOptions();
                consumer.accept(options);
                var configProperty = portProperty(options.getDefaultPort());
                var port = config.getOrCreate(configProperty);
                return new FluentWebsocketImpl(port, logger);
            } catch (Exception e) {
                logger.error("Websocket error, check if port is open and free to use", e);
                return null;
            }
        });
        builder.container().registerList(FluentWebsocketPacket.class, LifeTime.SINGLETON);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        if (!runServer) {
            return;
        }
        var config = fluentAPI.config();
        var webSocket = (FluentWebsocketImpl) fluentAPI.container().findInjection(FluentWebsocket.class);
        var ipProperty = customIpProperty();
        var serverIp = config.getOrCreate(ipProperty);
        if (StringUtils.isNullOrEmpty(serverIp)) {
            serverIp = getServerPublicIP();
            config.configFile().set("plugin.websocket.server-ip", serverIp);
            config.save();
        }

        webSocket.setServerIp(serverIp);
        var packets = fluentAPI.container().findAllByInterface(FluentWebsocketPacket.class);
        webSocket.registerPackets(packets);
        webSocket.start();
        fluentAPI.logger().info("Websocket runs on:", webSocket.getServerIp() + ":" + webSocket.getPort());
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        if (!runServer) {
            return;
        }
        var webSocket = fluentAPI.container().findInjection(FluentWebsocket.class);
        webSocket.stop();
    }

    private String getServerPublicIP() throws IOException {
        var url = new URL("http://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }


    private ConfigProperty<Integer> portProperty(int defaultPort) {
        var description = new TextBuilder()
                .text("Set port for websocket").newLine()
                .text("! Make sure that port is open").newLine()
                .text("! When you have server on hosting, generate new port on the hosting panel").newLine().toString();
        return new ConfigProperty<Integer>("plugin.websocket.port", defaultPort, description);
    }

    private ConfigProperty<String> customIpProperty() {
        var description = new TextBuilder()
                .text("Set own IP for websocket, by default plugin use IP of your server").newLine()
                .text("! When you are using proxy set here proxy IP").newLine()
                .text("! When you are running plugin locally on your PC, set 'localhost'").newLine()
                .text("! When default IP not works try use IP that you are using in minecraft server list").newLine().toString();

        return new ConfigProperty<String>("plugin.websocket.server-ip", StringUtils.EMPTY, description);
    }

    private ConfigProperty<Boolean> runProperty() {
        return new ConfigProperty<Boolean>("plugin.websocket.run", true, "When false websocket will not run ");
    }
}
