package br.zero.txtask.core.parser.element.listtitle;

import static br.zero.txtask.core.parser.element.listtitle.Constants.LIST_TITLE_PREFIX;

import java.io.IOException;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.AbstractElementParser;
import br.zero.txtask.core.parser.reader.ParserReader;

public class ListTitleParser extends AbstractElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws IOException, ParserException {
        reader.consume().next(LIST_TITLE_PREFIX.length()).go();

        String title = reader.consume().eol().go();

        String lineBreak = reader.consume().next(1).go();

        assert "\n".equals(lineBreak) || "".equals(lineBreak) : "After list title should have a line break or EOF";

        return title;
    }

    @Override
    public void internalPut(TaskList taskList,
            ParserContext context,
            String title) {
        taskList.setTitle(title);
    }

}
