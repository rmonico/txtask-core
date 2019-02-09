package br.zero.txtask.parser.scope;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

public abstract class SingleScope<T> extends AbstractScope<T> {

    public SingleScope(ElementParser<T> parser) {
        setParser(parser);
    }

    protected abstract boolean matches(ParserReader reader) throws IOException, ParserException;

    @Override
    public Scope<?> findScope(ParserReader reader) throws IOException, ParserException {
        if (this.matches(reader))
            return this;
        else
            return null;
    }

    @Override
    public boolean done() {
        return true;
    }
}
