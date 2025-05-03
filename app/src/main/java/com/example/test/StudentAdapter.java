package com.example.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    
    private List<Student> studentList;
    private OnStudentClickListener listener;
    
    // Interface để xử lý sự kiện click
    public interface OnStudentClickListener {
        void onStudentClick(Student student, int position);
    }
    
    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }
    
    public void setOnStudentClickListener(OnStudentClickListener listener) {
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.nameTextView.setText(student.getName());
        holder.mssvTextView.setText(student.getMssv());
        holder.emailTextView.setText(student.getEmail());
        holder.sdtTextView.setText(student.getSdt());
    }
    
    @Override
    public int getItemCount() {
        return studentList.size();
    }
    
    public class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView mssvTextView;
        public TextView emailTextView;
        public TextView sdtTextView;
        
        public StudentViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.text_name);
            mssvTextView = view.findViewById(R.id.text_mssv);
            emailTextView = view.findViewById(R.id.text_email);
            sdtTextView = view.findViewById(R.id.text_sdt);
            
            // Xử lý sự kiện click
            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onStudentClick(studentList.get(position), position);
                }
            });
        }
    }
}
