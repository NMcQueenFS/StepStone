package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements taskCard_creator.CreatorDialogListener{

    private Button button;
    private ListView tasklist;
    private TaskCardAdapter adapter;
    private ArrayList<taskCard> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.addTask_button);
        tasklist = (ListView)findViewById(R.id.taskList);
        arrayList = new ArrayList<taskCard>();

        adapter = new TaskCardAdapter(getApplicationContext());
        tasklist.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new taskCard_creator();
                newFragment.show(getSupportFragmentManager(), "taskCreator");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        adapter.add(new taskCard(null,false));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void setTaskDescription(String description) {

    }

    @Override
    public void getTaskDescription(String description) {
        adapter.add(new taskCard(description,false));
    }
}
