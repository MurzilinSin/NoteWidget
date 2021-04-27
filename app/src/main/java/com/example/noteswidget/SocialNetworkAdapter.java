package com.example.noteswidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteswidget.model.Note;
import com.example.noteswidget.model.NoteSource;
import com.example.noteswidget.model.NoteSourceImpl;

import java.util.List;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {

    private NoteSource noteSource;
    private Context context;
    private OnItemClickListener listener;
    private final Fragment fragment;
    private int menuPosition;

    public SocialNetworkAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setNoteSource(NoteSource noteSource){
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.fragment_content_note, parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note noteData = noteSource.getNote(position);
        holder.title.setText(noteData.getTitle());
        holder.content.setText(noteData.getContent());
        holder.date.setText(noteData.getDate());
    }

    public int getItemCount() {
        return noteSource == null ? 0 : noteSource.size();
    }

    public int getMenuPosition(){
        return menuPosition;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.listener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, date, content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            content = itemView.findViewById(R.id.content);
            registerContextMenu(itemView);
            itemView.setOnClickListener(v -> {
                if(listener != null){
                    listener.onItemClick(v, getAdapterPosition());
                }
            });
        }
        private void registerContextMenu(View itemView) {
            if(fragment!= null){
                itemView.setOnLongClickListener(v -> {

                    menuPosition = getLayoutPosition();
                    System.out.println(noteSource.getNote(menuPosition));
                    return false;
                });
            }
            fragment.registerForContextMenu(itemView);
        }
    }




}
