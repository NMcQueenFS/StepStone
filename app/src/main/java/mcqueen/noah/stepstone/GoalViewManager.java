package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoalViewManager extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final int TASK_MOD_CODE = 0;
    private GoalViewModel goalModel;
    private TextView goalDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_slider);
        goalDescription = findViewById(R.id.goal_description_textView);

        goalModel = new ViewModelProvider(this).get(GoalViewModel.class);
        goalModel.getActiveGoal().observe(this, new Observer<Goal>() {
            @Override
            public void onChanged(Goal goal) {
                findViewById(R.id.goal_bar_placeholder).setVisibility(View.GONE);
                goalDescription.setText(goalModel.getActiveGoal().getValue().getDescription());
                refreshScreen();
            }
        });

        FloatingActionButton fab = findViewById(R.id.goalManager_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateNewPopup(view);
            }
        });
    }

    public void showCreateNewPopup(View view) {
        PopupMenu popup = new PopupMenu(GoalViewManager.this, view);
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
        Intent intent = new Intent(this, TaskModifierScreen.class);
        startActivityForResult(intent, TASK_MOD_CODE);
    }

    private void makeNewGoal() {
        Intent intent = new Intent(this, GoalCreationScreen.class);
        startActivityForResult(intent, TASK_MOD_CODE);
    }

    private void refreshScreen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        GoalView goalView = new GoalView();
        ft.replace(R.id.goal_slider,goalView);
        ft.commit();
    }

    public void refreshToNext(View view){
        int tempIndex = goalModel.getActiveGoalIndex();
        if (goalModel.getGoalCount() > ++tempIndex) {
        goalModel.setActiveGoal(tempIndex);
        goalModel.setActiveGoalIndex(tempIndex);
        refreshScreen();
        }
    }

    public void refreshToPrevious(View view){
        int tempIndex = goalModel.getActiveGoalIndex();
        if (0 <= --tempIndex ) {
            goalModel.setActiveGoal(tempIndex);
            goalModel.setActiveGoalIndex(tempIndex);
            refreshScreen();
        }
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
        if (resultCode == 5) {
            Goal newGoal = new Goal();
            newGoal.setDescription(data.getStringExtra("GOAL_DESCRIPTION"));
            goalModel.addNewGoal(newGoal);
            goalModel.setActiveGoal(newGoal);
        }

        //if (resultCode == 0) { goalView.addActiveTask(buildNewTask(data)); }
        //else if (resultCode == 2) { goals.get(0).getCurrentGoalView().modifyTask(data); }
        //else if (resultCode == 99) { goals.get(0).getCurrentGoalView().deleteTask(data); }`
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



}
