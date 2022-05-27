package model;


import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
   // private String description;
    //private List<Task> tasks;
    private List<Todo> tasks;
    
    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
//        if (description == null || description.length() == 0) {
//            throw new EmptyStringException("Cannot construct a project with no description");
//        }
        super(description);
        tasks = new ArrayList<>();
    }
    
    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (!contains(task) && !task.getDescription().equals(this.getDescription())) {
            tasks.add(task);
        }
    }
    
    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }
    
    // EFFECTS: returns the description of this project
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public int getEstimatedTimeToComplete() {
//        return super.etcHours;
        int totalTimeOfTasks = 0;
        for (Todo t : tasks) {
            totalTimeOfTasks += t.getEstimatedTimeToComplete();
        }
        return totalTimeOfTasks;
    }

//    // EFFECTS: returns an unmodifiable list of tasks in this project.
//    public List<Task> getTasks() {
//        return Collections.unmodifiableList(tasks);
//    }

    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

//    // EFFECTS: returns an integer between 0 and 100 which represents
//    //     the percentage of completed tasks (rounded down to the closest integer).
//    //     returns 100 if this project has no tasks!
//    public int getProgress() {
//        int numerator = getNumberOfCompletedTasks();
//        int denominator = getNumberOfTasks();
//        if (numerator == denominator) {
//            return 100;
//        } else {
//            return (int) Math.floor(numerator * 100.0 / denominator);
//        }
//    }

    // EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    public int getProgress() {
//        return 0; // stub
        int totalProgress = 0;
        int numTasks = 0;
        for (Todo t : tasks) {
            numTasks += 1;
            totalProgress += t.getProgress();
        }
        if (totalProgress == 0) {
            return totalProgress;
        } else {
            return totalProgress / numTasks;
        }
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }


    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {
        Iterator projectIterator = tasks.iterator();
        Iterator projectIterator2 = tasks.iterator();
        Iterator projectIterator3 = tasks.iterator();
        Iterator projectIterator4 = tasks.iterator();
        private int listIndex = tasks.size() - 1;
        private int listIndex2 = 0;
        private int taskIndex = 0;
//        Todo nextTodo = null;
        private Todo nextTodo = null;
        private  int counter1 = 0;
        private  int counter2 = 0;
        private  int counter3 = 0;
        private  int counter4 = 0;
        List<Integer> ints = new LinkedList<>();



        @Override
        public boolean hasNext() {
            return (projectIterator.hasNext() || projectIterator2.hasNext()
                    || projectIterator3.hasNext() || projectIterator4.hasNext());
        }

        @Override
        public Todo next() {
            List<Todo> selected = new LinkedList<>();

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (projectIterator.hasNext()) {
                Todo pg = (Todo) projectIterator.next();

                return pg;
            } else if (projectIterator2.hasNext()) {
                Todo pg2 = (Todo) projectIterator2.next();

                return pg2;
            } else if (projectIterator3.hasNext()) {
                Todo pg3 = (Todo) projectIterator3.next();

                return pg3;
            } else {
                Todo pg4 = (Todo) projectIterator4.next();
                return pg4;
            }


        }


    }



    private int priorityToInt(Priority priority) {
        if (priority.isImportant() && priority.isUrgent()) {
            return 1;
        } else if (priority.isImportant() && !priority.isUrgent()) {
            return 2;
        } else if (!priority.isImportant() && priority.isUrgent()) {
            return 3;
        } else {
            return 4;
        }
    }
}