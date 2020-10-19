package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private static final int TASK_MOD_CODE = 0;
    private TaskViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define and assemble the recycler view for tasks to be visible
        adapter = new TaskViewAdapter(generateTaskList());
        RecyclerView taskRecycler = findViewById(R.id.taskList);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setAdapter(adapter);

        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.TaskSort(position);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    //Launch the New Task window, then collect information from it to create the new task
    public void launchTaskModifier(View view) {
        Intent intent = new Intent(this, TaskModifierScreen.class);
        startActivityForResult(intent, TASK_MOD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String description = Objects.requireNonNull(extras).getString("TASK_DESCRIPTION");
            int priority = extras.getInt("TASK_PRIORITY");
            int repeat = extras.getInt("TASK_REPEAT");
            extras.get("TASK_DUE_DATE");
            Date dueDate = (Date)extras.get("TASK_DUE_DATE");
            adapter.addTask(new Task(Objects.requireNonNull(description), dueDate, priority, repeat));
            adapter.notifyDataSetChanged();
        }
        else if (resultCode == 2) {
            Bundle extras = data.getExtras();
            int taskPos = extras.getInt("TASK_POS_IN_LIST");
            Task editingTask = adapter.getTaskList().get(taskPos);
            editingTask.setDescription(extras.getString("TASK_DESCRIPTION"));
            editingTask.setPriority(extras.getInt("TASK_PRIORITY"));

            Date d = new Date();
            d.setTime(extras.getLong("TASK_DUE_DATE"));
            editingTask.setDueDate(d);

            adapter.notifyDataSetChanged();
        }
        else if (resultCode == 99) {
            int taskPos = data.getIntExtra("TASK_POS_IN_LIST",-1);
            adapter.getTaskList().remove(taskPos);
            adapter.notifyDataSetChanged();
        }
    }

    //Generates a blank taskList to initialize TaskViewAdapter
    private List<Task> generateTaskList() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            taskList.add(new Task(String.format(Locale.US,"My priority is: %d", i), Calendar.getInstance().getTime(),i, 0));
        }
        return taskList;
    }
}
