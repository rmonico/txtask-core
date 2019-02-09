package br.zero.txtask.parser.scope.task;

import static br.zero.java.MapBuilder.create;
import static br.zero.txtask.model.Status.DONE;
import static br.zero.txtask.model.Status.OPEN;

import java.util.Map;

import br.zero.txtask.model.Status;

public class Constants {

    public static final Map<Status, String> TASK_STATUSES = create(Status.class, String.class).hashMap().put(OPEN, "- ").put(DONE, "x ").done();

    public static final String[] TASK_STATUSES_ARRAY = TASK_STATUSES.values().toArray(new String[] {});

}
