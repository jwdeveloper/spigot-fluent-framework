package io.github.jwdeveloper.spigot.fluent.core.observer.api;

import java.util.function.Consumer;

public interface Observable<T> {
    void set(T value);
    T get();
    void invoke();
    void subscribe(Consumer<T> onChangeEvent);
    void unsubscribe(Consumer<T> onChangeEvent);
}
