package com.example.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView mssvTextView;
    private TextView emailTextView;
    private TextView sdtTextView;
    private Button backButton;
    private Student student;
    private int studentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        // Ánh xạ các view
        nameTextView = findViewById(R.id.text_detail_name);
        mssvTextView = findViewById(R.id.text_detail_mssv);
        emailTextView = findViewById(R.id.text_detail_email);
        sdtTextView = findViewById(R.id.text_detail_sdt);
        backButton = findViewById(R.id.button_back);

        // Lấy dữ liệu từ Intent
        student = (Student) getIntent().getSerializableExtra("student");
        studentPosition = getIntent().getIntExtra("position", -1);

        if (student != null) {
            // Hiển thị thông tin sinh viên
            nameTextView.setText(student.getName());
            mssvTextView.setText(student.getMssv());
            emailTextView.setText(student.getEmail());
            sdtTextView.setText(student.getSdt());
        }

        // Xử lý sự kiện nút quay lại
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete) {
            // Hiển thị hộp thoại xác nhận xóa
            showDeleteConfirmationDialog();
            return true;
        } else if (id == R.id.call) {
            // Gọi điện thoại
            makePhoneCall();
            return true;
        } else if (id == R.id.email) {
            // Gửi email
            sendEmail();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên " + student.getName() + "?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteStudent();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteStudent() {
        // Tạo Intent để trả về kết quả xóa
        Intent resultIntent = new Intent();
        resultIntent.putExtra("delete_position", studentPosition);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Đã xóa sinh viên " + student.getName(), Toast.LENGTH_SHORT).show();
        finish();
    }

    private void makePhoneCall() {
        String phoneNumber = student.getSdt();
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng gọi điện", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail() {
        String email = student.getEmail();
        if (email != null && !email.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + email));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Liên hệ từ ứng dụng Quản lý sinh viên");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Không tìm thấy ứng dụng email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}
