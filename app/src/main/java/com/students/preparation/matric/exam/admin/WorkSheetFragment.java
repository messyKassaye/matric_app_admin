package com.students.preparation.matric.exam.admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.model.NoteTipModel;
import com.students.preparation.matric.exam.model.Worksheet;


public class WorkSheetFragment extends Fragment implements View.OnClickListener {

    //these are the views
    TextView textViewStatus;
    EditText documentTitle, documentContent;
    ProgressBar progressBar;

    //the fire base objects for storage and database
    DatabaseReference mDatabaseReference;

    Spinner stream, subject, grade;
    String typeSelected = "";
    public WorkSheetFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_work_sheet, container, false);

        //getting the views
        textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
        documentTitle = (EditText) view.findViewById(R.id.admin_input_book_title);
        documentContent = (EditText) view.findViewById(R.id.admin_content_);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        //attaching listeners to views
        view.findViewById(R.id.btn_admin_submit).setOnClickListener(this);
        //findViewById(R.id.textViewUploads).setOnClickListener(this);

        stream = view.findViewById(R.id.admin_stream);
        grade = view.findViewById(R.id.admin_grade);
        subject = view.findViewById(R.id.admin_subject);


        return view;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_admin_submit:
                //dbPath();
                //getPDF();

                if(documentContent.getText().length() != 0 && documentTitle.getText().length() != 0 &&  subject.getSelectedItemPosition() !=0) {
                    mDatabaseReference = FirebaseDatabase
                            .getInstance()
                            .getReference(Constants.DATABASE_PATH_WORKSHEET);

                    Worksheet noteTipsModel = new Worksheet(documentTitle.getText().toString(), stream.getSelectedItem().toString(), grade.getSelectedItem().toString(), subject.getSelectedItem().toString(), documentContent.getText().toString());
                    Task<Void> xx = mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(noteTipsModel);
                    xx.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setTitle("Success");
                            alert.setMessage("Worksheet Inserted Successfully");
                            alert.setPositiveButton("OK", null);
                            alert.show();
                        }
                    });
                }else{
                    Toast.makeText(getActivity() , "Please check the form again, and fill all the required information." , Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.textViewUploads:
                //startActivity(new Intent(this, ViewUploadsActivity.class));
                break;
        }
    }

}