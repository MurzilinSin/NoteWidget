package com.example.noteswidget.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.fragment.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.noteswidget.ContentNoteActivity;
import com.example.noteswidget.R;
import com.example.noteswidget.fragments.ContentNoteFragment;

public class NotesFragment extends Fragment {
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layout = (LinearLayout)view;
        String[] notes = getResources().getStringArray(R.array.noteName);
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(40);
            layout.addView(tv);
            final int index = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNote(index);
                }
            });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if(isLandscape){
            showLandNote(0);
        }
    }

    private void showNote(int index){
        if(isLandscape) {
            showLandNote(index);
        }
        else{
            showPortNote(index);
        }
    }

    private void showLandNote(int index){
        ContentNoteFragment details = ContentNoteFragment.newInstance(index);
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.note,details);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }


    private void showPortNote(int index) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ContentNoteActivity.class);
        intent.putExtra(ContentNoteFragment.ARG_INDEX,index);
        startActivity(intent);
    }
}