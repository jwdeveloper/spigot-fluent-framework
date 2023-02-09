package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.resourcepack;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Consumer;

public class ResourcepackExtention implements FluentApiExtension {

    private final Consumer<ResourcepackOptions> consumer;
    private final String commandName = "resourcepack";

    private ResourcepackOptions options;

    public ResourcepackExtention(Consumer<ResourcepackOptions> options) {
        this.consumer = options;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        options = loadOptions(builder.config());


        builder.container().register(ResourcepackOptions.class, LifeTime.SINGLETON, container ->
        {
            return options;
        });
        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(commandName, commandBuilder ->
                    {
                        commandBuilder.propertiesConfig(propertiesConfig ->
                        {
                            propertiesConfig.setDescription("downloads plugin resourcepack");
                            propertiesConfig.setUsageMessage("/" + builder.defaultCommand().getName() + " " + commandName);
                        });

                        commandBuilder.subCommandsConfig(subCommandConfig1 ->
                        {
                            subCommandConfig1.addSubCommand("download", commandBuilder1 ->
                            {
                                commandBuilder1.propertiesConfig(propertiesConfig ->
                                        {
                                            propertiesConfig.setDescription("downloads plugin resourcepack");
                                            propertiesConfig.setUsageMessage("/" + builder.defaultCommand().getName() + " " + commandName + " download");
                                        })
                                        .eventsConfig(eventConfig ->
                                        {
                                            eventConfig.onPlayerExecute(event ->
                                            {
                                                byte[] sh1 = null;
                                                try {
                                                    sh1 = toSHA1(options.getDefaultUrl());
                                                    event.getPlayer().setResourcePack(options.getDefaultUrl(), sh1);
                                                } catch (Exception e) {
                                                    event.getPlayer().setResourcePack(options.getDefaultUrl());
                                                }
                                            });
                                        });
                            });

                            subCommandConfig1.addSubCommand("link", commandBuilder1 ->
                            {
                                commandBuilder1.propertiesConfig(propertiesConfig ->
                                        {
                                            propertiesConfig.setDescription("sending to player resourcepack link");
                                            propertiesConfig.setUsageMessage("/" + builder.defaultCommand().getName() + " " + commandName + " link");
                                        })
                                        .eventsConfig(eventConfig ->
                                        {
                                            eventConfig.onPlayerExecute(event ->
                                            {
                                                LinkMessageUtility.send(event.getPlayer(), options.getDefaultUrl(), "Resourcepack URL");
                                            });
                                        });
                            });
                        });

                    });
                });


    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        if (!options.isLoadOnJoin())
        {
          return;
        }
        fluentAPI.events().onEvent(PlayerJoinEvent.class, playerJoinEvent ->
        {
            playerJoinEvent.getPlayer().setResourcePack(options.getDefaultUrl());
        });
    }


    private ResourcepackOptions loadOptions(FluentConfig config) {
        var options = new ResourcepackOptions();
        consumer.accept(options);
        var customUrl = getCustomUrl(config, options);
        var loadOnJoin = getLoadOnJoin(config, options);

        options.setDefaultUrl(customUrl);
        options.setLoadOnJoin(loadOnJoin);
        return options;
    }


    private String getCustomUrl(FluentConfig config, ResourcepackOptions options) {
        var property = new ConfigProperty<String>("plugin.resourcepack.url",
                options.getDefaultUrl(),
                "If you need to replace default resourcepack with your custom one",
                "set this to link of you resourcepack",
                "! after plugin update make sure your custom resourcepack is compatible !"
        );
        return config.getOrCreate(property);
    }

    private boolean getLoadOnJoin(FluentConfig config, ResourcepackOptions options) {
        var property = new ConfigProperty<Boolean>("plugin.resourcepack.download-on-join",
                options.isLoadOnJoin(),
                "Downloads resourcepack when player joins to server");
        return config.getOrCreate(property);
    }


    public static byte[] toSHA1(String url) throws NoSuchAlgorithmException {
        var bytes = url.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return Base64.getEncoder().encode(md.digest(bytes));
    }

    public static String sha1Code(File file) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(file);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        // read all file content
        while (digestInputStream.read(bytes) > 0)
            digest = digestInputStream.getMessageDigest();
        byte[] resultByteArry = digest.digest();
        return bytesToHexString(resultByteArry);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int value = b & 0xFF;
            if (value < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(value).toUpperCase());
        }
        return sb.toString();
    }
}
