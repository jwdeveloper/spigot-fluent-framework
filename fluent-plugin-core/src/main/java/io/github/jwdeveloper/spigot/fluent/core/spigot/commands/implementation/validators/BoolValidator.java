package io.github.jwdeveloper.spigot.fluent.core.spigot.commands.implementation.validators;


import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.models.ValidationResult;

public class BoolValidator implements CommandArgumentValidator{
    @Override
    public ValidationResult validate(String arg)
    {
        arg = arg.toUpperCase();
        if(arg.matches("^([T][R][U][E]|[F][A][L][S][E])$"))
         return new ValidationResult(true,"");
        else
         return new ValidationResult(false,"should be True or False");
    }
}
