package al.project.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_detail_task extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

            databaseReference = FirebaseDatabase.getInstance().getReference("tasks");

            TextView title = findViewById(R.id.tv_name_task);
            TextView category = findViewById(R.id.tv_category);
            TextView date = findViewById(R.id.tv_date);
            TextView description = findViewById(R.id.tv_description);
            ImageView backButton = findViewById(R.id.btn_kembali);

            backButton.setOnClickListener(v -> finish());

            // Mendapatkan taskId dan detail lainnya dari Intent
            String taskId = getIntent().getStringExtra("taskId");
            String taskName = getIntent().getStringExtra("taskName");
            String taskCategory = getIntent().getStringExtra("category");
            String taskCalender = getIntent().getStringExtra("calender");
            String taskDescription = getIntent().getStringExtra("description");

            title.setText(taskName);
            category.setText(taskCategory);
            date.setText(taskCalender);
            description.setText(taskDescription);

            findViewById(R.id.btn_edit).setOnClickListener(v -> {
                Log.d("activity_detail_task", "Edit button clicked");
                Intent editIntent = new Intent(activity_detail_task.this, activity_edit_task.class);
                editIntent.putExtra("taskId", taskId);
                editIntent.putExtra("taskName", taskName);
                editIntent.putExtra("category", taskCategory);
                editIntent.putExtra("calender", taskCalender);
                editIntent.putExtra("description", taskDescription);
                startActivity(editIntent);
            });

            findViewById(R.id.btn_delete).setOnClickListener(v -> {
                deleteTaskFromDatabase(taskId);
            });
        }

        private void deleteTaskFromDatabase(String taskId) {
            if (taskId != null) {
                databaseReference.child(taskId).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(activity_detail_task.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.w("TaskDetailActivity", "Failed to delete task", e);
                        });
            } else {
                Toast.makeText(activity_detail_task.this, "Error: Task ID is missing", Toast.LENGTH_SHORT).show();
            }
        }

    }