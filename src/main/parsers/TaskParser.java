package parsers;

import model.*;
import model.exceptions.NullArgumentException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

// Represents Task parser
public class TaskParser {
    private List<Task> tasks = new ArrayList<>();
    private Calendar calendar = GregorianCalendar.getInstance();
    private DueDate dueDate = new DueDate();
    private Priority priority;
    private Status status;

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray

    public List<Task> parse(String input) {
        JSONArray tasksArrayHelper = new JSONArray(input);

        for (int i = 0; i < tasksArrayHelper.length(); i++) {
            JSONObject ban = tasksArrayHelper.getJSONObject(i);
            Set<String> stSet = new HashSet<>();
            addFieldsToStSet(stSet);

            try {
                if (ban.keySet().containsAll(stSet)) {
                    dealWithFields(ban);
                }

            } catch (JSONException e) {
                //Don't add task, do nothing
            } catch (NullArgumentException e) {
                //Don't add task
            } catch (NullPointerException e) {
                //do nothing
            }
        }
        return tasks;
    }

    private void addFieldsToStSet(Set<String> stSet) {
        stSet.add("description");
        stSet.add("due-date");
        stSet.add("priority");
        stSet.add("tags");
        stSet.add("status");
    }

    private void dealWithFields(JSONObject ban) {
        if (ban.get("description") instanceof String) {
            Task task = new Task(ban.getString("description"));

            dealWithIncorrectMissingStatus(ban, task);
            dealWithIncorrectMissingPriority(ban, task);
            dealWithIncorrectMissingDueDate(ban, task);
            dealWithIncorrectMissingTags(ban, task);

            if (dealWithIncorrectMissingDueDate(ban,task) && dealWithIncorrectMissingStatus(ban,task)
                    && dealWithIncorrectMissingPriority(ban,task) && dealWithIncorrectMissingTags(ban,task)) {
                noDuplicatesInList(task);
            }
        }
    }

    private boolean parseStatusHelper(Object s,Task task) {
        try {
            if (conditionsForStatus(s)) {
                task.setStatus(status);
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        } catch (NullArgumentException e) {
            return false;
        }
    }

    private Boolean conditionsForStatus(Object s) {
        if (s.toString().equals("TODO")) {
            status = Status.TODO;
            return true;
        } else if (s.toString().equals("IN_PROGRESS")) {
            status = Status.IN_PROGRESS;
            return true;
        } else if (s.toString().equals("UP_NEXT")) {
            status = Status.UP_NEXT;
            return true;
        } else if (s.toString().equals("DONE")) {
            status = Status.DONE;
            return true;
        } else {
            return false;
        }
    }


    private Boolean parsePriorityHelper(JSONObject p, Task task) {
        try {
            if (priorityContains(p)) {
                return prioritySetters(p,task);
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        } catch (NullArgumentException e) {
            return false;
        }
    }

    private Boolean prioritySetters(JSONObject p, Task task) {
        if (p.get("important") instanceof Boolean && p.get("urgent") instanceof Boolean) {
            if (p.getBoolean("important") && p.getBoolean("urgent")) {
                priority = new Priority(1);
            } else if (p.getBoolean("important") && !p.getBoolean("urgent")) {
                priority = new Priority(2);
            } else if (!p.getBoolean("important") && p.getBoolean("urgent")) {
                priority = new Priority(3);
            } else if (!p.getBoolean("important") && !p.getBoolean("urgent")) {
                priority = new Priority();
            }
            task.setPriority(priority);
            return true;
        } else {
            return false;
        }
    }

    private boolean priorityContains(JSONObject p) {
        Set<String> allQualities = new HashSet<>();
        allQualities.add("important");
        allQualities.add("urgent");
        return (p.keySet().containsAll(allQualities));
    }

    private boolean parseDueDateHelper(JSONObject d,Task task) {
        try {
            if (dueDateContains(d)) {
                return dueDateSetters(d,task);
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        } catch (NullArgumentException e) {
            return false;
        }

    }

    private boolean dueDateContains(JSONObject d) {
        Set<String> allQualities = new HashSet<>();
        allQualities.add("month");
        allQualities.add("hour");
        allQualities.add("year");
        allQualities.add("day");
        allQualities.add("minute");
        return (d.keySet().containsAll(allQualities));
    }

    private Boolean dueDateSetters(JSONObject d, Task task) {
        if (d.get("month") instanceof Integer && d.get("hour") instanceof Integer
                && d.get("year") instanceof Integer && d.get("day") instanceof Integer
                && d.get("minute") instanceof Integer) {
            calendar.set(Calendar.MONTH, d.getInt("month"));
            calendar.set(Calendar.HOUR_OF_DAY, d.getInt("hour"));
            calendar.set(Calendar.YEAR, d.getInt("year"));
            calendar.set(Calendar.DAY_OF_MONTH, d.getInt("day"));
            calendar.set(Calendar.MINUTE, d.getInt("minute"));
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dueDate.setDueDate(calendar.getTime());
            task.setDueDate(dueDate);
            return true;
        } else {
            return false;
        }
    }

    private void noDuplicatesInList(Task task) {
        if (!tasks.contains(task)) {
            tasks.add(task);
        }
    }

    private Boolean dealWithIncorrectMissingStatus(JSONObject taskJson, Task task) {
        try {
            if (!taskJson.isNull("status")) {
                Object lj = taskJson.get("status");
                if (lj instanceof String) {
                    return parseStatusHelper(taskJson.get("status"),task);
                } else {
                    return false;
                }
            }
            return false;
        } catch (JSONException e) {
            return false;
        } catch (NullArgumentException e) {
            return false;
        }
    }

    private boolean dealWithIncorrectMissingPriority(JSONObject taskJson, Task task) {
        try {
            if (!taskJson.isNull("priority")) {
                Object lj = taskJson.get("priority");
                if (lj instanceof JSONObject) {
                    return parsePriorityHelper((JSONObject) taskJson.get("priority"),task);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (JSONException e) {
            return false;
        } catch (NullArgumentException e) {
            return false;
        }
    }

    private boolean dealWithIncorrectMissingDueDate(JSONObject taskJson, Task task) {
        try {
            if (!taskJson.isNull("due-date")) {
                Object lj = taskJson.get("due-date");
                if (lj instanceof JSONObject) {
                    return parseDueDateHelper((JSONObject) taskJson.get("due-date"),task);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (JSONException e) {
            return false;
        } catch (NullArgumentException e) {
            return false;
        }

    }

    private boolean dealWithIncorrectMissingTags(JSONObject taskJson, Task task) {
        if (!taskJson.isNull("tags")) {
            try {
                return tagLoop(taskJson,task);

            } catch (JSONException e) {
                return false;
            } catch (NullArgumentException e) {
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean tagLoop(JSONObject taskJson, Task task) {
        for (Object tg: taskJson.getJSONArray("tags")) {
            try {
                JSONObject tag = (JSONObject) tg;
                if (!(tag.get("name") instanceof String)) {
                    return false;
                }
                task.addTag(tag.getString("name"));
            } catch (JSONException e) {
                return false;
            }
        }
        return true;
    }
}
