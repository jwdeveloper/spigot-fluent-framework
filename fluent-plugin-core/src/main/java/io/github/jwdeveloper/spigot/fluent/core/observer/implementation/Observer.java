package io.github.jwdeveloper.spigot.fluent.core.observer.implementation;

import io.github.jwdeveloper.spigot.fluent.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.spigot.fluent.core.observer.api.Observable;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Observer<T> implements Observable<T> {
    private Field field;
    private Object object;
    private Class<?> fieldType;
    private boolean binded;
    protected Set<Consumer<T>> onChange = new LinkedHashSet<>();
    private final SimpleLogger logger;

    public Observer() {
        logger = new SimpleLogger();
    }

    public Observer(Object classObject, String field) {
        this();
        binded = bind(classObject.getClass(), field);
        this.object = classObject;

    }

    public Observer(Object classObject, Field field) {
        this();
        this.field = field;
        this.object = classObject;
        this.fieldType = this.field.getType();
        binded = true;
    }


    @Override
    public T get() {
        if (!binded)
            return null;
        try {
            return (T) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void set(T value) {
        if (!binded)
            return;
        try {
            field.set(object, value);  //set new value to field
            for (Consumer<T> onChangeEvent : onChange) {
                onChangeEvent.accept(value);          //trigger all OnValueChange events
            }
        } catch (Exception e) {
            logger.error("Set binding field: ", e);
        }
    }

    public boolean bind(Class<?> _class, String filedName) {
        try {
            this.field = _class.getDeclaredField(filedName);
            this.field.setAccessible(true);
            this.fieldType = this.field.getType();
            binded = true;
            return true;
        } catch (NoSuchFieldException e) {
            logger.error("Binding error:" + e.getMessage() + " Field: " + filedName, e);
            return false;
        }
    }

    public Class<?> getValueType() {
        return fieldType;
    }

    public void subscribe(Consumer<T> onChangeEvent) {
        this.onChange.add(onChangeEvent);
    }

    public void unsubscribe(Consumer<T> onChangeEvent) {
        this.onChange.remove(onChangeEvent);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFieldName() {
        return field != null ? field.getName() : "";
    }

    public void invoke() {
        set(get());
    }
}
