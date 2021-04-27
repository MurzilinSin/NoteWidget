package com.example.noteswidget.model;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {

    public static class Fields {
        public final static String TITLE = "title";
        public final static String CONTENT = "content";
        public final static String DATE = "date";
    }

    public static Note toNoteData (String id, Map<String,Object> doc) {

        Note answer = new Note((String)doc.get(Fields.TITLE),(String) doc.get(Fields.DATE),(String) doc.get(Fields.CONTENT));
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note note){
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE,note.getTitle());
        answer.put(Fields.CONTENT,note.getContent());
        answer.put(Fields.DATE,note.getDate());
        return answer;
    }
}
