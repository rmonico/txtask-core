package br.zero.txtask.core.parser.element.abstracttag;

import static br.zero.java.StringFormatter.s;

public class Constants {

    static final String TAG_MARK = "#";
    public static final String TAG_PREFIX = s(" %s").format(TAG_MARK);

    static final String IMPLICIT_TAG_MARK = s("#%s").format(TAG_MARK);
    static final String IMPLICIT_TAG_PREFIX = s(" %s").format(IMPLICIT_TAG_MARK);
    public static final String IMPLICIT_TAG_INITIAL_PREFIX = s("\n%s").format(IMPLICIT_TAG_MARK);

    static final String IMPLICIT_TAG_REMOVAL_MARK = "==";
    static final String IMPLICIT_TAG_REMOVAL_PREFIX = s(" %s").format(IMPLICIT_TAG_REMOVAL_MARK);
    static final String IMPLICIT_TAG_REMOVAL_INITIAL_PREFIX = s("\n%s").format(IMPLICIT_TAG_REMOVAL_MARK);

}
