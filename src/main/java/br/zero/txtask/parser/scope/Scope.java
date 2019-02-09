package br.zero.txtask.parser.scope;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

public interface Scope<T> {

    Scope<?> findScope(ParserReader reader) throws ParserException, IOException;

    Scope<?> getParentScope();

    ElementParser<T> getParser();

    Consumer<T> getConsumer();

    boolean done();
}