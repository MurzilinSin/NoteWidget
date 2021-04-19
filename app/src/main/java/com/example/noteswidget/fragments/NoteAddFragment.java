package com.example.noteswidget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.noteswidget.MainActivity;
import com.example.noteswidget.R;
import com.example.noteswidget.model.Note;
import com.example.noteswidget.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

public class NoteAddFragment extends Fragment {
    private Note note;
    private Publisher publisher;
    private static final String ARG_NOTE_ADD = "NOTE_ADD";
    private TextInputEditText title;
    private TextInputEditText description;

    public static NoteAddFragment newInstance(Note note){
        NoteAddFragment fragment = new NoteAddFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_ADD, note);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteAddFragment newInstance() {
        NoteAddFragment fragment = new NoteAddFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            note = getArguments().getParcelable(ARG_NOTE_ADD);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_add, container,false);
        initView(view);
        if(note != null){
            populateView();
        }
        return view;
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
    }

    private void populateView(){
        title.setText(note.getTitle());
        description.setText(note.getContent());
    }

    @Override
    public void onStop() {
        super.onStop();
        note = collectNote();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(note);
    }

    private Note collectNote() {
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        /*if(note != null){

        }*/
        return new Note(title, "12",description);
    }


}