package mcqueen.noah.stepstone;

import androidx.annotation.NonNull;

public class Task {
    //Variables
    private String description, dueDate;
    private boolean completed, repeatable;
    private int priority;
    enum priority {VERY_LOW,LOW,MEDIUM,HIGH,CRITICAL,OVERDUE}

    //Constructors
    public Task(){}
    public Task(@NonNull final String description, final String dueDate) {
        setDescription(description);
        setDueDate(dueDate);
    }

    //Setters
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public void setPriority(int priority) { this.priority = priority; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setRepeatable(boolean repeatable) { this.repeatable = repeatable; }

    //Getters
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
    public int getPriority() { return priority; }
    public boolean getCompleted() { return completed; }
    public boolean getRepeatable() { return repeatable; }
}
