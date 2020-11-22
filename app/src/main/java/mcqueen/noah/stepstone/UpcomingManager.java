package mcqueen.noah.stepstone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import mcqueen.noah.stepstone.primitives.Goal;
import mcqueen.noah.stepstone.primitives.Task;

public class UpcomingManager extends Fragment {
    private RecyclerView upcomingList,criticalList;
    private UpcomingAdapter upcomingAdapter,criticalAdapter;
    private GoalViewModel goalModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        goalModel = new ViewModelProvider(requireActivity()).get(GoalViewModel.class);
        return inflater.inflate(R.layout.upcoming_display,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        upcomingList = view.findViewById(R.id.upcoming_recycler);
        upcomingAdapter = new UpcomingAdapter();
        upcomingList.setAdapter(upcomingAdapter);

        criticalList = view.findViewById(R.id.critical_recycler);
        criticalAdapter = new UpcomingAdapter();
        criticalList.setAdapter(criticalAdapter);

        upcomingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        criticalList.setLayoutManager(new LinearLayoutManager(getActivity()));
        upcomingList.setHasFixedSize(true);
        criticalList.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        upcomingList.removeAllViews();
        criticalList.removeAllViews();

        List<Goal> goals = goalModel.getAllGoals().getValue();
        List<Task> urgentList = new ArrayList<>();
        List<Task> upcomingDateList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 3);

        if (goals != null) {
            for (int i = 0; i < goals.size(); i++) {
                List<Task> tasks = goals.get(i).getActiveTasks();

                for (int j = 0; j < tasks.size(); j++) {
                    if (tasks.get(j).getPriority() == 5) urgentList.add(tasks.get(j));
                    if (tasks.get(j).getDueDate().before(c.getTime())) upcomingDateList.add(tasks.get(j));
                }
            }

            for (int i = 0; i < upcomingDateList.size(); i++) { upcomingAdapter.addTask(new Task(urgentList.get(i).getDescription(),urgentList.get(i).getDueDate(),urgentList.get(i).getPriority())); }
            for (int i = 0; i < urgentList.size(); i++) { criticalAdapter.addTask(new Task(urgentList.get(i).getDescription(),urgentList.get(i).getDueDate(),urgentList.get(i).getPriority())); }
        }
    }
}
