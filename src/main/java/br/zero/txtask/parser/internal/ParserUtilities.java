package br.zero.txtask.parser.internal;

import static br.zero.txtask.parser.internal.Constants.SUB_TASK_IDENT;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

public class ParserUtilities {

    static String identString(int identLevel) {
        return range(0, identLevel).mapToObj(i -> SUB_TASK_IDENT).collect(joining());
    }
}
