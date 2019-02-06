package br.zero.txtask.core.parser.element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;
import br.zero.txtask.core.parser.element.blankline.BlankLineMatcher;
import br.zero.txtask.core.parser.element.garbage.GarbageMatcher;
import br.zero.txtask.core.parser.element.listtitle.ListTitleMatcher;
import br.zero.txtask.core.parser.element.taggroup.TagGroupMatcher;
import br.zero.txtask.core.parser.element.task.TaskMatcher;
import br.zero.txtask.core.parser.reader.ParserReader;

public class ElementParserAbstractFactory {

    private List<ElementMatcher<?>> matchers = new ArrayList<>();

    public ElementParserAbstractFactory() {
        // TODO Inject these classes through annotation

        matchers.add(new ListTitleMatcher());
        matchers.add(new TaskMatcher());
        matchers.add(new TagGroupMatcher());
        matchers.add(new BlankLineMatcher());
        matchers.add(new GarbageMatcher());
    }

    public ElementParser<?> getNextParser(ParserReader reader,
            ParserContext context) throws IOException, ParserException {
        for (ElementMatcher<?> matcher : matchers) {
            if (matcher.matchs(reader, context))
                return matcher.getParser();
        }

        assert false : "No matchers found";
        return null;
    }

}
