package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.translator;

import io.github.jwdeveloper.spigot.fluent.core.common.java.StringUtils;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.files.FileUtility;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.api.enums.ArgumentDisplay;
import jw.fluent.api.spigot.permissions.api.PermissionModel;
import jw.fluent.api.translator.implementation.SimpleLangLoader;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.config.ConfigProperty;
import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.plugin.implementation.modules.permissions.api.FluentPermissionBuilder;
import org.bukkit.ChatColor;

import java.io.File;

public class FluentTranslationExtension implements FluentApiExtension {
    private final String CONFIG_LANG_PATH = "plugin.language";
    private final String COMMAND_NAME = "lang";
    private FluentTranslatorImpl fluentTranslator;

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        fluentTranslator = new FluentTranslatorImpl();
        builder.container().register(FluentTranslator.class, LifeTime.SINGLETON, (x) -> fluentTranslator);

        var permission = createPermission(builder.permissions());
        builder.permissions().registerPermission(permission);
        builder.defaultCommand().subCommandsConfig(subCommandConfig ->
        {
            subCommandConfig.addSubCommand(createCommand(permission, builder.defaultCommand().getName(), builder.config(),fluentTranslator));
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var basePath = fluentAPI.path() + File.separator + "languages";
        var loader = new SimpleLangLoader(fluentAPI.plugin());
        FileUtility.ensurePath(basePath);
        loader.generateFiles(basePath);
        var langName = getPluginLanguage(fluentAPI.config(), fluentAPI.logger());
        var langDatas = loader.load(basePath, langName);
        fluentTranslator.setLanguages(langDatas, langName);

       // fluentTranslator.generateEmptyTranlations();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }

    private String getPluginLanguage(FluentConfig configFile, SimpleLogger logger) {

        var configProperty = createConfigLanguage();
        var languageValue = configFile.getOrCreate(configProperty);
        if (StringUtils.isNullOrEmpty(languageValue))
        {
            logger.warning("Unable to load `" + CONFIG_LANG_PATH + "` from config");
            return "en";
        }
        return languageValue;
    }

    private CommandBuilder createCommand(PermissionModel permission, String defaultPermissionName, FluentConfig configFile, FluentTranslator translator) {
        return FluentCommand.create(COMMAND_NAME)
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(permission.getName());
                    propertiesConfig.setDescription("Changes plugin languages, changes will be applied after server reload. Change be use both be player or console");
                    propertiesConfig.setUsageMessage("/" + defaultPermissionName + " " + COMMAND_NAME + " <language>");
                })
                .argumentsConfig(argumentConfig ->
                {
                    argumentConfig.addArgument("language", argumentBuilder ->
                    {
                        argumentBuilder.setTabComplete(translator.getLanguagesName());
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                        argumentBuilder.setDescription("select language");
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var languageName = commandEvent.getCommandArgs()[0];
                        if (!translator.langAvaliable(languageName)) {
                            FluentMessage.message()
                                    .warning()
                                    .text(" Language ", ChatColor.GRAY)
                                    .text(languageName, ChatColor.RED)
                                    .text(" not found ", ChatColor.GRAY)
                                    .send(commandEvent.getSender());
                            return;
                        }
                        configFile.configFile().set(CONFIG_LANG_PATH, languageName);
                        configFile.save();
                        FluentMessage.message()
                                .info()
                                .textSecondary(" Language has been changed to ")
                                .textPrimary(languageName)
                                .textSecondary(" use ")
                                .textPrimary("/reload")
                                .textSecondary(" to apply changes")
                                .send(commandEvent.getSender());
                    });
                });
    }

    private PermissionModel createPermission(FluentPermissionBuilder builder) {
        var permission = new PermissionModel();
        permission.setName("lang");
        permission.setDescription("Allow player to change plugin language");
        builder.defaultPermissionSections().commands().addChild(permission);
        return permission;
    }


    private ConfigProperty<String> createConfigLanguage()
    {
        return new ConfigProperty<String>(CONFIG_LANG_PATH,"en","If you want add your language open `languages` folder copy `en.yml` call it as you want \\n\" +\n" +
                " \"set `language` property to your file name and /reload server ");
    }
}
