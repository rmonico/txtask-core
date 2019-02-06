package br.zero.txtask.core.parser.element.task;

import static br.zero.java.MapBuilder.create;
import static br.zero.txtask.core.model.Status.DONE;
import static br.zero.txtask.core.model.Status.OPEN;

import java.util.Map;

import br.zero.txtask.core.model.Status;

class Constants {

    static final Map<Status, String> TASK_STATUSES = create(Status.class, String.class).hashMap().put(OPEN, "- ").put(DONE, "x ").done();

    static final String[] TASK_STATUSES_ARRAY = TASK_STATUSES.values().toArray(new String[] {});

}
