package al.project.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import al.project.mytask.adapter.TaskAdapter;
import al.project.mytask.data.Task;

public class activity_list_task extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;
    private DatabaseReference reference;
    private TextView noTaskView;
    private TextView noTaskView2;
    private ImageView noTaskimg;
    private TextView greeting;;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_task);

            findViewById(R.id.btn_fab_add).setOnClickListener(view -> {
                Intent intent = new Intent(activity_list_task.this, activity_add_task.class);
                startActivity(intent);
            });
            greeting = findViewById(R.id.tv_hello);
            recyclerView = findViewById(R.id.rv_list);

            String name = "Alif"; // Ganti dengan nama dinamis jika diperlukan
            greeting.setText("Hello, " + name);

            recyclerView = findViewById(R.id.rv_list);
            noTaskView = findViewById(R.id.tv_empty);
            noTaskView2 = findViewById(R.id.tv_empty2);
            noTaskimg = findViewById(R.id.img_empty);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            taskList = new ArrayList<>();
            adapter = new TaskAdapter(taskList);
            recyclerView.setAdapter(adapter);

            loadDataFromFirebase();
        }
    private void loadDataFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("tasks");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    taskList.add(task);
                }
                adapter.notifyDataSetChanged();
                if (taskList.isEmpty()) {
                    noTaskView.setVisibility(View.VISIBLE);
                    noTaskView2.setVisibility(View.VISIBLE);
                    noTaskimg.setVisibility(View.VISIBLE);
                } else {
                    noTaskView.setVisibility(View.GONE);
                    noTaskView2.setVisibility(View.GONE);
                    noTaskimg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("activityListTask", "Failed to read value.", error.toException());
                }
            });
        }
}