package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Task;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.ConstantParser.constantParser;
import static br.zero.txtask.parser.internal.Constants.TAG_PREFIX;
import static br.zero.txtask.parser.internal.TagsParser.tagsParser;
import static br.zero.txtask.parser.internal.TaskStatusParser.taskStatusParser;
import static br.zero.txtask.parser.internal.TaskTitleParser.taskTitleParser;

class TaskParser {

    private static TaskParser instance = new TaskParser();

    static TaskParser taskParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<Task> consumer) throws IOException, ParserException {
        while (taskStatusParser().matches(reader)) {
            Task task = new Task();

            task.setStatus(taskStatusParser().parse(reader));

            task.setTitle(taskTitleParser().parse(reader));

            tagsParser().parse(reader, task.getTags()::add, TAG_PREFIX);

            constantParser().parseUntilNextNonEmptyLine(reader);

            consumer.accept(task);
        }
    }

}
