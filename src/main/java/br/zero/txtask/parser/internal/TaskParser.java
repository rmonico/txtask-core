package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Task;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.ConstantParser.constantParser;
import static br.zero.txtask.parser.internal.Constants.TAG_MARK;
import static br.zero.txtask.parser.internal.TagsParser.tagsParser;
import static br.zero.txtask.parser.internal.TaskStatusParser.taskStatusParser;
import static br.zero.txtask.parser.internal.TaskTitleParser.taskTitleParser;

class TaskParser {

    private static TaskParser instance = new TaskParser();

    static TaskParser taskParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<Task> consumer) throws IOException, ParserException {
        this.parse(reader, consumer, 0);
    }

    void parse(ParserReader reader, Consumer<Task> consumer, int identLevel) throws IOException, ParserException {
        while (taskStatusParser().matches(reader, identLevel)) {
            Task task = new Task();

            task.setStatus(taskStatusParser().parse(reader, identLevel));

            String title = taskTitleParser().parse(reader);

            if (title.contains(TAG_MARK))
                throw new ParserException(s("Task title cant have TAG_MARK ('%s')").format(TAG_MARK));

            task.setTitle(title);

            tagsParser().parse(reader, task.getTags()::add, TAG_MARK);

            constantParser().parseUntilNextNonEmptyLine(reader);

            taskParser().parse(reader, task.getTasks()::add, identLevel + 1);

            consumer.accept(task);
        }
    }

}
