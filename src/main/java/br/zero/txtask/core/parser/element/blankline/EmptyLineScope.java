package br.zero.txtask.core.parser.element.blankline;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

public class EmptyLineScope implements ElementMatcher {

    @Override
    public boolean matchs(ParserReader reader) throws ParserException, IOException {
        return reader.followed().by("\n").go();
    }

}
