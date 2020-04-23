package com.students.preparation.matric.exam.modules.Students.fragment.shortnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.students.preparation.matric.exam.Constants;
import com.students.preparation.matric.exam.R;
import com.students.preparation.matric.exam.adapter.AdminShortNotesAdapter;
import com.students.preparation.matric.exam.model.NoteTipModel;

import java.util.ArrayList;


public class ShortNotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView noNotesFound;

    private AdminShortNotesAdapter adapter;
    private ArrayList<NoteTipModel> notesArrayList = new ArrayList<>();
    DatabaseReference mDatabaseReference;
    public ShortNotesFragment() {
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
        final View view= inflater.inflate(R.layout.fragment_short_notes, container, false);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_SHORTNOTES);

        noNotesFound = view.findViewById(R.id.adminNoNotesFound);
        adapter = new AdminShortNotesAdapter(getContext(),notesArrayList);
        recyclerView = view.findViewById(R.id.adminShortNotesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        NoteTipModel uploadsModel = postSnapshot.getValue(NoteTipModel.class);
                        if (uploadsModel.getType().equals("Shortnote")){
                            notesArrayList.add(uploadsModel);
                        }
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    noNotesFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       return view;
    }


}
