package br.zero.matchers;

import java.util.function.Function;

import org.hamcrest.Matcher;

public class FunctionalFeatureMatcherFactory<CONTAINER, FEATURE> {

    private Function<CONTAINER, FEATURE> extractor;
    private String featureDescription;
    private String featureName;

    protected FunctionalFeatureMatcherFactory(Function<CONTAINER, FEATURE> extractor) {
        this.extractor = extractor;
    }

    public static <CONTAINER, FEATURE> FunctionalFeatureMatcherFactory<CONTAINER, FEATURE> feature(Function<CONTAINER, FEATURE> extractor) {
        return new FunctionalFeatureMatcherFactory<CONTAINER, FEATURE>(extractor);
    }
    
    public <SUBFEATURE> FunctionalFeatureMatcherFactory<CONTAINER, SUBFEATURE> sub(Function<FEATURE, SUBFEATURE> subFeatureExtractor) {
        return new FunctionalFeatureMatcherFactory<CONTAINER, SUBFEATURE>(f -> subFeatureExtractor.apply(this.extractor.apply(f)));
    }

    public FunctionalFeatureMatcherFactory<CONTAINER, FEATURE> description(String description) {
        this.featureDescription = description;
        return this;
    }

    public FunctionalFeatureMatcherFactory<CONTAINER, FEATURE> name(String featureName) {
        this.featureName = featureName;
        return this;
    }

    public Matcher<? super CONTAINER> should(Matcher<? super FEATURE> subMatcher) {
        return new FunctionalFeatureMatcher<CONTAINER, FEATURE>(subMatcher, this.featureDescription, this.featureName, this.extractor);
    }

}
