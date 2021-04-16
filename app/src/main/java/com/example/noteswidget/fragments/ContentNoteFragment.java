package com.example.noteswidget.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;

import com.example.noteswidget.model.NoteSourceImpl;
import com.example.noteswidget.R;
import com.example.noteswidget.SocialNetworkAdapter;

public class ContentNoteFragment extends Fragment {

    public static final String ARG_INDEX = "index";
    private int index;
    private SocialNetworkAdapter adapter;

    public static ContentNoteFragment newInstance(int index) {
        ContentNoteFragment fragment = new ContentNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX,index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           index = getArguments().getInt(ARG_INDEX);
        }
    }

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new SocialNetworkAdapter(new NoteSourceImpl(getResources()).getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        adapter.SetOnItemClickListener((view0, position) -> {
            
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initRecyclerView(view);
        return view;
    }

}