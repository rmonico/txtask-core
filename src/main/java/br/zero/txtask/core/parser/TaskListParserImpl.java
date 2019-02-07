package br.zero.txtask.core.parser;

import java.io.IOException;
import java.io.Reader;

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
        TaskListScope scope = new TaskListScope();

        while (!reader.finished()) {
            @SuppressWarnings("unchecked")
            ElementDescription<Object> desc = (ElementDescription<Object>) scope.findParser(reader);

            Object element = desc.parser.parse(reader);

            desc.consumer.accept(element);
        }

        return scope.getTaskList();
    }

}
