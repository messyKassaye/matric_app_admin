package com.students.preparation.matric.exam.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.StudentsModel;
import com.students.preparation.matric.exam.model.Tutorials;

import java.util.ArrayList;


public class AdminHomeFragment extends Fragment {
    private TextView totalNewStudent,totalApprovedStudent,totalTutorial;
    private Button showAllNewStudent,showAllApprovedStudent;
    private ArrayList<StudentsModel> newStudentArrayList = new ArrayList<>();
    private ArrayList<StudentsModel> appreovedStudentArrayList = new ArrayList<>();
    private ArrayList<Tutorials> tutorialsArrayList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        //view initialization
        totalNewStudent = view.findViewById(R.id.newStudentsNumber);
        totalApprovedStudent = view.findViewById(R.id.approvedStudentsNumber);
        totalTutorial =view.findViewById(R.id.totalTutorialNumbers);

        showAllNewStudent = view.findViewById(R.id.showAllNewStudentsButton);
        showAllApprovedStudent = view.findViewById(R.id.showAllApprovedStudentButton);
        showAllNewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AdminMainDashboard dashboard= (AdminMainDashboard) (getActivity());
               dashboard.showNewStudentFragment();
            }
        });

        showAllApprovedStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminMainDashboard dashboard= (AdminMainDashboard) (getActivity());
                dashboard.showApprovedStudentFragment();
            }
        });
        populateRegStudents();
        return view;
    }

    private void populateRegStudents() {
        //getting the database reference
        //database reference to get uploads data
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_REG_STUDENTS);


        //retrieving upload data from firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentsModel uploadsModel = postSnapshot.getValue(StudentsModel.class);
                    newStudentArrayList.add(uploadsModel);
                }
                totalNewStudent.setText(""+newStudentArrayList.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_APPROVED_STUDENTS);


        //retrieving upload data from firebase database
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    StudentsModel uploadsModel = postSnapshot.getValue(StudentsModel.class);
                    appreovedStudentArrayList.add(uploadsModel);
                }

                totalApprovedStudent.setText(""+appreovedStudentArrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       DatabaseReference tdatabaseReference = FirebaseDatabase.getInstance()
                .getReference(Constants.DATABASE_PATH_TUTORIALS);
        tdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tutorialSnapshot : dataSnapshot.getChildren()) {
                    Tutorials uploadsModel = tutorialSnapshot.getValue(Tutorials.class);
                    tutorialsArrayList.add(uploadsModel);
                }
                totalTutorial.setText(""+tutorialsArrayList.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
