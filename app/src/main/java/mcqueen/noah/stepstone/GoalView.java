package mcqueen.noah.stepstone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class GoalView extends Fragment{

    private int sortMethod;
    private TaskViewAdapter activeTaskAdapter;
    private GoalViewModel goalModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        goalModel = new ViewModelProvider(requireActivity()).get(GoalViewModel.class);
        goalModel.getNewestTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                activeTaskAdapter.addTask(task);
                activeTaskAdapter.notifyDataSetChanged();
            }
        });

        return inflater.inflate(R.layout.goal_tracking_screen,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        CompletedTaskAdapter completedTaskAdapter = new CompletedTaskAdapter();
        RecyclerView completedTaskRecycler = view.findViewById(R.id.completedList);
        completedTaskRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        completedTaskRecycler.setHasFixedSize(true);
        completedTaskRecycler.setAdapter(completedTaskAdapter);
        activeTaskAdapter = new TaskViewAdapter(goalModel.getActiveTasks().getValue());
        RecyclerView activeTaskRecycler = view.findViewById(R.id.taskList);
        activeTaskRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        activeTaskRecycler.setHasFixedSize(true);
        activeTaskRecycler.setAdapter(activeTaskAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TaskViewAdapter.SwipeToDeleteCallback(activeTaskAdapter));
        itemTouchHelper.attachToRecyclerView(activeTaskRecycler);

        Spinner sortSpinner = view.findViewById(R.id.sortSpinner);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortMethod = position;
                activeTaskAdapter.TaskSort(sortMethod);
                activeTaskAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
