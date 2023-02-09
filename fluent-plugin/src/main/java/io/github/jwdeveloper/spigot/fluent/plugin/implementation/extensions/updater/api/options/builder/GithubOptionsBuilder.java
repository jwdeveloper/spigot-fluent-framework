package io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.builder;

import io.github.jwdeveloper.spigot.fluent.plugin.implementation.extensions.updater.api.options.GithubUpdaterOptions;

public class GithubOptionsBuilder
{
    private GithubUpdaterOptions options;

    public GithubOptionsBuilder()
    {
        options = new GithubUpdaterOptions();
    }

    public GithubOptionsBuilder setGithubUserName(String userName)
    {
        options.setGithubUserName(userName);
        return this;
    }

    public GithubOptionsBuilder setRepositoryName(String repositoryName)
    {
        options.setRepositoryName(repositoryName);
        return this;
    }

    public GithubOptionsBuilder setForceUpdate(boolean forceUpdate)
    {
        options.setForceUpdate(forceUpdate);
        return this;
    }

    public GithubOptionsBuilder setCheckUpdateOnStart(boolean checkUpdateOnStart)
    {
        options.setCheckUpdateOnStart(checkUpdateOnStart);
        return this;
    }

    public GithubOptionsBuilder setCommandName(String commandName)
    {
        options.setCommandName(commandName);
        return this;
    }

    public GithubUpdaterOptions build()
    {
        return options;
    }

}
