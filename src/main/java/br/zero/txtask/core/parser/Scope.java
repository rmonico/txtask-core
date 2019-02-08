package br.zero.txtask.core.parser;

import java.io.IOException;
import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public interface Scope<T> {

    Scope<?> findScope(ParserReader reader) throws ParserException, IOException;

    Scope<?> getParentScope();

    ElementMatcher getMatcher();

    ElementParser<T> getParser();

    Consumer<T> getConsumer();

    boolean done();
}