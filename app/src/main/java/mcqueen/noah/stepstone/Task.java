package mcqueen.noah.stepstone;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class Task {
    //Variables
    private String description;
    private Date dueDate;
    private int priority;
    private Task parentTask;
    private List<Task> childTasks;

    //Constructors
    public Task(@NonNull final String description, final Date dueDate, final int priority) {
        setDescription(description);
        setDueDate(dueDate);
        setPriority(priority);
    }

    //Setters
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setParentTask(Task parent) { this.parentTask = parent; }
    public void setChildTasks(List<Task> children) { this.childTasks = children; }

    //Getters
    public String getDescription() { return description; }
    public Date getDueDate() { return dueDate; }
    public int getPriority() { return priority; }
    public Task getParentTask() { return parentTask; }
    public List<Task> getChildTasks() { return childTasks; }

    public void AddChild(Task child){ this.childTasks.add(child); }
}
