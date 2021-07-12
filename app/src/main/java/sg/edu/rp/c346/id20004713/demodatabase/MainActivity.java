package sg.edu.rp.c346.id20004713.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnGetContent;
    TextView tvResult;
    ListView lvResults;
    EditText etDec, etDate;
    int count = 0; //used to toggle between ascending and descending

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetContent = findViewById(R.id.btnGetContent);
        tvResult = findViewById(R.id.tvResult);
        lvResults = findViewById(R.id.lvResults);
        etDec = findViewById(R.id.etDec);
        etDate = findViewById(R.id.etDate);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create the DBHelper object, passing in the activity context
                DBHelper db = new DBHelper(MainActivity.this);

                //insert a task
                db.insertTask(etDec.getText().toString(), etDate.getText().toString());

                etDec.setText("");
                etDate.setText("");

                Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_LONG).show(); // To show a warning message
            }
        });

        btnGetContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                ArrayList<String> data = db.getTaskContent();
                ArrayList<Task> ArrayList = db.getTasks(count % 2);
                count++;
                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+data.get(i));  //used for debugging
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResult.setText(txt);

                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,ArrayList);
                lvResults.setAdapter(adapter);
            }
        });
    }
}