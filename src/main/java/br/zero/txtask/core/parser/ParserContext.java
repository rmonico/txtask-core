package br.zero.txtask.core.parser;

import static br.zero.txtask.core.parser.ParsingStep.TASK_TITLE;

import java.util.ArrayList;
import java.util.List;

import br.zero.txtask.core.model.Tag;

public class ParserContext {

	private List<Tag> implicitTags;
	private ParsingStep step;

	public ParserContext() {
		this.implicitTags = new ArrayList<>();
		this.step = TASK_TITLE;
	}

	public List<Tag> implicitTags() {
		return implicitTags;
	}

	public ParsingStep step() {
		return this.step;
	}

}
