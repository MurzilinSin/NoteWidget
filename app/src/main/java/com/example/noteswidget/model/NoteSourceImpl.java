package com.example.noteswidget.model;

import android.content.res.Resources;

import com.example.noteswidget.R;
import com.example.noteswidget.model.Note;
import com.example.noteswidget.model.NoteSource;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private final List<Note> dataSource;
    private final Resources resources;

    public NoteSourceImpl(Resources resources){
        dataSource = new ArrayList<>();
        this.resources = resources;
    }
    @Override
    public List<Note> getData() {
        String[] titles = resources.getStringArray(R.array.noteName);
        String[] dates = resources.getStringArray(R.array.noteDate);
        String[] contents = resources.getStringArray(R.array.noteContent);
        for (int i = 0; i < contents.length; i++) {
            dataSource.add(new Note(titles[i],dates[i],contents[i]));
        }
        return dataSource;
    }

}
