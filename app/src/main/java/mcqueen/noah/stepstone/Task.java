package mcqueen.noah.stepstone;

import androidx.annotation.NonNull;

import java.util.Date;

public class Task {
    //Variables
    private String description;
    private Date dueDate;
    private int priority;

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

    //Getters
    public String getDescription() { return description; }
    public Date getDueDate() { return dueDate; }
    public int getPriority() { return priority; }

}
