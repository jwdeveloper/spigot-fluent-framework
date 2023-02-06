package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.validators;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.ValidationResult;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg);
}
