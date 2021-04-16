package com.example.noteswidget.model;

import java.util.*;

public class Note {
    private final String title;
    private final String date;
    private final String content;

    public Note(String title, String date, String content){
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

   /* private List<String> noteNames = new ArrayList<>();
    private List<String> noteDates = new ArrayList<>();
    private List<String> noteContents = new ArrayList<>();


    public String getCurrentNoteContent(int index) {
        if (index < 0 || index >= noteContents.size()) {
            return null;
        }
        return noteContents.get(index);
    }

    public String getCurrentNoteName(int index) {
        if (index < 0 || index >= noteNames.size()) {
            return null;
        }
        return noteNames.get(index);
    }

    public String getCurrentNoteData(int index) {
        if (index < 0 || index >= noteDates.size()) {
            return null;
        }
        return noteDates.get(index);
    }
*/
/*
    public void setNoteContents(List<String> noteContents) {
        this.noteContents = noteContents;
    }

    public void setNoteNames(List<String> noteNames) {
        this.noteNames = noteNames;
    }

    public void setNoteDates(List<String> noteDates) {
        this.noteDates = noteDates;
    }*/
}
