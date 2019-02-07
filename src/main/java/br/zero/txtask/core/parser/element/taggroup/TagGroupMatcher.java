package br.zero.txtask.core.parser.element.taggroup;

import static br.zero.txtask.core.parser.element.abstracttag.Constants.IMPLICIT_TAG_INITIAL_PREFIX;

import br.zero.txtask.core.parser.element.abstracttag.AbstractTagMatcher;

public class TagGroupMatcher extends AbstractTagMatcher {

    public TagGroupMatcher() {
        super(IMPLICIT_TAG_INITIAL_PREFIX);
    }

}