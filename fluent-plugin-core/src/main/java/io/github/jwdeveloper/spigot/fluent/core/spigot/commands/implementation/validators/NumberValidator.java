package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.validators;


import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.ValidationResult;

public class NumberValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg) {
        if(arg.matches("^[1-9]\\d*(\\.\\d+)?$"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be number");
    }
}
