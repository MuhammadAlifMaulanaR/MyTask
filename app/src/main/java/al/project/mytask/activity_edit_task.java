package al.project.mytask;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class activity_edit_task extends AppCompatActivity {

        private EditText taskNameInput, taskCategoryInput, calenderInput, descriptionInput;
//        private AutoCompleteTextView categorySpinner;
        private DatabaseReference databaseReference;
        private String taskId;  // Variable to store task ID

        @SuppressLint("WrongViewCast")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_task);

            databaseReference = FirebaseDatabase.getInstance().getReference("tasks");

            taskId = getIntent().getStringExtra("taskId");

            if (taskId == null) {
                Toast.makeText(this, "Error: Task ID is missing.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }


            Log.d("activity_edit_task", "Received taskId: " + taskId);


//            categorySpinner = findViewById(R.id.category_spinner);
//            categorySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, new String[]{"Life", "Sport", "Education"}));

            String taskId = getIntent().getStringExtra("taskId");
            String taskName = getIntent().getStringExtra("taskName");
            String taskCategory = getIntent().getStringExtra("category");
            String taskCalender = getIntent().getStringExtra("calender");
            String taskDescription = getIntent().getStringExtra("description");

            taskNameInput = findViewById(R.id.edit_task_name);
            taskCategoryInput = findViewById(R.id.edit_task_category);
            calenderInput = findViewById(R.id.edit_task_calender);
            descriptionInput = findViewById(R.id.edit_task_description);

            ImageView backButton = findViewById(R.id.btn_backk);
            backButton.setOnClickListener(v -> finish());

            // mengambil data kemudian dan memberikan data ke intent
            // categorySpinner.setText(getIntent().getStringExtra("category"), false);
            taskNameInput.setText(getIntent().getStringExtra("taskName"));
            taskCategoryInput.setText(getIntent().getStringExtra("category"));
            calenderInput.setText(getIntent().getStringExtra("calender"));
            descriptionInput.setText(getIntent().getStringExtra("description"));

            Log.d("activity_edit_task", "Received taskName: " + getIntent().getStringExtra("taskName"));
            Log.d("activity_edit_task", "Received category: " + getIntent().getStringExtra("category"));
            Log.d("activity_edit_task", "Received calender: " + getIntent().getStringExtra("calender"));
            Log.d("activity_edit_task", "Received description: " + getIntent().getStringExtra("description"));

            findViewById(R.id.btn_create_task).setOnClickListener(v -> saveTask());

            findViewById(R.id.btn_backk).setOnClickListener(view -> {
                Intent intent = new Intent(activity_edit_task.this, activity_list_task.class);
                startActivity(intent);
                finish();
            });

            calenderInput.setOnClickListener(this::showDatePicker);
        }

        public void showDatePicker(View view) {
            final Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (datePicker, year, monthOfYear, dayOfMonth) -> calenderInput.setText(String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year)),
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }

        private void saveTask() {
            Map<String, Object> updates = new HashMap<>();
            updates.put("taskName", taskNameInput.getText().toString());
            updates.put("category", taskCategoryInput.getText().toString());
            updates.put("calender", calenderInput.getText().toString());
            updates.put("description", descriptionInput.getText().toString());

            updateTaskById(updates);
        }

        private void updateTaskById(Map<String, Object> updates) {
            if (taskId == null || taskId.isEmpty()) {
                Toast.makeText(activity_edit_task.this, "Error: Task ID is missing", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference taskRef = databaseReference.child(taskId);

            taskRef.updateChildren(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(activity_edit_task.this, "Task updated successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(activity_edit_task.this, activity_list_task.class);
                        intent.putExtra("taskId", taskId);
                        intent.putExtra("taskName", updates.get("taskName").toString());
                        intent.putExtra("category", updates.get("category").toString());
                        intent.putExtra("calender", updates.get("calender").toString());
                        intent.putExtra("description", updates.get("description").toString());
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(activity_edit_task.this, "Failed to update task.", Toast.LENGTH_SHORT).show());
        }


    }