package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.files;

import io.github.jwdeveloper.spigot.fluent.core.injector.api.containers.FluentContainer;
import io.github.jwdeveloper.spigot.fluent.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.spigot.fluent.core.repository.api.CustomFile;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.ConfigSection;
import io.github.jwdeveloper.spigot.fluent.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.file_handlers.FilesDataContext;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.file_handlers.JsonFile;

public class FluentFilesExtention implements FluentApiExtension {

    private SimpleTaskTimer savingTask;
    private FilesDataContext filesDataContext;


    @Override
    public void onConfiguration(FluentApiSpigotBuilder fluentApiBuilder)
    {
        fluentApiBuilder.container().register(FluentFiles.class, LifeTime.SINGLETON,(x) ->
        {
            //TODO pass path
            filesDataContext = new FluentFilesImpl("PATH");
            var searchContainer = (FluentContainer)x;

            var config = (FluentConfig)searchContainer.find(FluentConfig.class);
            var tasks = (FluentTaskManager)searchContainer.find(FluentTaskManager.class);

            var customFiles = searchContainer.findAllByInterface(CustomFile.class);
            var jsonFiles =  searchContainer.findAllByAnnotation(JsonFile.class);
            var configSections =  searchContainer.findAllByInterface(ConfigSection.class);
            for (var file: customFiles)
            {
                filesDataContext.addCustomFileObject(file);
            }
            for (var file: jsonFiles)
            {
                filesDataContext.addJsonObject(file);
            }
            for(var file : configSections)
            {
                filesDataContext.addConfigFileObject(file);
            }
            var savingFrequency = getConfigSavingFrequency(config);
            savingTask = tasks.taskTimer(20*savingFrequency*60,(iteration, task) ->
            {
                filesDataContext.save();
            });
            savingTask.runAsync();
            return filesDataContext;
        });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        filesDataContext.load();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {
        if(filesDataContext == null)
        {
            return;
        }
        filesDataContext.save();
        if(savingTask == null)
        {
            return;
        }
        savingTask.cancel();
    }

    private int getConfigSavingFrequency(FluentConfig configFile)
    {
        var property = createSavingPropertyConfig();
        var propertyValue =  configFile.getOrCreate(property);
        return propertyValue;
    }


    public ConfigProperty<Integer> createSavingPropertyConfig()
    {
        return new ConfigProperty<Integer>("plugin.saving-frequency", 5,"Determinate how frequent data is saved to files, value in minutes");
    }
}
