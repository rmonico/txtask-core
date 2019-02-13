package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Tag;
import br.zero.txtask.model.TaskList;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.CommentParser.commentParser;
import static br.zero.txtask.parser.internal.ConstantParser.constantParser;
import static br.zero.txtask.parser.internal.Constants.IMPLICIT_TAG_INSERTION_MARK;
import static br.zero.txtask.parser.internal.Constants.IMPLICIT_TAG_REMOVAL_MARK;
import static br.zero.txtask.parser.internal.ListTitleParser.listTitleParser;
import static br.zero.txtask.parser.internal.TagsParser.tagsParser;
import static br.zero.txtask.parser.internal.TaskParser.taskParser;

class TaskListParser {

    private static final TaskListParser instance = new TaskListParser();

    static TaskListParser listParser() {
        return instance;
    }

    TaskList parse(ParserReader reader) throws IOException, ParserException {
        TaskList taskList = new TaskList();

        taskList.setTitle(listTitleParser().parse(reader));

        if (reader.finished())
            return taskList;

        commentParser().parse(reader, taskList::setComment, 0);

        List<Tag> implicitTags = new ArrayList<>();

        while (!reader.finished()) {
            int initialPosition = reader.position();

            constantParser().parseUntilNextNonEmptyLine(reader);

            taskParser().parse(reader, t -> {
                t.getTags().addAll(implicitTags);
                taskList.getTasks().add(t);
            });

            tagsParser().parse(reader, implicitTags::add, IMPLICIT_TAG_INSERTION_MARK);

            tagsParser().parse(reader, implicitTags::remove, IMPLICIT_TAG_REMOVAL_MARK);

            if (initialPosition == reader.position())
                error("Invalid token", reader);
        }

        return taskList;
    }

}
