package com.example.noteswidget.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private final String title;
    private final String date;
    private final String content;
    private String id;

    public Note(String title, String date, String content){
        this.title = title;
        this.date = date;
        this.content = content;
    }

    protected Note(Parcel in) {
        title = in.readString();
        date = in.readString();
        content = in.readString();
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

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(content);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
