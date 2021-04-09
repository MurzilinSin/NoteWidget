package com.example.noteswidget;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private String currentNoteName;
    private String currentNoteData;
    private String currentNoteContent;

    private List<String> noteNames = new ArrayList<>();
    private List<String> noteDates = new ArrayList<>();
    private List<String> noteContents = new ArrayList<>();


    public String getCurrentNoteContent(int index) {
        for (int i = 0; i < noteContents.size(); i++) {
            if(i == index){
                return noteContents.get(i);
            }
        }
        return null;
    }

    public String getCurrentNoteName(int index) {
        for (int i = 0; i < noteNames.size(); i++) {
            if(i == index){
                return noteNames.get(i);
            }
        }
        return null;
    }

    public String getCurrentNoteData(int index) {
        for (int i = 0; i < noteDates.size(); i++) {
            if(i == index){
                return noteDates.get(i);
            }
        }
        return null;
    }


    public void setNoteContents(List<String> noteContents) {
        this.noteContents = noteContents;
    }

    public void setNoteNames(List<String> noteNames) {
        this.noteNames = noteNames;
    }

    public void setNoteDates(List<String> noteDates) {
        this.noteDates = noteDates;
    }
}
