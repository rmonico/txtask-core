package br.zero.txtask.core.parser.reader;

import java.io.IOException;

class ParserReaderConsumer extends ParserReaderGetter {

    private ParserReader reader;

    public ParserReaderConsumer(ParserReader reader) {
        super(reader);
        this.reader = reader;
    }

    @Override
    public String go() throws IOException {
        String result = super.go();

        reader.updateReaderPosition(result.length());

        return result;
    }

}
