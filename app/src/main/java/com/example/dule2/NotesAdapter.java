package com.example.dule2;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dule2.Note;
import com.example.dule2.NotesListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public ArrayList<String> list;

    private List<Note> notes;
    private NotesListener notesListener;
    private Timer timer;
    private List<Note> notesSource;

    public NotesAdapter(List<Note> notes, NotesListener notesListener) {
        this.notes = notes;
        this.notesListener = notesListener;
        notesSource = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setNote(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesListener.onNoteClicked(notes.get(position), position);
            }
        });
        holder.layoutNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(view.getContext(), "LongClicked", Toast.LENGTH_SHORT).show();

                
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textSubtitle, textDataTime;
        LinearLayout layoutNote;
        RoundedImageView imageNote;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDataTime = itemView.findViewById(R.id.textDateTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.imageNote);
        }
        @SuppressLint("ResourceAsColor")
        void setNote(Note note) {
            textTitle.setTextColor(Color.parseColor("#000000"));
            textDataTime.setTextColor(Color.parseColor("#333333"));
            textSubtitle.setTextColor(Color.parseColor("#333333"));

            textTitle.setText(note.getTitle());
            if(note.getNoteText().trim().isEmpty() && note.getSubtitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            } else if (!note.getSubtitle().trim().isEmpty()) {
                textSubtitle.setText(note.getSubtitle());
                textSubtitle.setTextColor(Color.parseColor("#89CFF0"));
            } else
                textSubtitle.setText(note.getNoteText());

            textDataTime.setText(note.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(R.color.colorDefaultNoteColor);
            }

            if (note.getImagePath() != null) {
                imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            } else {
                imageNote.setVisibility(View.GONE);
            }
        }
    }

    public void searchNotes(final String searchKeyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyword.trim().isEmpty()) {
                    notes = notesSource;
                } else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : notesSource) {
                        if (note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                            || note.getSubtitle().toLowerCase().contains(searchKeyword.toLowerCase())
                            || note.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            temp.add(note);

                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }

    public void canselTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void pushNotes () {
        for (Note note : notesSource) {
            list.add(String.valueOf(note));
        }
        System.out.println(list);
    }

}