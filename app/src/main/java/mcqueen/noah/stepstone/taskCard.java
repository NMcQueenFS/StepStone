package mcqueen.noah.stepstone;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

public class taskCard extends MainActivity {

    String description;
    boolean completed;
    private Spinner repeatSpinner;
    private Switch repeatableSwitch;



    taskCard(String newDescription, boolean isComplete) {
        description = newDescription;
        completed = isComplete;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repeatSpinner = (Spinner)findViewById(R.id.repeat_spinner);
        repeatableSwitch = (Switch)findViewById(R.id.repeatable_switch);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.repeat_choices,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(spinnerAdapter);
    }
}

