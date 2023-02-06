package io.github.jwdeveloper.spigot.fluent.plugin.implementation.modules.metrics;

import io.github.jwdeveloper.spigot.fluent.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.spigot.fluent.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.FluentApiSpigot;

public class MetricsExtension implements FluentApiExtension {
    private final int metricsId;
    public MetricsExtension(int metricsId) {
        this.metricsId = metricsId;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        if (metricsId == 0) {
            return;
        }

        var metrics = new MetricsLite(builder.plugin(), metricsId);
        builder.container().registerSigleton(MetricsLite.class, metrics);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {


    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }
}
