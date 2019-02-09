package br.zero.txtask.parser.scope.garbage;

import br.zero.txtask.parser.scope.SingleScope;
import br.zero.txtask.parser.reader.ParserReader;

public class GarbageScope extends SingleScope<String> {

    public GarbageScope() {
        super(new GarbageParser());
    }

    @Override
    protected boolean matches(ParserReader reader) {
        return true;
    }
}
