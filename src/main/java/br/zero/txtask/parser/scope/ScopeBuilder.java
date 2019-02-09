package br.zero.txtask.parser.scope;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ScopeBuilder<T> {

    private Supplier<AbstractScope<T>> scopeSupplier;
    private Scope<?> parent;
    private Consumer<T> consumer;

    public static <T> ScopeBuilder<T> newScope(Supplier<AbstractScope<T>> scopeSupplier) {
        ScopeBuilder<T> factory = new ScopeBuilder<>();

        factory.scopeSupplier(scopeSupplier);

        return factory;
    }

    public ScopeBuilder<T> parent(Scope<?> parent) {
        this.parent = parent;
        return this;
    }

    public ScopeBuilder<T> consumer(Consumer<T> consumer) {
        this.consumer = consumer;

        return this;
    }

    public ScopeBuilder<T> scopeSupplier(Supplier<AbstractScope<T>> supplier) {
        this.scopeSupplier = supplier;

        return this;
    }

    public Scope<T> make() {
        AbstractScope<T> scope = scopeSupplier.get();

        scope.setParentScope(this.parent);
        scope.setConsumer(consumer);

        return scope;
    }

}
