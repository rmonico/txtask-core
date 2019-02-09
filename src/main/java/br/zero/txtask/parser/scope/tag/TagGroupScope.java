package br.zero.txtask.parser.scope.tag;

import br.zero.txtask.model.Tag;
import br.zero.txtask.parser.scope.SingleScope;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.List;

public class TagGroupScope extends SingleScope<List<Tag>> {

    private String initialTagPrefix;

    public TagGroupScope(String... prefixes) {
        super(new TagGroupParser(prefixes));
        assert prefixes.length > 0 : "At least one prefix must be passed";

        this.initialTagPrefix = prefixes[0];
    }

    @Override
    protected boolean matches(ParserReader reader) throws IOException {
        return reader.followed().by(this.initialTagPrefix).go();
    }

}
