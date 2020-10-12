package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{
    private static final int TASKMOD_CODE = 0;

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

        //Recognize elements of the main activity screen
        Button button = findViewById(R.id.addTask_button);
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

    public void launchTaskModifier(View view) {
        Intent intent = new Intent(this, TaskModifierScreen.class);
        startActivityForResult(intent, TASKMOD_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String description = extras.getString("TASK_DESCRIPTION");
            int priority = extras.getInt("TASK_PRIORITY");
            adapter.addTask(new Task(description, null, priority));
            adapter.notifyDataSetChanged();
        }
    }

    //Generates a blank taskList to initialize TaskViewAdapter
    private List<Task> generateTaskList() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            int random = (int)(Math.random()*7);
            taskList.add(new Task(String.format(Locale.US,"My priority is: %d", random),null,random));
        }
        return taskList;
    }
}
