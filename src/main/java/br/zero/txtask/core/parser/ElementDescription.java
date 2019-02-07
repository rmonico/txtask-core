package br.zero.txtask.core.parser;

import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;

public class ElementDescription<T> {

    public ElementMatcher matcher;
    public ElementParser<T> parser;
    public Consumer<T> consumer;

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

}