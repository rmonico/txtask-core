package br.zero.txtask.parser.matcherfactory;

import static br.zero.matchers.FunctionalFeatureMatcherFactory.feature;

import java.util.function.Function;

import br.zero.matchers.FunctionalFeatureMatcherFactory;
import br.zero.txtask.model.Tag;
import br.zero.txtask.model.TaskContainer;

public class TagMatcherFactory {

	private Function<TaskContainer, Tag> extractor;

    public TagMatcherFactory(Function<TaskContainer, Tag> extractor) {
        this.extractor = extractor;
	}

    public FunctionalFeatureMatcherFactory<TaskContainer, String> name() {
        return tagFeature(Tag::getName).description("Tag name").name("name");
    }

    private <T> FunctionalFeatureMatcherFactory<TaskContainer, T> tagFeature(Function<Tag, T> tagExtractor) {
        return feature((TaskContainer c) -> tagExtractor.apply(extractor.apply(c)));
    }
}
