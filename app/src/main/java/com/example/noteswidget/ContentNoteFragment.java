package com.example.noteswidget;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.*;
import android.widget.TextView;
import java.util.Arrays;

public class ContentNoteFragment extends Fragment {

    public static final String ARG_INDEX = "index";
    private int index;
    Note note = new Note();

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_note, container, false);

        Note note = new Note();
        note.setNoteContents(Arrays.asList(getResources().getStringArray(R.array.noteContent)));
        note.setNoteDates(Arrays.asList(getResources().getStringArray(R.array.noteDate)));
        note.setNoteNames(Arrays.asList(getResources().getStringArray(R.array.noteName)));

        TextView tvName = view.findViewById(R.id.name_note);
        TextView tvDate = view.findViewById(R.id.data_note);
        TextView tvContent = view.findViewById(R.id.content_note);
        /*TypedArray names = getResources().obtainTypedArray(R.array.noteName);
        TypedArray dates = getResources().obtainTypedArray(R.array.noteDate);
        TypedArray contents = getResources().obtainTypedArray(R.array.noteContent);*/
        //AppCompatImageView imageView = view.findViewById(R.id.content_note);
        //TypedArray images = getResources().obtainTypedArray(R.array.numbs);
        // imageView.setImageResource(images.getResourceId(index,-1));
        tvName.setText(note.getCurrentNoteName(index));
        tvName.setTextSize(30);
        tvDate.setText(note.getCurrentNoteData(index));
        tvDate.setTextSize(20);
        tvContent.setText(note.getCurrentNoteContent(index));
        tvContent.setTextSize(24);
        return view;
    }
}