package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();

        tagJson.put("name", tag.getName());

        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();

        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());

        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();

        try {
            Calendar c = GregorianCalendar.getInstance();
            c.setTime(dueDate.getDate());

            dueDateJson.put("year", c.get(Calendar.YEAR));
            dueDateJson.put("month", c.get(Calendar.MONTH));
            dueDateJson.put("day", c.get(Calendar.DAY_OF_MONTH));
            dueDateJson.put("hour", c.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute", c.get(Calendar.MINUTE));
        } catch (NullPointerException e) {
            //return null;
            dueDateJson.put("due-date",JSONObject.NULL);
        }

        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        JSONArray tags = new JSONArray();
        for (Tag t: task.getTags()) {
            tags.put(tagToJson(t));
        }

        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tags);
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", addUnderscoreToStatus(task));
        if (task.getDueDate() == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        }

        return taskJson;
    }

    private static String addUnderscoreToStatus(Task task) {
        if (task.getStatus().toString().equals("UP NEXT")) {
            return "UP_NEXT";
        } else if (task.getStatus().toString().equals("IN PROGRESS")) {
            return "IN_PROGRESS";
        } else {
            return task.getStatus().toString();
        }
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray taskArray = new JSONArray();

        for (Task t: tasks) {
            taskArray.put(taskToJson(t));
        }

        return taskArray;
    }

}
