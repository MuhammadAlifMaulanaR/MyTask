package al.project.mytask;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import al.project.mytask.data.Task;
import al.project.mytask.databinding.ActivityAddTaskBinding;

public class activity_add_task extends AppCompatActivity {

    private EditText edtName;
    private EditText edtCategory;
    private EditText edtDescription;
    private EditText edtCalender;

    private Calendar myCalender = Calendar.getInstance();

    private ActivityAddTaskBinding addBinding;

    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addBinding = ActivityAddTaskBinding.inflate(getLayoutInflater());
        setContentView(addBinding.getRoot());

        edtName = findViewById(R.id.edt_task_name);
        edtCategory = findViewById(R.id.edt_task_category);
        edtDescription = findViewById(R.id.edt_task_description);
        edtCalender = findViewById(R.id.edt_task_calender);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("tasks");

        edtCalender.setOnClickListener(view -> {
            new DatePickerDialog(activity_add_task.this, date, myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
        });

        addBinding.btnCreateTask.setOnClickListener(View -> addTask());

        addBinding.btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addTask();
            }
        });

        addBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(activity_add_task.this, activity_list_task.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addTask(){
        String taskName = addBinding.edtTaskName.getText().toString();
        String calender = addBinding.edtTaskCalender.getText().toString();
        String description = addBinding.edtTaskDescription.getText().toString();
        String category = addBinding.edtTaskCategory.getText().toString();

        if(taskName.isEmpty() || calender.isEmpty() || description.isEmpty() || category.isEmpty()){
            Toast.makeText(this, "Harap lengkapi data", Toast.LENGTH_SHORT).show();
            return;
        }

        String taskId = reference.push().getKey();
        Task task = new Task(taskId, taskName, category, calender, description);

        reference.child(taskId).setValue(task).addOnCompleteListener(task1 -> {
           if (task1.isSuccessful()){
               Toast.makeText(activity_add_task.this, "Task berhasil ditambahkan", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(activity_add_task.this, activity_list_task.class);
               startActivity(intent);
               finish();
           } else {
               Toast.makeText(activity_add_task.this, "Task gagal ditambahkan", Toast.LENGTH_SHORT).show();
           }
        });
    }

    DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        myCalender.set(Calendar.YEAR, year);
        myCalender.set(Calendar.MONTH, monthOfYear);
        myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    private void updateLabel() {
        String myFormat = "dd MMM, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtCalender.setText(sdf.format(myCalender.getTime()));
    }

}