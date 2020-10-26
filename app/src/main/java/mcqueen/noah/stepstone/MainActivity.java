package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
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
    private int sortMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        //Define and assemble the recycler view for tasks to be visible
        //TODO: set this back to being null when done with demo
        adapter = new TaskViewAdapter(generateQuickList());
        RecyclerView taskRecycler = findViewById(R.id.taskList);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        taskRecycler.setHasFixedSize(true);
        taskRecycler.setAdapter(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskViewAdapter.SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(taskRecycler);

        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortMethod = position;
                adapter.TaskSort(sortMethod);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private List<Task> generateQuickList(){
        List<Task> dump= new ArrayList<>();
        Date dumpdate = Calendar.getInstance().getTime();
        dump.add(new Task("Come up with name for new pet", dumpdate, 1, 0));
        dump.add(new Task("Get stamina up for 24",dumpdate, 5,0));
        dump.add(new Task("Research new perks",dumpdate, 5,0));
        dump.add(new Task("Clear level 8 dungeon",dumpdate, 3,0));
        dump.add(new Task("Get to level 15",dumpdate, 3,0));
        dump.add(new Task("Finish all quests in area",dumpdate, 4,0));
        dump.add(new Task("Move on to new leveling zone",dumpdate, 4,0));
        dump.add(new Task("Make a caster alt?",dumpdate, 0,0));
        return dump;
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
            Date dueDate = new Date();
            dueDate.setTime(extras.getLong("TASK_DUE_DATE"));

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
        adapter.TaskSort(sortMethod);
    }
}
