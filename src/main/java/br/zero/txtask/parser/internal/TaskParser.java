package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Task;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;

import static br.zero.txtask.parser.internal.ConstantParser.parseUntilNextNonEmptyLine;

class TaskParser {

    private static TaskParser instance = new TaskParser();

    static Task parse(ParserReader reader) throws IOException, ParserException {
        return instance.internalParse(reader);
    }

    Task internalParse(ParserReader reader) throws IOException, ParserException {
        Task task = new Task();

        task.setStatus(TaskStatusParser.parse(reader));

        task.setTitle(TaskTitleParser.parse(reader));

        parseUntilNextNonEmptyLine(reader);

        return task;
    }

}
