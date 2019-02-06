package br.zero.txtask.core.parser.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class ParserReader {

    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private Reader source;
    private CharBuffer buffer;
    private int position;

    public ParserReader(Reader source) {
        this.source = new BufferedReader(source);
        this.position = 0;
    }

    public int position() {
        return this.position;
    }

    public ParserReaderMatcher followed() {
        return new ParserReaderMatcher(this);
    }

    public ParserReaderGetter get() {
        return new ParserReaderGetter(this);
    }

    public ParserReaderGetter consume() {
        return new ParserReaderConsumer(this);
    }

    public boolean finished() throws IOException {
        return get(1).length() == 0;
    }

    StringBuilder get(int charCount) throws IOException {
        // TODO Refactor
        if (this.buffer == null || this.buffer.remaining() < charCount) {
            CharBuffer oldBuffer = this.buffer;

            int bufferSize = charCount > DEFAULT_BUFFER_SIZE ? charCount : DEFAULT_BUFFER_SIZE;

            this.buffer = CharBuffer.allocate(bufferSize);

            if (oldBuffer != null && oldBuffer.hasRemaining())
                this.buffer.put(oldBuffer);

            CharBuffer tempBuffer = CharBuffer.allocate(this.buffer.remaining());

            source.read(tempBuffer);

            tempBuffer.flip();

            this.buffer.append(tempBuffer);

            this.buffer.flip();
        }

        int realCharCount = this.buffer.remaining() < charCount ? this.buffer.remaining() : charCount;

        return new StringBuilder(this.buffer.subSequence(0, realCharCount));
    }

    void updateReaderPosition(int length) {
        this.buffer.position(this.buffer.position() + length);

        this.position += length;
    }

}
