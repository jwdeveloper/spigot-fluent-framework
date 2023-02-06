package io.github.jwdeveloper.spigot.fluent.core.spigot.particles.implementation.builder;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.FluentLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.ParticleSettings;
import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.implementation.SimpleParticle;

public class FinalizeBuild  {

    private final ParticleSettings particleSettings;

    public FinalizeBuild(final ParticleSettings particleSettings) {
        this.particleSettings = particleSettings;
    }

    public SimpleParticle build() {
        //TODO PASS PLUGIN INSTANCE
        return new SimpleParticle(particleSettings, null, FluentLogger.LOGGER);
    }

    public SimpleParticle buildAndStart() {
        var result = build();
        result.start();
        return result;
    }
}
