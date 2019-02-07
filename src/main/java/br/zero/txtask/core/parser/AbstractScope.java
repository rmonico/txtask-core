package br.zero.txtask.core.parser;

import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;

public abstract class AbstractScope<T> implements Scope<T> {

    private ElementMatcher matcher;
    private ElementParser<T> parser;
    private Consumer<T> consumer;

    public AbstractScope(ElementMatcher matcher, ElementParser<T> parser, Consumer<T> consumer) {
        this.matcher = matcher;
        this.parser = parser;
        this.consumer = consumer;
    }

    public ElementMatcher getMatcher() {
        return matcher;
    }

    public ElementParser<T> getParser() {
        return parser;
    }

    public Consumer<T> getConsumer() {
        return consumer;
    }

}
