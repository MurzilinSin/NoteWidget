package com.example.noteswidget.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

//import com.example.noteswidget.ContentNoteActivity;
import com.example.noteswidget.MainActivity;
import com.example.noteswidget.Navigation;
import com.example.noteswidget.R;
import com.example.noteswidget.SocialNetworkAdapter;
import com.example.noteswidget.fragments.ContentNoteFragment;
import com.example.noteswidget.model.Note;
import com.example.noteswidget.model.NoteSourceImpl;
import com.example.noteswidget.observe.Observer;
import com.example.noteswidget.observe.Publisher;

import java.util.List;

public class NotesFragment extends Fragment {
    private boolean isLandscape;
    private NoteSourceImpl note;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private static final int MY_DEFAULT_DURATION = 1000;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;
    private Note newNote = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return inflater.inflate(R.layout.fragment_content_note, container, false);
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        note = new NoteSourceImpl(getResources()).init();
        initRecyclerView(view, note);
    }

    private void initRecyclerView(View view, NoteSourceImpl note) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new SocialNetworkAdapter(note, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
        if (moveToLastPosition){
            recyclerView.smoothScrollToPosition(note.size() - 1);
            moveToLastPosition = false;
        }
        adapter.setOnItemClickListener((view0, position) -> {
            FragmentManager fragmentManager = getFragmentManager();
            // до этого ставила getChildFragmentManager() и вылетало с IllegalArgumentException: No view found for id.
            // и везде пишут, что его надо использовать его, но стоило поставить обынчный менеджер и все работает.
            // ведь при открытия фрагмента во фрагменте я же должен использовать childManager. так почему он не сработал должен образом ?
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.notes, ContentNoteFragment.newInstance(note.getNote(position)));
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                note.addNote(new Note("Заголовок " + note.size(), "Описание" + note.size(), "Дата " + note.size()));
                navigation.addFragment(NoteAddFragment.newInstance(),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNote(Note anotherNote) {
                        note.addNote(anotherNote);
                        adapter.notifyItemInserted(note.size()-1);
                        moveToLastPosition = true;
                    }
                });
                return true;
            case R.id.action_clear:
                note.clearNotes();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_note_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch(item.getItemId()) {
            case R.id.action_update:
                navigation.addFragment(NoteAddFragment.newInstance(note.getNote(position)),true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNote(Note updatedNote) {
                        note.updateNote(position,updatedNote);
                        adapter.notifyItemChanged(position);
                    }
                });
                return true;
            case R.id.action_delete:
                note.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(newNote != null) {
            note.addNote(newNote);
            adapter.notifyItemInserted(note.size() - 1);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.smoothScrollToPosition(note.size() - 1);
                }
            }, 250);

            newNote = null;
        }
    }

    /*   @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        List<Note> dataSource = new NoteSourceImpl(getResources()).getData();
        System.out.println(dataSource.get(0).getTitle());
    }*/

/*  @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if(isLandscape){
            //showLandNote(0);
        }
    }

    private void showNote(int index){
        if(isLandscape) {
            //showLandNote(index);
        }
        else{
            showPortNote(index);
        }
    }*/

/*    private void showLandNote(int index){
        ContentNoteFragment details = ContentNoteFragment.newInstance(index);
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.note,details);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }*/

/*
    private void showPortNote(int index) {
        Intent intent = new Intent();
       // intent.setClass(getActivity(), ContentNoteActivity.class);
        intent.putExtra(ContentNoteFragment.ARG_INDEX,index);
        startActivity(intent);
    }*/
}