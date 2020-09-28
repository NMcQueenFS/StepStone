package mcqueen.noah.stepstone;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class taskCard extends MainActivity {

    String description;
    TextView descriptionView;
    boolean completed;
    boolean repeatable;
    CheckBox completedCheck;
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

        repeatableSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (repeatableSwitch.isChecked()) repeatSpinner.setVisibility(view.VISIBLE);
                else repeatSpinner.setVisibility(view.INVISIBLE);
            }
        });

    }
}

