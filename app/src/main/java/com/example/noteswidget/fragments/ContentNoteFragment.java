package com.example.noteswidget.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;
import android.widget.TextView;

import com.example.noteswidget.model.Note;
import com.example.noteswidget.model.NoteSourceImpl;
import com.example.noteswidget.R;
import com.example.noteswidget.SocialNetworkAdapter;

public class ContentNoteFragment extends Fragment {

    public static final String ARG_INDEX = "note_key";
    private Note note;
    TextView noteTitle, noteContent, noteDate;

    public static ContentNoteFragment newInstance(Note note) {
        ContentNoteFragment fragment = new ContentNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INDEX, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           note = getArguments().getParcelable(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        setAllText(view);
        return view;
    }

    private void setAllText(View view) {
        noteTitle = view.findViewById(R.id.title);
        noteContent = view.findViewById(R.id.content);
        noteDate = view.findViewById(R.id.date);
        System.out.println(note.getTitle());
        System.out.println(note.getContent());
        System.out.println(note.getDate());
        noteTitle.setText(note.getTitle());
        noteContent.setText(note.getContent());
        noteDate.setText(note.getDate());
    }
/*
    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new SocialNetworkAdapter(new NoteSourceImpl(getResources()).getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.SetOnItemClickListener((view0, position) -> {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.note, new NotesFragment());
            transaction.commit();
        });
    }*/
}