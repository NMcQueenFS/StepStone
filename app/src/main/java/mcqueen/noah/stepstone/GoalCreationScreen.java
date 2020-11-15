package mcqueen.noah.stepstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GoalCreationScreen extends AppCompatActivity {
    private EditText goalDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_creation_screen);
        goalDescription = findViewById(R.id.goalDescription_field);
    }

    //Called if Add Task/Accept Changes button is pushed
    public void sendData(View view) {
        String description = goalDescription.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("GOAL_DESCRIPTION", description);
        setResult(5, intent);
        finish();
    }


}
