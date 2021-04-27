package com.example.noteswidget.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.*;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.*;
import androidx.recyclerview.widget.*;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.AdapterView;


//import com.example.noteswidget.ContentNoteActivity;
import com.example.noteswidget.MainActivity;
import com.example.noteswidget.Navigation;
import com.example.noteswidget.R;
import com.example.noteswidget.SocialNetworkAdapter;
import com.example.noteswidget.fragments.ContentNoteFragment;
import com.example.noteswidget.model.Note;
import com.example.noteswidget.model.NoteSource;
import com.example.noteswidget.model.NoteSourceFirebase;
import com.example.noteswidget.model.NoteSourceImpl;
import com.example.noteswidget.model.NoteSourceResponse;
import com.example.noteswidget.observe.Observer;
import com.example.noteswidget.observe.Publisher;

import java.util.List;

public class NotesFragment extends Fragment {
    private boolean isLandscape;
    private NoteSource note;
    private SocialNetworkAdapter adapter;
    private RecyclerView recyclerView;
    private static final int MY_DEFAULT_DURATION = 1000;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;
    private Note newNote = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        initView(view);
        setHasOptionsMenu(true);
        note = new NoteSourceFirebase().init(noteSource -> adapter.notifyDataSetChanged());
        adapter.setNoteSource(note);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new SocialNetworkAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);
        if (moveToFirstPosition && note.size() > 0){
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }
        adapter.setOnItemClickListener((view0, position) -> {
            FragmentManager fragmentManager = getFragmentManager();
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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_note_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return onItemSelected(item.getItemId()) || super.onContextItemSelected(item);
    }

    private boolean onItemSelected(int itemId) {
        switch (itemId){
            case R.id.action_add:
               navigation.addFragment(NoteAddFragment.newInstance(),true);
               publisher.subscribe(soNewNote -> {
                   note.addNote(soNewNote);
                   adapter.notifyItemInserted(note.size()-1);
                   moveToFirstPosition = true;
               });
               return true;
            case R.id.action_update:
                final int updatedPosition = adapter.getMenuPosition();
                navigation.addFragment(NoteAddFragment.newInstance(note.getNote(updatedPosition)),true);
                publisher.subscribe(updatedNote -> {
                    note.updateNote(updatedPosition,updatedNote);
                    adapter.notifyItemChanged(updatedPosition);
                });
                return true;
            case R.id.action_delete:
                int deletePosition = adapter.getMenuPosition();
                new AlertDialog.Builder(getActivity()).setTitle("Delete for real ?")
                        .setMessage("Do you want to delete this blank?")
                        .setNegativeButton("No", null)
                        .setNeutralButton("Yes", (dialogInterface, i) -> {
                            note.deleteNote(deletePosition);
                            adapter.notifyItemRemoved(deletePosition);
                        })
                        .create()
                        .show();

                return true;
            case R.id.action_clear:
                note.clearNotes();
                adapter.notifyDataSetChanged();
                return true;
        }
        return false;
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
}