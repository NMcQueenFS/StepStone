package mcqueen.noah.stepstone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class GoalViewModel extends ViewModel {
    private MutableLiveData<List<Goal>> availableGoals;
    private MutableLiveData<Goal> activeGoal;
    private MutableLiveData<List<Task>> activeTasks, completedTasks;
    private MutableLiveData<Task> newestTask;

    public LiveData<Goal> getActiveGoal() {
        if (activeGoal == null) activeGoal = new MutableLiveData<>();
            return activeGoal;
    }
    public void setActiveGoal(Goal goal) { activeGoal.setValue(goal); }

    public LiveData<List<Task>> getActiveTasks() {
        if (activeTasks == null) activeTasks = new MutableLiveData<List<Task>>();
        return activeTasks;
    }
    public LiveData<Task> getNewestTask() {
        if (newestTask == null) newestTask = new MutableLiveData<Task>();
        return newestTask;
    }

    public void setActiveTasks(List<Task> newList) { activeTasks.setValue(newList); }
    public void updateNewestTask(Task task) { newestTask.setValue(task); }
}
