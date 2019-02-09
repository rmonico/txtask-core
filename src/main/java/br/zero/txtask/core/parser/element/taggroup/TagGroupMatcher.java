package br.zero.txtask.core.parser.element.taggroup;

import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

import java.io.IOException;

public class TagGroupMatcher implements ElementMatcher {

    private String initialTagPrefix;

    public TagGroupMatcher(String initialTagPrefix) {
        this.initialTagPrefix = initialTagPrefix;
    }

    @Override
    public boolean matchs(ParserReader reader) throws IOException {
        return reader.followed().by(this.initialTagPrefix).go();
    }


}
