package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Tag;
import br.zero.txtask.model.Task;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.internal.ConstantParser.parseUntilNextNonEmptyLine;
import static br.zero.txtask.parser.internal.Constants.TAG_PREFIX;

class TaskParser {

    private static TaskParser instance = new TaskParser();

    static Task parse(ParserReader reader) throws IOException, ParserException {
        return instance.internalParse(reader);
    }

    Task internalParse(ParserReader reader) throws IOException, ParserException {
        Task task = new Task();

        task.setStatus(TaskStatusParser.parse(reader));

        task.setTitle(TaskTitleParser.parse(reader));

        TagsParser.parse(reader, task.getTags()::add, TAG_PREFIX);

        parseUntilNextNonEmptyLine(reader);

        return task;
    }

}
