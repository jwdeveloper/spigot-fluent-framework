package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.UpdaterOptions;
import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.builder.GithubOptionsBuilder;

public class UpdaterApiOptions
{
    private GithubOptionsBuilder githubOptionsBuilder;

    public UpdaterApiOptions()
    {
        githubOptionsBuilder = new GithubOptionsBuilder();
    }
    public GithubOptionsBuilder useGithub()
    {
        return githubOptionsBuilder;
    }

    public UpdaterOptions getOptions()
    {
        return githubOptionsBuilder.build();
    }
}
