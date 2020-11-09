package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoalViewManager extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private static final int TASK_MOD_CODE = 0;
    private GoalViewModel goalModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goalModel = new ViewModelProvider(this).get(GoalViewModel.class);
        goalModel.getActiveGoal().observe(this, new Observer<Goal>() {
            @Override
            public void onChanged(Goal goal) {
                findViewById(R.id.placeholder_doohicky).setVisibility(View.GONE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GoalView goalView = new GoalView();
                ft.add(R.id.goal_slider,goalView);
                ft.commit();
            }
        });
        setContentView(R.layout.goal_slider);

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
        Toast.makeText(this,"Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
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
        Goal newGoal = new Goal();
        goalModel.setActiveGoal(newGoal);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            if (goalModel.getNewestTask().getValue() != null) { goalModel.updateNewestTask(buildNewTask(data)); }
            else {
                Task tempTask = buildNewTask(data);
                goalModel.updateNewestTask(tempTask);
            }

            List<Task> tempList;
            if (goalModel.getActiveTasks().getValue() != null) { tempList = goalModel.getActiveTasks().getValue(); }
            else tempList = new ArrayList<>();
            tempList.add(buildNewTask(data));
            goalModel.setActiveTasks(tempList);
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

        Task returnedTask = new Task(description,dueDate,priority);
        return returnedTask;
    }



}
