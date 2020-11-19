package mcqueen.noah.stepstone.primitives;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Goal {
    private final List<Task> activeTasks;
    private final List<Task> completedTasks;
    private String description;
    private Date earliestDate;

    public Goal(){
        activeTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
    }

    public List<Task> getActiveTasks() { return activeTasks; }

    public void setDescription(String newDescription) { description = newDescription; }
    public String getDescription() { return description; }

    public void addActiveTask(Task addedTask) { activeTasks.add(addedTask); }
    public void markTaskCompleted(int position) {
        completedTasks.add(activeTasks.get(position));
        activeTasks.remove(position);
    }
}
