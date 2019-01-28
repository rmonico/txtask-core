package br.zero.txtask.core.parser;

import java.io.IOException;
import java.io.Reader;

import br.zero.txtask.core.model.Tag;
import br.zero.txtask.core.model.Task;
import br.zero.txtask.core.model.TaskList;

public class TaskListParser implements Parser<TaskList> {

    private static final String TASK_LIST_TITLE_PREFIX_REGEX = "^:: .+$";
    private static final String TASK_STATUS_REGEX = "[-x]";
    private static final String TASK_PREFIX_REGEX = format("%s ", TASK_STATUS_REGEX);
    private static final String TASK_NAME_REGEX = "(?<tagname>[a-z_][a-z0-9_\\.]*)";
    private static final String TAG_REGEX = format(" #%s", TASK_NAME_REGEX);
    private static final String TASK_TITLE_REGEX = format("^%s(?<tasktitle>[^#]+)(%s)*$", TASK_PREFIX_REGEX, TAG_REGEX);

    private static final Pattern TAG_PATTERN = Pattern.compile(TAG_REGEX);
    private static final Pattern TASK_TITLE_PATTERN = Pattern.compile(TASK_TITLE_REGEX);

    @Override
    public TaskList parse(Reader source) throws ParserException {
        BufferedReader reader = new BufferedReader(source);

        TaskList taskList = new TaskList();

        try {
            String line = reader.readLine();

            if (line == null)
                throw new ParserException("Reader is empty");

            while (line != null) {
                doParse(taskList, line);

                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new ParserException(e);
        }

        return taskList;
    }

    private void doParse(TaskList taskList, String line) {
        if (line.matches(TASK_LIST_TITLE_PREFIX_REGEX))
            taskList.setTitle(line.substring(3));
        else if (line.matches("^" + TASK_PREFIX_REGEX + ".+$")) {
            Task task = new Task();

            Matcher match = TAG_PATTERN.matcher(line);

            while (match.find()) {
                String tagName = match.group("tagname");

                Tag tag = new Tag();
                tag.setName(tagName);

                task.getTags().add(tag);
            }

            match = TASK_TITLE_PATTERN.matcher(line);

            // TODO Check if == 0
            match.find();

            task.setTitle(match.group("tasktitle"));

            taskList.getTasks().add(task);
        }
    }
}
