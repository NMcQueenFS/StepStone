package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TaskModifierScreen extends AppCompatActivity {
    int priority, repeatability, taskPos;
    boolean isEditing;
    EditText taskDescription;
    String description;
    Spinner prioritySpinner, repeatSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_modifier_screen);

        taskDescription = findViewById(R.id.taskDescription_field);
        Button acceptChangesButton = findViewById((R.id.task_accept_changes_button));
        Button deleteTaskButton = findViewById(R.id.delete_task_button);

        prioritySpinner = findViewById(R.id.prioritySpinner);
        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { priority = position; }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        repeatSpinner = findViewById(R.id.repeatingSpinner);
        repeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { repeatability = position; }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("TASK_EXISTS")) isEditing = true;
            if (extras.getString("TASK_DESCRIPTION") != null) taskDescription.setText(extras.getString("TASK_DESCRIPTION"));
            prioritySpinner.setSelection(extras.getInt("TASK_PRIORITY"));
            taskPos = extras.getInt("TASK_POS_IN_LIST");
        }

        acceptChangesButton.setText(isEditing ? "Accept Changes" : "Create Task");
        deleteTaskButton.setVisibility(isEditing ? View.VISIBLE : View.INVISIBLE);
    }

    //Called if Add Task/Accept Changes button is pushed
    public void sendData(View view) {
        description = taskDescription.getText().toString();

        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putString("TASK_DESCRIPTION", description);
        extras.putInt("TASK_PRIORITY",priority);
        extras.putInt("TASK_REPEAT",repeatability);
        if(isEditing) extras.putInt("TASK_POS_IN_LIST",taskPos);
        intent.putExtras(extras);

        if (isEditing) setResult(2, intent);
        else setResult(RESULT_OK, intent);
        finish();
    }

    //Called if Remove Task button is pushed
    public void deleteTask(View view) {
        Intent intent = new Intent();
        intent.putExtra("TASK_POS_IN_LIST",taskPos);
        setResult(99, intent);
        finish();
    }
}