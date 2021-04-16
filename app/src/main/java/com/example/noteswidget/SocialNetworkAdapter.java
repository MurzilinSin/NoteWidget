package com.example.noteswidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteswidget.model.Note;

import java.util.List;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.ViewHolder> {

    private List<Note> dataSource;
    private Context context;
    private OnItemClickListener listener;

    public SocialNetworkAdapter(List<Note> dataSource) {
        this.dataSource = dataSource;
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
        Note noteData = dataSource.get(position);

        holder.title.setText(noteData.getTitle());
        holder.content.setText(noteData.getContent());
        holder.date.setText(noteData.getDate());
    }

    public int getItemCount() {
        return dataSource == null ? 0 : dataSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemClick(v,getAdapterPosition());
                    }
                }
            });
        }
    }
}
