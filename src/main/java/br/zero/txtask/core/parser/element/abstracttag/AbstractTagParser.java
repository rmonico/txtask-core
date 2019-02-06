package br.zero.txtask.core.parser.element.abstracttag;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public abstract class AbstractTagParser implements ElementParser<Tag> {

    private String prefix;

    public AbstractTagParser(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Tag parse(ParserReader reader) throws ParserException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}
