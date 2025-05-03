package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText mssvEditText;
    private EditText emailEditText;
    private EditText sdtEditText;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Ánh xạ các view
        nameEditText = findViewById(R.id.edit_name);
        mssvEditText = findViewById(R.id.edit_mssv);
        emailEditText = findViewById(R.id.edit_email);
        sdtEditText = findViewById(R.id.edit_sdt);
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);

        // Xử lý sự kiện nút Lưu
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });

        // Xử lý sự kiện nút Hủy
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveStudent() {
        // Lấy dữ liệu từ các EditText
        String name = nameEditText.getText().toString().trim();
        String mssv = mssvEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String sdt = sdtEditText.getText().toString().trim();

        // Kiểm tra dữ liệu
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Vui lòng nhập họ tên");
            return;
        }

        if (TextUtils.isEmpty(mssv)) {
            mssvEditText.setError("Vui lòng nhập mã số sinh viên");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Vui lòng nhập email");
            return;
        }

        if (TextUtils.isEmpty(sdt)) {
            sdtEditText.setError("Vui lòng nhập số điện thoại");
            return;
        }

        // Tạo đối tượng Student mới
        Student newStudent = new Student(name, mssv, email, sdt);

        // Trả về kết quả
        Intent resultIntent = new Intent();
        resultIntent.putExtra("new_student", newStudent);
        setResult(RESULT_OK, resultIntent);
        
        Toast.makeText(this, "Đã thêm sinh viên: " + name, Toast.LENGTH_SHORT).show();
        finish();
    }
}
