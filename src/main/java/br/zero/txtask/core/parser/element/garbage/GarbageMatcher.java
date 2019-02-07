package br.zero.txtask.core.parser.element.garbage;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

public class GarbageMatcher implements ElementMatcher {

    @Override
    public boolean matchs(ParserReader reader) throws ParserException, IOException {
        return true;
    }

}
