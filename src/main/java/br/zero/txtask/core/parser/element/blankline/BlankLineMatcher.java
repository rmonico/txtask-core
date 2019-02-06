package br.zero.txtask.core.parser.element.blankline;

import java.io.IOException;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.ElementMatcher;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class BlankLineMatcher implements ElementMatcher<String> {

    @Override
    public boolean matchs(ParserReader reader,
            ParserContext context) throws ParserException, IOException {
        return reader.followed().by("\n").go();
    }

    @Override
    public ElementParser<String> getParser() {
        return new ElementParser<String>() {

            @Override
            public void put(TaskList taskList,
                    ParserContext context,
                    Object element) throws ParserException {
            }

            @Override
            public String parse(ParserReader reader) throws ParserException, IOException {
                String line = reader.consume().next(1).go();

                assert "\n".equals(line) : "Trying to parse with BlankLineParser on non-blank line";

                return line;
            }
        };
    }

}
