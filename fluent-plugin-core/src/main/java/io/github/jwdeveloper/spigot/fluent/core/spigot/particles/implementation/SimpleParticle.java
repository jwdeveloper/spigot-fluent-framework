package io.github.jwdeveloper.spigot.fluent.core.spigot.particles.implementation;


import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.particles.api.ParticleSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

//Generator https://cloudwolfyt.github.io/pages/gens/particle-plots.html
public class SimpleParticle {

    private final Plugin plugin;
    private final SimpleLogger logger;
    private final ParticleInvoker particleInvoker;
    private final ParticleSettings settings;
    private BukkitTask bukkitTask;
    private int time;

    public SimpleParticle(ParticleSettings particleSettings,
                          Plugin plugin,
                          SimpleLogger logger) {
        this.plugin = plugin;
        this.logger = logger;
        this.settings = particleSettings;
        this.particleInvoker = new ParticleInvoker();
    }

    public void start() {
        stop();
        run();
    }

    public void followEntity(Entity entity) {
        this.settings.setEntityToTrack(entity);
    }

    public void setLocation(Location location) {
        this.settings.setLocation(location);
    }

    private void run()
    {
        bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin,
                () -> {
                    try {
                        if (time >= settings.getStopAfterTicks()) {
                            stop();
                            return;
                        }
                        var event = new ParticleEvent();
                        event.originLocation = settings.getLocation().clone();
                        event.index = time % settings.getParticleCount();
                        event.particleColor = settings.getColor();
                        event.particle = settings.getParticle();
                        event.setParticleColor(settings.getColor());
                        event.time = time;
                        switch (settings.getParticleDisplayMode()) {
                            case ALL_AT_ONCE -> {
                                for (var i = 0; i < settings.getParticleCount(); i++) {
                                    event.originLocation = settings.getLocation().clone();
                                    event.index = i;
                                    settings.getOnParticleEvent().execute(event, particleInvoker);
                                }
                                return;
                            }
                            case SINGLE_AT_ONCE -> {
                                event.index = time % settings.getParticleCount();
                                settings.getOnParticleEvent().execute(event, particleInvoker);
                                return;
                            }
                        }
                        time++;
                    } catch (Exception e) {
                        logger.error("Error while running particleEffect", e);
                        bukkitTask.cancel();
                    }
                },
                settings.getStartAfterTicks(),
                settings.getTriggerEveryTicks());
    }


    public boolean isRunning() {
        return bukkitTask != null;
    }

    public void stop() {
        if (bukkitTask == null)
        {
            return;
        }

        bukkitTask.cancel();
        time = 0;
        settings.setOnStop(null);
    }
}
