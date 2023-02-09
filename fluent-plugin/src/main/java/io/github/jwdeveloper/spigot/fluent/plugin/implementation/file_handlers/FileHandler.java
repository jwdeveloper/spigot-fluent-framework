package io.github.jwdeveloper.spigot.fluent.plugin.implementation.file_handlers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface FileHandler
{
  public void load() throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException;

  public void save();

  public void addObject(Object object);

  public void removeObject(Object object);
}
