package br.zero.txtask.parser.scope.tasklist.title;

import java.io.IOException;

import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.reader.ParserReader;

public class ListTitleParser implements ElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws IOException, ParserException {
        reader.consume().next(Constants.LIST_TITLE_PREFIX.length()).go();

        String title = reader.consume().eol().go();

        String lineBreak = reader.consume().next(1).go();

        assert "\n".equals(lineBreak) || "".equals(lineBreak) : "After list title should have a line break or EOF";

        return title;
    }

}
