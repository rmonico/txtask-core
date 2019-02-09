package br.zero.txtask.parser.scope.task.title;

import br.zero.txtask.parser.scope.ElementParser;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.scope.tag.Constants.TAG_PREFIX;

public class TaskTitleParser implements ElementParser<String> {

    @Override
    public String parse(ParserReader reader) throws IOException {
        return reader.consume().until(TAG_PREFIX).or().eol().go();
    }
}
