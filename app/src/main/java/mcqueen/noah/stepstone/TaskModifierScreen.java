package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class TaskModifierScreen extends AppCompatActivity {
    public static final String INTENT_KEYCODE = "stepstone.KEYCODE";

    int priority;
    EditText taskDescription;
    String description;
    Spinner prioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_modifier_screen);

        taskDescription = (EditText)findViewById(R.id.taskDescription_field);
        prioritySpinner = (Spinner)findViewById(R.id.prioritySpinner);

        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void sendData(View view) {
        description = taskDescription.getText().toString();

        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putString("TASK_DESCRIPTION", description);
        extras.putInt("TASK_PRIORITY",priority);
        intent.putExtras(extras);
        setResult(RESULT_OK, intent);
        finish();
    }
}