package br.zero.txtask.core.parser.element.taggroup;

import java.io.IOException;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.element.abstracttag.AbstractTagParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class TagGroupParser extends AbstractTagParser {

    public TagGroupParser(String prefix) {
        super(prefix);
    }

    @Override
    public Tag parse(ParserReader reader) throws ParserException, IOException {
        return null;
    }

    @Override
    protected void internalPut(TaskList taskList,
            Tag element) {

    }

}
