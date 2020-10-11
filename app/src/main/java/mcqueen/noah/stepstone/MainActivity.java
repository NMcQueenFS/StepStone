package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements taskCard_creator.CreatorDialogListener{

    private TaskViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define and assemble the recycler view for tasks to be visible
        adapter = new TaskViewAdapter(generateTaskList());
        RecyclerView taskRecycler = (RecyclerView)findViewById(R.id.taskList);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setAdapter(adapter);


        Button button = (Button)findViewById(R.id.addTask_button);
        Spinner sortSpinner = (Spinner)findViewById(R.id.sortSpinner);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new taskCard_creator();
                newFragment.show(getSupportFragmentManager(), "taskCreator");
            }
        });

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

    @Override
    public void onDialogPositiveClick(String description) {
        adapter.addTask(new Task(description, null));
        adapter.notifyDataSetChanged();
    }

    //Generates a blank taskList to initialize TaskViewAdapter
    private List<Task> generateTaskList() {
        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < 99; i++) {
            int random = (int)(Math.random()*(7-0)+0);
            taskList.add(new Task(String.format("My priority is: %d", random),null));
            taskList.get(i).setPriority(random);
        }
        return taskList;
    }
}
