package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity implements StudentAdapter.OnStudentClickListener {

    private static final int REQUEST_STUDENT_DETAIL = 1001;
    private static final int REQUEST_ADD_STUDENT = 1002;

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recycler_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Khởi tạo danh sách sinh viên
        studentList = new ArrayList<>();
        loadStudentData();

        // Khởi tạo adapter
        adapter = new StudentAdapter(studentList);
        adapter.setOnStudentClickListener(this);
        recyclerView.setAdapter(adapter);

        // Khởi tạo FAB
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddStudentActivity();
            }
        });
    }

    private void loadStudentData() {
        // Thêm dữ liệu mẫu
        studentList.add(new Student("Hoang Duc", "20225286", "ducdh225286@sis.hust.edu.vn", "0976243559"));
        studentList.add(new Student("Nguyen Van A", "20225386", "a.nv225386@sis.hust.edu.vn", "0123456789"));
        studentList.add(new Student("Tran Thi B", "20225246", "b.tt225246@sis.hust.edu.vn", "0987654321"));
        studentList.add(new Student("Le Van C", "20225486", "c.lv225486@sis.hust.edu.vn", "0123123123"));

        // Thêm nhiều dữ liệu hơn để kiểm tra cuộn
        for (int i = 1; i <= 10; i++) {
            studentList.add(new Student(
                    "Sinh viên " + i,
                    "2022" + (5000 + i),
                    "sv" + i + "@sis.hust.edu.vn",
                    "098765432" + i
            ));
        }
    }

    private void openAddStudentActivity() {
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivityForResult(intent, REQUEST_ADD_STUDENT);
    }

    @Override
    public void onStudentClick(Student student, int position) {
        // Chuyển đến màn hình chi tiết sinh viên
        Intent intent = new Intent(this, StudentDetailActivity.class);
        intent.putExtra("student", student);
        intent.putExtra("position", position);
        startActivityForResult(intent, REQUEST_STUDENT_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_STUDENT_DETAIL) {
                // Xử lý kết quả từ StudentDetailActivity (xóa sinh viên)
                int position = data.getIntExtra("delete_position", -1);
                if (position != -1 && position < studentList.size()) {
                    // Xóa sinh viên khỏi danh sách
                    Student deletedStudent = studentList.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, studentList.size() - position);

                    Toast.makeText(this, "Đã xóa sinh viên: " + deletedStudent.getName(), Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_ADD_STUDENT) {
                // Xử lý kết quả từ AddStudentActivity (thêm sinh viên)
                Student newStudent = (Student) data.getSerializableExtra("new_student");
                if (newStudent != null) {
                    // Thêm sinh viên mới vào đầu danh sách
                    studentList.add(0, newStudent);
                    adapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add) {
              openAddStudentActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
