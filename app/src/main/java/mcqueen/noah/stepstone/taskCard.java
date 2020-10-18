package mcqueen.noah.stepstone;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class taskCard extends MainActivity {

    taskCard() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.repeat_choices,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}

