package com.example.noteswidget.model;

import android.util.Log;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.*;
import java.util.*;


public class NoteSourceFirebase implements NoteSource{
    private static final String NOTES_COLLECTION = "notes";
    private static final String TAG = "[NoteSourceFirebase]";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES_COLLECTION);
    private List<Note> notesData = new ArrayList<>();

    @Override
    public NoteSource init(final NoteSourceResponse noteSourceResponse) {
        //collection.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get().addOnCompleteListener()
        collection.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                notesData = new ArrayList<>();
                for(QueryDocumentSnapshot document : task.getResult()) {
                    Map<String,Object> doc = document.getData();
                    String id = document.getId();
                    Note note = NoteDataMapping.toNoteData(id,doc);
                    notesData.add(note);
                }
                Log.d(TAG, "success " + notesData.size() + " wtf");
                noteSourceResponse.initialized(NoteSourceFirebase.this);
            }
            else {
                Log.d(TAG, "get failed with this shit", task.getException());
                }
            }).addOnFailureListener(e -> Log.d(TAG, "get failed with shit ", e));
        return this;
    }

    @Override
    public Note getNote(int position) {
        return notesData.get(position);
    }

    @Override
    public void deleteNote(int position) {
        collection.document(notesData.get(position).getId()).delete();
        notesData.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteDataMapping.toDocument(note));
    }

    @Override
    public void addNote(Note note) {
        collection.add(NoteDataMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNotes() {
        for (Note note : notesData) {
            collection.document(note.getId()).delete();
        }
        notesData = new ArrayList<>();
    }

    @Override
    public int size() {
        if(notesData == null){
            return 0;
        }
        return notesData.size();
    }
}
