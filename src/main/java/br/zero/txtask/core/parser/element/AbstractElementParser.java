package br.zero.txtask.core.parser.element;

import br.zero.txtask.core.model.TaskList;
import br.zero.txtask.core.parser.ParserContext;
import br.zero.txtask.core.parser.ParserException;

public abstract class AbstractElementParser<ELEMENTTYPE> implements ElementParser<ELEMENTTYPE> {

    @SuppressWarnings("unchecked")
    @Override
    public void put(TaskList taskList,
            ParserContext context,
            Object element) throws ParserException {
        this.internalPut(taskList, context, (ELEMENTTYPE) element);
    }

    protected abstract void internalPut(TaskList taskList,
            ParserContext context,
            ELEMENTTYPE element);

}
