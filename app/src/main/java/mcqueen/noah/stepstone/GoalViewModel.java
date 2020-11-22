package mcqueen.noah.stepstone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import mcqueen.noah.stepstone.primitives.Goal;
import mcqueen.noah.stepstone.primitives.Task;

public class GoalViewModel extends ViewModel {
    private MutableLiveData<List<Goal>> availableGoals;
    private MutableLiveData<Goal> activeGoal;
    private MutableLiveData<Integer> activeGoalIndex;
    private MutableLiveData<List<Task>> activeTasks; //, completedTasks;

    public void addNewGoal(Goal goal){
        List<Goal> tempList;
        if (availableGoals == null) {
            availableGoals = new MutableLiveData<>();
            tempList = new ArrayList<>();
        }
        else tempList = availableGoals.getValue();
        tempList.add(goal);
        availableGoals.setValue(tempList);
    }
    public Integer getGoalCount(){
        if (availableGoals != null) return availableGoals.getValue().size();
        else return -1;
    }

    public LiveData<Goal> getActiveGoal() {
        if (activeGoal == null) activeGoal = new MutableLiveData<>();
            return activeGoal;
    }

    public LiveData<List<Goal>> getAllGoals() {
        if (availableGoals == null) availableGoals = new MutableLiveData<>();
        return availableGoals;
    }

    public void setActiveGoal(Goal goal) { activeGoal.setValue(goal); }
    public void setActiveGoal(int index) { activeGoal.setValue(availableGoals.getValue().get(index));}

    public void setActiveGoalIndex(int value){
        activeGoalIndex.setValue(value);
    }

    public Integer getActiveGoalIndex() {
        if (activeGoalIndex == null) {
            activeGoalIndex = new MutableLiveData<>();
            activeGoalIndex.setValue(0);
        }
        return activeGoalIndex.getValue();
    }

    public LiveData<List<Task>> getActiveTasks() {
        if (activeTasks == null) activeTasks = new MutableLiveData<>();
        activeTasks.setValue(getActiveGoal().getValue().getActiveTasks());
        return activeTasks;
    }

    public void setActiveTasks(List<Task> newList) { activeTasks.setValue(newList); }
}
