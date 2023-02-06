package io.github.jwdeveloper.spigot.fluent.core.spigot.particles.implementation.builder;

import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.NextStep;
import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.ParticleSettings;

public class SimpleParticleBuilder extends ParticleBuilderBase implements NextStep<DetailBuilder>
{
    public SimpleParticleBuilder(final ParticleSettings particleSettings)
    {
       super(particleSettings);
        particleSettings.setTriggerEveryTicks(20);
        particleSettings.setStopAfterTicks(Integer.MAX_VALUE);
        particleSettings.setStartAfterTicks(Integer.MIN_VALUE);
    }

    public SimpleParticleBuilder triggerEveryTicks(int ticks)
    {
        particleSettings.setTriggerEveryTicks(ticks);
        return this;
    }

    public SimpleParticleBuilder triggerEverySeconds(int seconds)
    {
        particleSettings.setTriggerEveryTicks(seconds*20);
        return this;
    }

    public SimpleParticleBuilder startAfterTicks(int ticks)
    {
        particleSettings.setStartAfterTicks(ticks);
        return this;
    }

    public SimpleParticleBuilder startAfterSeconds(int seconds)
    {
        particleSettings.setStartAfterTicks(seconds*20);
        return this;
    }
    public SimpleParticleBuilder startAfterMinutes(int minute)
    {
        particleSettings.setStartAfterTicks(minute*60*20);
        return this;
    }
    public SimpleParticleBuilder stopAfterTicks(int ticks)
    {
        particleSettings.setStopAfterTicks(ticks);
        return this;
    }
    public SimpleParticleBuilder stopAfterSeconds(int seconds)
    {
        particleSettings.setStopAfterTicks(seconds*20);
        return this;
    }
    public SimpleParticleBuilder stopAfterMinutes(int minute)
    {
        particleSettings.setStopAfterTicks(minute*60*20);
        return this;
    }

    @Override
    public DetailBuilder nextStep()
    {
        return new DetailBuilder(particleSettings);
    }
}
