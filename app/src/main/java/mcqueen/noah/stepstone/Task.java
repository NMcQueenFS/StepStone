package mcqueen.noah.stepstone;

import androidx.annotation.NonNull;

import java.util.List;

public class Task {
    //Variables
    private String description, dueDate;
    private boolean completed, repeatable;
    private int priority, repeatability;
    private Task parentTask;
    private List<Task> childTasks;

    //Constructors
    public Task(){}
    public Task(@NonNull final String description, final String dueDate, final int priority, final int repeat) {
        setDescription(description);
        setDueDate(dueDate);
        setPriority(priority);
        setRepeatability(repeat);
    }

    //Setters
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setRepeatable(boolean repeatable) { this.repeatable = repeatable; }
    public void setRepeatability(int repeatability) { this.repeatability = repeatability; }
    public void setParentTask(Task parent) { this.parentTask = parent; }
    public void setChildTasks(List<Task> children) { this.childTasks = children; }

    //Getters
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public int getPriority() { return priority; }
    public boolean getCompleted() { return completed; }
    public int getRepeatability() { return  repeatability; }
    public boolean getRepeatable() { return repeatable; }
    public Task getParentTask() { return parentTask; }
    public List<Task> getChildTasks() { return childTasks; }
}
