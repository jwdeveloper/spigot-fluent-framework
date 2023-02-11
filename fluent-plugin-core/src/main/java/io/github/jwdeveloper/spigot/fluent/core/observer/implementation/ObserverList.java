package io.github.jwdeveloper.spigot.fluent.core.observer.implementation;

import io.github.jwdeveloper.spigot.fluent.core.observer.api.Observable;
import io.github.jwdeveloper.spigot.fluent.core.observer.api.ObserverListEvent;
import org.apache.commons.lang.NotImplementedException;

import java.util.*;
import java.util.function.Consumer;

public class ObserverList<T> extends ArrayList<T> implements Observable<ObserverListEvent> {
    private final Set<Consumer<ObserverListEvent>> subscribers;

    public ObserverList(List<T> values) {
        subscribers = new HashSet<>();
        this.addAll(values);
    }

    public void setContent(Collection<? extends T> c) {
        this.clear();
        addAll(c);
    }

    @Override
    public T remove(int index) {
        var value = super.get(index);
        var event = ObserverListEvent.create("remove", value);
        subscribers.forEach(e -> e.accept(event));
        if (event.isCancelled()) {
            return null;
        }
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        var event = ObserverListEvent.create("remove", o);
        subscribers.forEach(e -> e.accept(event));
        if (event.isCancelled()) {
            return false;
        }
        return super.remove(o);
    }

    @Override
    public boolean add(T t) {
        var event = ObserverListEvent.create("add", t);
        subscribers.forEach(e -> e.accept(event));
        if (event.isCancelled()) {
            return false;
        }
        return super.add(t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        var event = ObserverListEvent.create("AddAll", c);
        subscribers.forEach(e -> e.accept(event));
        if (event.isCancelled()) {
            return false;
        }
        return super.addAll(c);
    }


    @Override
    public void set(ObserverListEvent event) {
        subscribers.forEach(e -> e.accept(event));
    }

    @Override
    public ObserverListEvent get() {
        throw new NotImplementedException();
    }

    @Override
    public void invoke() {
        throw new NotImplementedException();
    }

    @Override
    public void subscribe(Consumer<ObserverListEvent> onChangeEvent) {
        subscribers.add(onChangeEvent);
    }

    @Override
    public void unsubscribe(Consumer<ObserverListEvent> onChangeEvent) {
        subscribers.remove(onChangeEvent);
    }


}
