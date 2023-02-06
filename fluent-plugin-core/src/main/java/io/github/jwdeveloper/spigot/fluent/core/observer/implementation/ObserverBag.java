package io.github.jwdeveloper.spigot.fluent.core.observer.implementation;

import io.github.jwdeveloper.spigot.fluent.core.observer.api.Observable;
import lombok.Getter;

import java.util.function.Consumer;

public class ObserverBag<T> implements Observable<T> {
    @Getter
    private final Observer<T> observer;
    private T value;

    public ObserverBag(T initValue) {
        value = initValue;
        observer = new Observer<>();
        observer.bind(this.getClass(), "value");
        observer.setObject(this);
    }

    @Override
    public T get() {
        return observer.get();
    }

    @Override
    public void invoke() {
        observer.invoke();
    }

    @Override
    public void subscribe(Consumer<T> onChangeEvent) {
        observer.subscribe(onChangeEvent);
    }

    @Override
    public void unsubscribe(Consumer<T> onChangeEvent) {
        observer.unsubscribe(onChangeEvent);
    }

    @Override
    public void set(T value) {
        observer.set(value);
    }
}
