package io.github.jwdeveloper.spigot.fluent.core.spigot.messages;


import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.boss_bar.BossBarBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.spigot.fluent.core.spigot.messages.title.SimpleTitleBuilder;
import org.bukkit.plugin.Plugin;

public class SimpleMessage
{

    public SimpleTitleBuilder title() {
        return new SimpleTitleBuilder();
    }

    public MessageBuilder chat() {
        return new MessageBuilder();
    }

    public BossBarBuilder bossBar() {
        return new BossBarBuilder();
    }
}
