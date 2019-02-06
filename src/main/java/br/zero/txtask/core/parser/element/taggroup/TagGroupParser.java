package br.zero.txtask.core.parser.element.taggroup;

import static br.zero.txtask.core.parser.element.abstracttag.Constants.IMPLICIT_TAG_INITIAL_PREFIX;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.abstracttag.AbstractTagParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TagGroupParser extends AbstractTagParser {

    public TagGroupParser() {
        super(IMPLICIT_TAG_INITIAL_PREFIX);
    }

    @Override
    public Tag parse(ParserReader reader) throws ParserException, IOException {
        return null;
    }

}
