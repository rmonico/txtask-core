package br.zero.txtask.core.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.reader.ParserReader;

class TaskListParserImpl implements TaskListParser {

    @Override
    public TaskList parse(Reader source) throws ParserException {
        ParserReader parserReader = new ParserReader(source);
        return this.doParse(parserReader);
    }

    private TaskList doParse(ParserReader reader) throws ParserException {
        try {
            if (reader.finished())
                throw new ParserException("List is empty");

            return internalParse(reader);
        } catch (IOException e) {
            throw new ParserException(e);
        }
    }

    private TaskList internalParse(ParserReader reader) throws ParserException, IOException {
        TaskListScope mainScope = new TaskListScope();

        Scope<?> scope = mainScope;

        while (!reader.finished()) {
            while (scope.done())
                scope = scope.getParentScope();

            scope = scope.findScope(reader);

            Object element = scope.getParser().parse(reader);

            @SuppressWarnings("unchecked")
            Consumer<Object> consumer = (Consumer<Object>) scope.getConsumer();

            consumer.accept(element);
        }

        return mainScope.getParser().parse(reader);
    }

}
