package br.zero.txtask.parser.scope.linebreak;

import java.io.IOException;

import br.zero.txtask.parser.scope.SingleScope;
import br.zero.txtask.parser.reader.ParserReader;

public class LineBreakScope extends SingleScope<String> {

    public LineBreakScope() {
        super(new LineBreakParser());
    }

    @Override
    protected boolean matches(ParserReader reader) throws IOException {
        return reader.followed().by("\n").go();
    }

}
