package mcqueen.noah.stepstone.primitives;

import java.util.ArrayList;
import java.util.List;

public class Goal {
    private final List<Task> activeTasks;
    // --Commented out by Inspection (11/22/2020 9:54 AM):private final List<Task> completedTasks;
    private String description;

    public Goal(){
        activeTasks = new ArrayList<>();
        //completedTasks = new ArrayList<>();
    }

    public List<Task> getActiveTasks() { return activeTasks; }

    public void setDescription(String newDescription) { description = newDescription; }
    public String getDescription() { return description; }

    // --Commented out by Inspection (11/22/2020 9:53 AM):public void addActiveTask(Task addedTask) { activeTasks.add(addedTask); }
// --Commented out by Inspection START (11/22/2020 9:53 AM):
//    public void markTaskCompleted(int position) {
//        completedTasks.add(activeTasks.get(position));
//        activeTasks.remove(position);
//    }
// --Commented out by Inspection STOP (11/22/2020 9:53 AM)
}
