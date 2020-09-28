package mcqueen.noah.stepstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText textEditor;
    private Button button;
    private ListView tasklist;
    private TaskCardAdapter adapter;
    private ArrayList<taskCard> arrayList;
    private CheckBox taskCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEditor = (EditText)findViewById(R.id.editText_taskEditor);
        button = (Button)findViewById(R.id.addTask_button);
        tasklist = (ListView)findViewById(R.id.taskList);
        arrayList = new ArrayList<taskCard>();

        adapter = new TaskCardAdapter(getApplicationContext());
        tasklist.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.add(new taskCard(textEditor.getText().toString(), false));
            }
        });
    }
}
