package br.zero.txtask.parser.internal;

import br.zero.txtask.model.Tag;
import br.zero.txtask.parser.ParserException;
import br.zero.txtask.parser.reader.ParserReader;

import java.io.IOException;
import java.util.function.Consumer;

import static br.zero.txtask.parser.internal.Constants.TAG_VALUE_SEPARATOR;

class TagsParser {

    private static TagsParser instance = new TagsParser();
    private static final String TAG_NAME_VALID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz_.";
    private static final String TAG_NAME_VALID_FIRST_CHAR = "abcdefghijklmnopqrstuvwxyz_.";
    private static final String TAG_VALUE_VALID_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/_.,";

    static TagsParser tagsParser() {
        return instance;
    }

    void parse(ParserReader reader, Consumer<Tag> consumer, String prefix) throws IOException, ParserException {
        while (reader.followed().by(prefix).which() > -1) {
            reader.consume().next(prefix.length()).go();

            Tag tag = new Tag();

            String tagName = reader.consume().until(" ").or().until(TAG_VALUE_SEPARATOR).eol().go();

            validateTagName(tagName);

            if (reader.followed().by(TAG_VALUE_SEPARATOR).go()) {
                reader.consume().next(TAG_VALUE_SEPARATOR.length()).go();

                String tagValue = reader.consume().until(" ").or().eol().go();

                validateTagValue(tagValue);

                tag.setValue(tagValue);
            }

            reader.consume().until(prefix).or().eol().go();

            tag.setName(tagName);

            consumer.accept(tag);
        }

    }

    private void validateTagValue(String tagValue) throws ParserException {
        for (int ch : tagValue.toCharArray())
            this.validateTagValueChar(ch);
    }

    private void validateTagValueChar(int ch) throws ParserException {
        if (TAG_VALUE_VALID_CHARS.indexOf(ch) == -1)
            throw new ParserException("Tag value can contain only a-z, A-Z, 0-9 '/_.,' chars");
    }

    private void validateTagName(String tagName) throws ParserException {
        char[] charArray = tagName.toCharArray();

        for (int ch : charArray)
            this.validateTagNameChar(ch);

        validateTagNameFirstChar(charArray[0]);
    }

    private void validateTagNameChar(int ch) throws ParserException {
        if (Character.isUpperCase(ch))
            throw new ParserException("Tags cant have uppercase letters");

        if (TAG_NAME_VALID_CHARS.indexOf(ch) == -1)
            throw new ParserException("Tags can have only a-z, 0-9 or _ characters");
    }

    private void validateTagNameFirstChar(char ch) throws ParserException {
        if (TAG_NAME_VALID_FIRST_CHAR.indexOf(ch) == -1)
            throw new ParserException("Tags must start with a-z, or _ characters");
    }

}
