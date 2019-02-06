package br.zero.matchers;

import java.util.function.Function;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class FunctionalFeatureMatcher<CONTAINER, FEATURE> extends FeatureMatcher<CONTAINER, FEATURE> {
	private Function<CONTAINER, FEATURE> featureExtractor;

	public FunctionalFeatureMatcher(Matcher<? super FEATURE> subMatcher, String featureDescription, String featureName,
			Function<CONTAINER, FEATURE> featureExtractor) {
		super(subMatcher, featureDescription, featureName);
		assert featureExtractor != null : "featureExtractor is null";
		this.featureExtractor = featureExtractor;
	}

	@Override
	protected FEATURE featureValueOf(CONTAINER actual) {
		return featureExtractor.apply(actual);
	}
}