package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.validators;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.ValidationResult;

public class EnumValidator<T extends Enum<T>> implements CommandArgumentValidator
{

    private final Class<T> enumClass;

    public EnumValidator(Class<T> enumClass)
    {
        this.enumClass =enumClass;
    }

    @Override
    public ValidationResult validate(String arg)
    {
        try {
            Enum.valueOf(enumClass, arg.toUpperCase());
            return new ValidationResult(true,"");
        } catch (Exception e) {
            return new ValidationResult(false,"should be " +enumClass.getSimpleName()+" name");
        }
    }
}
