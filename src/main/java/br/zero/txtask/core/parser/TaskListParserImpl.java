package br.zero.txtask.core.parser;

import java.io.IOException;
import java.io.Reader;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.element.ElementParserAbstractFactory;
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
        TaskList taskList = new TaskList();

        ParserContext context = new ParserContext();

        ElementParserAbstractFactory parserFactory = new ElementParserAbstractFactory();

        while (!reader.finished()) {
            ElementParser<?> parser = parserFactory.getNextParser(reader, context);

            Object element = parser.parse(reader);

            parser.put(taskList, context, element);
        }

//        while (!reader.finished()) {
//            } else if (reader.followed().by(IMPLICIT_TAG_INITIAL_PREFIX).go()) {
//                parseTags(reader, tag -> context.implicitTags().add(tag), IMPLICIT_TAG_INITIAL_PREFIX, IMPLICIT_TAG_PREFIX);
//            } else if (reader.followed().by(IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX).go()) {
//                parseTags(reader, tag -> context.implicitTags().remove(tag), IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX, IMPLICIT_TAG_REMOVAL_PREFIX);
//            } else if (reader.followed().byEol().go()) {
//                reader.consume().next(1).go();
//            } else {
//                String nextToken = reader.consume().until(" ").or().eol().go();
//
//                throw new ParserException(s("Invalid token: '%s'").format(nextToken));
//            }
//        }

        return taskList;
    }

}
