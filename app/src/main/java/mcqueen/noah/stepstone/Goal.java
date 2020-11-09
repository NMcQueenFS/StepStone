package mcqueen.noah.stepstone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Goal {
    private List<Task> activeTasks,upcomingTasks,completedTasks;
    private Date earliestDate;

    public Goal(){
        activeTasks = new ArrayList<>();
        upcomingTasks = new ArrayList<>();
        completedTasks = new ArrayList<>();
    }

    public List<Task> getActiveTasks() { return activeTasks; }

    public void addActiveTask(Task addedTask) { activeTasks.add(addedTask); }
    public void markTaskCompleted(int position) {
        completedTasks.add(activeTasks.get(position));
        activeTasks.remove(position);
    }
}
