package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private static final int TASK_MOD_CODE = 0;
    private TaskViewAdapter activeTaskAdapter;
    private CompletedTaskAdapter completedTaskAdapter;
    private int sortMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        Button addTaskButton = findViewById(R.id.addTask_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTaskModifier();
            }
        });

        //Define and assemble the recycler view for tasks to be visible
        completedTaskAdapter = new CompletedTaskAdapter();
        RecyclerView completedTaskRecycler = findViewById(R.id.completedList);
        completedTaskRecycler.setLayoutManager(new LinearLayoutManager(this));
        completedTaskRecycler.setHasFixedSize(true);
        completedTaskRecycler.setAdapter(completedTaskAdapter);

        activeTaskAdapter = new TaskViewAdapter(completedTaskAdapter);
        RecyclerView activeTaskRecycler = findViewById(R.id.taskList);
        activeTaskRecycler.setLayoutManager(new LinearLayoutManager(this));
        activeTaskRecycler.setHasFixedSize(true);
        activeTaskRecycler.setAdapter(activeTaskAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskViewAdapter.SwipeToDeleteCallback(activeTaskAdapter));
        itemTouchHelper.attachToRecyclerView(activeTaskRecycler);

        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortMethod = position;
                activeTaskAdapter.TaskSort(sortMethod);
                activeTaskAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    //Launch the New Task window, then collect information from it to create the new task
    public void launchTaskModifier() {
        Intent intent = new Intent(this, TaskModifierScreen.class);
        startActivityForResult(intent, TASK_MOD_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            Bundle extras = data.getExtras();
            String description = extras.getString("TASK_DESCRIPTION");
            int priority = extras.getInt("TASK_PRIORITY");
            extras.get("TASK_DUE_DATE");
            Date dueDate = new Date();
            dueDate.setTime(extras.getLong("TASK_DUE_DATE"));

            activeTaskAdapter.addTask(new Task(Objects.requireNonNull(description), dueDate, priority));
            activeTaskAdapter.notifyDataSetChanged();
        }
        else if (resultCode == 2) {
            Bundle extras = data.getExtras();
            int taskPos = extras.getInt("TASK_POS_IN_LIST");
            Task editingTask = activeTaskAdapter.getTaskList().get(taskPos);
            editingTask.setDescription(extras.getString("TASK_DESCRIPTION"));
            editingTask.setPriority(extras.getInt("TASK_PRIORITY"));

            Date d = new Date();
            d.setTime(extras.getLong("TASK_DUE_DATE"));
            editingTask.setDueDate(d);

            activeTaskAdapter.notifyDataSetChanged();
        }
        else if (resultCode == 99) {
            int taskPos = data.getIntExtra("TASK_POS_IN_LIST",-1);
            activeTaskAdapter.getTaskList().remove(taskPos);
            activeTaskAdapter.notifyDataSetChanged();
        }
        activeTaskAdapter.TaskSort(sortMethod);
    }

}
