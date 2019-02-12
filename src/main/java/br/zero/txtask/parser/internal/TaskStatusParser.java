package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Status;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.Map;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.parser.ParserException.error;
import static br.zero.txtask.parser.internal.Constants.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;

class TaskStatusParser {

    private static final TaskStatusParser instance = new TaskStatusParser();

    static TaskStatusParser taskStatusParser() {
        return instance;
    }

    Status parse(ParserReader reader, int identLevel) throws IOException, ParserException {
        String[] prefixes = getPrefixes(identLevel);

        if (!this.matches(reader, prefixes))
            error("Cant parse task status", reader);

        int statusIndex = reader.followed().byAnyOf(prefixes).which();

        String statusString = reader.consume().next(prefixes[statusIndex].length()).go();

        statusString = statusString.substring((SUB_TASK_IDENT.length() * identLevel));

        Status status = getTaskStatusByPrefix(statusString);

        assert status != null : s("Task status not found: '%s'").format(statusString);

        return status;
    }

    private String[] getPrefixes(int identLevel) {
        final String prefixString = range(0, identLevel).mapToObj(i -> SUB_TASK_IDENT).collect(joining());

        return of(TASK_STATUSES_ARRAY).map(s -> prefixString + s).toArray(String[]::new);
    }

    boolean matches(ParserReader reader, int identLevel) throws IOException {
        return this.matches(reader, getPrefixes(identLevel));
    }

    boolean matches(ParserReader reader, String[] prefixes) throws IOException {
        return reader.followed().byAnyOf(prefixes).which() > -1;
    }

    private Status getTaskStatusByPrefix(String prefix) {
        for (Map.Entry<Status, String> entry : TASK_STATUSES.entrySet())
            if (entry.getValue().equals(prefix))
                return entry.getKey();

        return null;
    }

}
