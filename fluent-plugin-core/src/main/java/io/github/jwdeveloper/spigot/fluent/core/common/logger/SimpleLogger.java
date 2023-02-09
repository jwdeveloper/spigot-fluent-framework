package io.github.jwdeveloper.spigot.fluent.core.common.logger;

import io.github.jwdeveloper.spigot.fluent.core.common.TextBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLogger {
    private final Logger logger;
    private final String errorBar;

    public SimpleLogger(Logger logger) {
        assert logger != null;
        this.logger = logger;
        errorBar = getBuilder().newLine().text(ChatColor.BOLD).text(ChatColor.DARK_RED).bar("-", 100).newLine().toString();
    }

    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    public void info(Object... messages) {
        var message = getBuilder()
                .text(getPrefix("Info", ChatColor.AQUA))
                .text(messages)
                .toString();
     //   logger.info(message);
        send(message);
    }

    public void success(Object... messages) {
        var message = getBuilder()
                .text(getPrefix("Success", ChatColor.GREEN))
                .text(messages)
                .toString();
       // logger.log(Level.FINEST, message);
        send(message);
    }

    public void warning(Object... messages) {
        var message = getBuilder()
                .text(getPrefix("Warning", ChatColor.YELLOW))
                .text(messages)
                .toString();
        send(message);
    }

    public void error(String message) {
        var msg = getBuilder()
                .text(getPrefix("Error", ChatColor.RED))
                .text(message)
                .toString();
       // logger.log(Level.WARNING, message);
        send(msg);
    }

    public void error(String message, Throwable throwable) {
        var description = getErrorDescription(message, throwable);
        var stackTrace = getStackTrace(throwable);
        var errorMessage = getBuilder()
                .text(description, errorBar, stackTrace, errorBar)
                .toString();
      //  logger.log(Level.WARNING, errorMessage);
        send(errorMessage);
    }


    private void send(String message)
    {
        if(Bukkit.getConsoleSender() != null)
        {
            Bukkit.getConsoleSender().sendMessage(message);
            return;
        }
        System.out.println(message);
    }

    private TextBuilder getErrorDescription(String message, Throwable exception) {
        var stackTrace = new TextBuilder();

        stackTrace.newLine()
                .text(ChatColor.DARK_RED).text("[Critical Error]").space()
                .text(ChatColor.RED).text(message)
                .text(ChatColor.RESET);
        if (exception == null) {
            return stackTrace;
        }
        var cause = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        stackTrace.newLine().text(ChatColor.DARK_RED)
                .text("[Reason]")
                .text(ChatColor.YELLOW)
                .space()
                .text(cause)
                .text(ChatColor.RESET)
                .newLine()
                .text(ChatColor.DARK_RED).text("[Exception type]")
                .text(ChatColor.YELLOW).space().text(exception.getClass().getSimpleName())
                .text(ChatColor.RESET);
        return stackTrace;
    }

    private TextBuilder getStackTrace(Throwable exception) {
        var builder = new TextBuilder();
        if (exception == null) {
            return builder;
        }
        var offset = 6;
        builder.text(ChatColor.RESET);
        for (var trace : exception.getStackTrace()) {
            offset = 6;
            offset = offset - (trace.getLineNumber() + "").length();
            builder
                    .newLine()
                    .text(ChatColor.WHITE)
                    .text("at line", ChatColor.WHITE)
                    .space(2)
                    .text(trace.getLineNumber(), ChatColor.AQUA)
                    .space(offset)
                    .text("in", ChatColor.WHITE)
                    .space()
                    .text(trace.getClassName(), ChatColor.GRAY)
                    .text("." + trace.getMethodName() + "()", ChatColor.AQUA)
                    .space()
                    .text(ChatColor.RESET);
        }
        return builder;
    }

    private String getPrefix(String name, ChatColor color) {
        return getBuilder().text(color, "[", name, "] ", ChatColor.RESET).toString();
    }
    private TextBuilder getBuilder() {
        return new TextBuilder();
    }

}
