package mcqueen.noah.stepstone;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mcqueen.noah.stepstone.primitives.Goal;
import mcqueen.noah.stepstone.primitives.Task;

public class GoalViewManager extends Fragment implements PopupMenu.OnMenuItemClickListener {
    private static final int TASK_MOD_CODE = 0;
    private GoalViewModel goalModel;
    private TextView goalDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.goal_slider,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        goalDescription = view.findViewById(R.id.goal_description_textView);
        goalDescription.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeLeft() {
                refreshToNext();
            }
            public void onSwipeRight() {
                refreshToPrevious();
            }
        });

        final TextView placeholder = view.findViewById(R.id.goal_bar_placeholder);

        goalModel = new ViewModelProvider(requireActivity()).get(GoalViewModel.class);
        goalModel.getActiveGoal().observe(getViewLifecycleOwner(), new Observer<Goal>() {
            @Override
            public void onChanged(Goal goal) {
                if (goalModel.getGoalCount() > 0) placeholder.setVisibility(View.GONE);
                goalDescription.setText(goalModel.getActiveGoal().getValue().getDescription());
                refreshScreen();
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.goalManager_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateNewPopup(view);
            }
        });
    }

    public void showCreateNewPopup(View view) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.setOnMenuItemClickListener(GoalViewManager.this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.createnew_menu,popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //Toast.makeText(this,"Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.createNew_goal:
                makeNewGoal();
                return true;
            case R.id.createNew_task:
                launchTaskModifier();
                return true;
            default:
                return false;
        }
    }

    private void launchTaskModifier() {
        Intent intent = new Intent(getContext(), TaskModifierScreen.class);
        startActivityForResult(intent, TASK_MOD_CODE);
    }

    private void makeNewGoal() {
        Intent intent = new Intent(getContext(), GoalCreationScreen.class);
        startActivityForResult(intent, TASK_MOD_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            if (goalModel.getActiveTasks().getValue() == null) goalModel.setActiveTasks(new ArrayList<Task>());
            List<Task> tempList = goalModel.getActiveTasks().getValue();
            tempList.add(buildNewTask(data));
            goalModel.setActiveTasks(tempList);
        }
        //else if (resultCode == 2) { goals.get(0).getCurrentGoalView().modifyTask(data); }

        else if (resultCode == 5) {
            Goal newGoal = new Goal();
            newGoal.setDescription(data.getStringExtra("GOAL_DESCRIPTION"));
            goalModel.addNewGoal(newGoal);
            goalModel.setActiveGoal(newGoal);
        }
    }

    public Task buildNewTask(Intent data) {
        Bundle extras = data.getExtras();
        String description = extras.getString("TASK_DESCRIPTION");
        int priority = extras.getInt("TASK_PRIORITY");
        extras.get("TASK_DUE_DATE");
        Date dueDate = new Date();
        dueDate.setTime(extras.getLong("TASK_DUE_DATE"));

        return new Task(description,dueDate,priority);
    }


    private void refreshScreen() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        GoalView goalView = new GoalView();
        ft.replace(R.id.goal_slider,goalView);
        ft.commit();
    }

    private void refreshToNext() {
        int tempIndex = goalModel.getActiveGoalIndex();
        if (goalModel.getGoalCount() > ++tempIndex) {
            goalModel.setActiveGoal(tempIndex);
            goalModel.setActiveGoalIndex(tempIndex);
            refreshScreen();
        }
    }

    public void refreshToPrevious() {
        int tempIndex = goalModel.getActiveGoalIndex();
        if (0 <= --tempIndex) {
            goalModel.setActiveGoal(tempIndex);
            goalModel.setActiveGoalIndex(tempIndex);
            refreshScreen();
        }
    }
}
