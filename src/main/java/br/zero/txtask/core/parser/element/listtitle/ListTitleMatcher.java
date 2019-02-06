package br.zero.txtask.core.parser.element.listtitle;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.core.parser.ParsingStep.TASK_TITLE;
import static br.zero.txtask.core.parser.element.listtitle.Constants.LIST_TITLE_PREFIX;

import java.io.IOException;

import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class ListTitleMatcher implements ElementMatcher<String> {

    private ElementParser<String> parser;

    public ListTitleMatcher() {
        this.parser = new ListTitleParser();
    }

    @Override
    public boolean matchs(ParserReader reader,
            ParserContext context) throws ParserException, IOException {
        boolean titleFollow = reader.followed().by(LIST_TITLE_PREFIX).go();

        if (context.step().equals(TASK_TITLE))
            return titleFollow;
        else if (titleFollow)
            throw new ParserException(s("List must start with '%s'").format(LIST_TITLE_PREFIX));
        else
            return false;
    }

    @Override
    public ElementParser<String> getParser() {
        return this.parser;
    }

}
