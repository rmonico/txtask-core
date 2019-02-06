package br.zero.txtask.core.parser.element.taggroup;

import static br.zero.txtask.core.parser.element.abstracttag.Constants.IMPLICIT_TAG_INITIAL_PREFIX;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.parser.element.ElementParser;
import br.zero.txtask.core.parser.element.abstracttag.AbstractTagMatcher;

public class TagGroupScope extends AbstractTagMatcher {

    public TagGroupScope() {
        super(IMPLICIT_TAG_INITIAL_PREFIX);
    }

    @Override
    protected ElementParser<Tag> makeParser() {
        return new TagGroupParser();
    }

}
