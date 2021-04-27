package com.example.noteswidget.model;

import android.content.res.Resources;
import com.example.noteswidget.R;
import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private final List<Note> noteSource;
    private final Resources resources;

    public NoteSourceImpl(Resources resources){
        noteSource = new ArrayList<>();
        this.resources = resources;
    }

    public NoteSourceImpl init(NoteSourceResponse noteSourceResponse) {
        String[] titles = resources.getStringArray(R.array.noteName);
        String[] dates = resources.getStringArray(R.array.noteDate);
        String[] contents = resources.getStringArray(R.array.noteContent);
        for (int i = 0; i < contents.length; i++) {
            noteSource.add(new Note(titles[i],dates[i],contents[i]));
        }
        if(noteSourceResponse != null){
            noteSourceResponse.initialized(this);
        }
        return this;
    }

    @Override
    public Note getNote(int position) {
        return noteSource.get(position);
    }

    public int size(){
        return noteSource.size();
    }

    @Override
    public void deleteNote(int position) {
        noteSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        noteSource.set(position,note);
    }

    @Override
    public void addNote(Note note) {
        noteSource.add(note);
    }

    @Override
    public void clearNotes() {
        noteSource.clear();
    }

}
