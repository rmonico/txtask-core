package br.zero.txtask.core.parser;

import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;

public abstract class AbstractScope<T> implements Scope<T> {

    private Scope<?> parentScope;
    private ElementMatcher matcher;
    private ElementParser<T> parser;
    private Consumer<T> consumer;

    public void setParentScope(Scope<?> parentScope) {
        this.parentScope = parentScope;
    }

    public Scope<?> getParentScope() {
        return parentScope;
    }

    public void setMatcher(ElementMatcher matcher) {
        this.matcher = matcher;
    }

    public ElementMatcher getMatcher() {
        return matcher;
    }

    public void setParser(ElementParser<T> parser) {
        this.parser = parser;
    }

    public ElementParser<T> getParser() {
        return parser;
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

    @Override
    public boolean done() {
        return false;
    }
}
