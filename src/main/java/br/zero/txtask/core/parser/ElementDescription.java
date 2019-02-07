package br.zero.txtask.core.parser;

import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;

public class ElementDescription<T> {

    private ElementMatcher matcher;
    private ElementParser<T> parser;
    private Consumer<T> consumer;

    public ElementDescription(ElementMatcher matcher, ElementParser<T> parser, Consumer<T> consumer) {
        this.matcher = matcher;
        this.parser = parser;
        this.consumer = consumer;
    }

    public static <T> ElementDescription<T> createDescription(ElementMatcher matcher,
            ElementParser<T> parser,
            Consumer<T> consumer) {
        return new ElementDescription<T>(matcher, parser, consumer);
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