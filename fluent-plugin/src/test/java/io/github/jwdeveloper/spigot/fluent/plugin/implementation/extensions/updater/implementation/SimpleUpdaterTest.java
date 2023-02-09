package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.FluentUpdater;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.GithubUpdaterOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.implementation.providers.GithubInfoProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public class SimpleUpdaterTest {
    private static FluentUpdater updater;

    @BeforeAll
    public static void before() {
        MockBukkit.mock();
        var options = new GithubUpdaterOptions();
        options.setRepositoryName("JW_Piano");
        options.setGithubUserName("jwdeveloper");
        var githubProvider = new GithubInfoProvider(options);
        var logger = mock(SimpleLogger.class);
        var taskMock = mock(FluentTaskManager.class);
        var plugin = MockBukkit.createMockPlugin();
        var command = "/update";
        updater = new SimpleUpdater(githubProvider, taskMock, plugin, logger, command);

    }

    @AfterAll
    public static void after()
    {
        MockBukkit.unmock();
    }


    @Test
    public void checkUpdate() throws IOException {
       var result = updater.checkUpdate();
        Assertions.assertEquals(result.getUpdateInfo().getFileName(), "JW_Piano.jar");
    }

}