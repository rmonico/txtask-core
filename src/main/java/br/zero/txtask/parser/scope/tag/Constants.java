package br.zero.txtask.parser.scope.tag;

import static br.zero.java.StringFormatter.s;

public class Constants {

    static final String TAG_MARK = "#";
    public static final String TAG_PREFIX = s(" %s").format(TAG_MARK);

    public static final String IMPLICIT_TAG_INSERTION_MARK = s("#%s").format(TAG_MARK);
    public static final String IMPLICIT_TAG_INSERTION_PREFIX = s(" %s").format(IMPLICIT_TAG_INSERTION_MARK);

    public static final String IMPLICIT_TAG_REMOVAL_MARK = "==";
    public static final String IMPLICIT_TAG_REMOVAL_PREFIX = s(" %s").format(IMPLICIT_TAG_REMOVAL_MARK);

}
