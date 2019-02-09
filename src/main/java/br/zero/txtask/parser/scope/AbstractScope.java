package br.zero.txtask.parser.scope;

import java.util.function.Consumer;

public abstract class AbstractScope<T> implements Scope<T> {

    private Scope<?> parentScope;
    private ElementParser<T> parser;
    private Consumer<T> consumer;

    public void setParentScope(Scope<?> parentScope) {
        this.parentScope = parentScope;
    }

    public Scope<?> getParentScope() {
        return parentScope;
    }

    protected void setParser(ElementParser<T> parser) {
        this.parser = parser;
    }

    public ElementParser<T> getParser() {
        return this.parser;
    }

    public Consumer<T> getConsumer() {
        return this.consumer;
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public boolean done() {
        return false;
    }
}
