package br.zero.txtask.core.parser;

import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;

public interface Scope<T> {

    public static <T> Scope<T> createDescription(ElementMatcher matcher,
            ElementParser<T> parser,
            Consumer<T> consumer) {
        return new AbstractScope<T>(matcher, parser, consumer);
    }

    public ElementMatcher getMatcher();

    public ElementParser<T> getParser();

    public Consumer<T> getConsumer();

}