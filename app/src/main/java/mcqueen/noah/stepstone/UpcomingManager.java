package mcqueen.noah.stepstone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingManager extends AppCompatActivity {
    private RecyclerView upcomingList;
    private GoalViewModel goalModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_display);

        goalModel = new ViewModelProvider(this).get(GoalViewModel.class);

        upcomingList = findViewById(R.id.upcoming_recycler);
    }
}
