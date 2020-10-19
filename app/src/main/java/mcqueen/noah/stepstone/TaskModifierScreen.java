package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class TaskModifierScreen extends AppCompatActivity {
    int priority, repeatability, taskPos;
    boolean isEditing;
    EditText taskDescription;
    Date taskDueDate;
    TextView taskDueDateDisplay;
    String description;
    Spinner prioritySpinner, repeatSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_modifier_screen);

        taskDescription = findViewById(R.id.taskDescription_field);
        taskDueDateDisplay = findViewById((R.id.dueDate_display));
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

    //Called when the due date EditText is pushed
    public void selectDate(View view){
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year,monthOfYear,dayOfMonth);
                taskDueDate = calendar.getTime();
                taskDueDateDisplay.setText(taskDueDate.toString());
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    //Called if Add Task/Accept Changes button is pushed
    public void sendData(View view) {
        description = taskDescription.getText().toString();

        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putString("TASK_DESCRIPTION", description);
        extras.putInt("TASK_PRIORITY",priority);
        extras.putInt("TASK_REPEAT",repeatability);
        extras.putLong("TASK_DUE_DATE", taskDueDate.getTime());
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