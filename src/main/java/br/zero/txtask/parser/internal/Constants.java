package br.zero.txtask.parser.internal;

import br.zero.java.MapBuilder;
import br.zero.txtask.model.Status;

import java.util.Map;

import static br.zero.java.StringFormatter.s;
import static br.zero.txtask.model.Status.DONE;
import static br.zero.txtask.model.Status.OPEN;

class Constants {

    static final String LIST_TITLE_PREFIX = ":: ";

    static final Map<Status, String> TASK_STATUSES = MapBuilder.create(Status.class, String.class).hashMap().put(OPEN, "- ").put(DONE, "x ").done();

    static final String[] TASK_STATUSES_ARRAY = TASK_STATUSES.values().toArray(new String[]{});

    static final String TAG_MARK = "#";
    static final String TAG_PREFIX = s(" %s").format(TAG_MARK);

}
