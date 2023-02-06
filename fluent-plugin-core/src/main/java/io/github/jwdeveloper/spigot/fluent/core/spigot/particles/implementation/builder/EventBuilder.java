package io.github.jwdeveloper.spigot.fluent.core.spigot.particles.implementation.builder;

import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.NextStep;
import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.OnParticleEvent;
import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.ParticleSettings;


public class EventBuilder  extends ParticleBuilderBase implements NextStep<FinalizeBuild>
{

    public EventBuilder(final ParticleSettings particleSettings)
    {
        super(particleSettings);
    }

    public EventBuilder onStart(OnParticleEvent particleEvent)
    {
        particleSettings.setOnStart(particleEvent);
        return this;
    }

    public EventBuilder onParticle(OnParticleEvent particleEvent)
    {
        particleSettings.setOnParticleEvent(particleEvent);
        return this;
    }

    public EventBuilder onStop(OnParticleEvent particleEvent)
    {
        particleSettings.setOnStop(particleEvent);
        return this;
    }

    @Override
    public FinalizeBuild nextStep()
    {
        return new FinalizeBuild(particleSettings);
    }
}
