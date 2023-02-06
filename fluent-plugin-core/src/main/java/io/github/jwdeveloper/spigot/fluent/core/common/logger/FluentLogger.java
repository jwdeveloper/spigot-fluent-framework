package io.github.jwdeveloper.spigot.fluent.core.common.logger;

import java.util.logging.Logger;

public class FluentLogger
{
    public static SimpleLogger LOGGER;

    public static void setLogger(Logger logger)
    {
        if(LOGGER == null)
        {
            LOGGER = new SimpleLogger(logger);
        }
    }
}
