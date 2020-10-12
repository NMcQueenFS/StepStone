package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            String description = extras.getString("TASK_DESCRIPTION");
            int priority = extras.getInt("TASK_PRIORITY");
            int repeat = extras.getInt("TASK_REPEAT");
            adapter.addTask(new Task(description, null, priority, repeat));
            adapter.notifyDataSetChanged();
        }
    }

    //Generates a blank taskList to initialize TaskViewAdapter
    private List<Task> generateTaskList() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            taskList.add(new Task(String.format(Locale.US,"My priority is: %d", i),null,i, 0));
        }
        return taskList;
    }
}
