package br.zero.txtask.core.parser;

import java.io.IOException;
import java.util.function.Consumer;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public interface Scope<T> {

    public ElementMatcher getMatcher();

    public Scope<?> findScope(ParserReader reader) throws ParserException, IOException;

    public ElementParser<T> getParser();

    public Consumer<T> getConsumer();

}