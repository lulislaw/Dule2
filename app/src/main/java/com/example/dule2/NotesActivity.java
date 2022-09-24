package com.example.dule2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.dule2.NotesAdapter;
import com.example.dule2.NotesDatabase;
import com.example.dule2.NotesListener;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesListener {

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;
    public static final int REQUEST_CODE_SELECT_IMAGE = 4;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 5;

    Integer notesViewMode = 1;

    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;
    private ImageView imageAddWebLink;
    ImageButton button_change_notes_view;

    private int noteClickedPosition = -1;
    /*Button BUTTON_TO_SELECTION, BUTTON_TO_MAIN, BUTTON_TO_NEWS, BUTTON_TO_SEARCH;
    Intent INTENT_TO_SELECTION,INTENT_TO_NEWS, INTENT_TO_SEARCH, INTENT_TO_MAIN;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        RelativeLayout[] buttons_menu = new RelativeLayout[4];
        Intent[] intents = new Intent[4];
        buttons_menu[0] = findViewById(R.id.BotNavButton_search);
        buttons_menu[1] = findViewById(R.id.BotNavButton_news);
        buttons_menu[2] = findViewById(R.id.BotNavButton_home);
        buttons_menu[3] = findViewById(R.id.BotNavButton_settings);
        intents[0] = new Intent(this, SearchActivity.class);
        intents[1] = new Intent(this, NewsActivity.class);
        intents[2] = new Intent(this, HomeActivity.class);
        intents[3] = new Intent(this, SettingsActivity.class);


        EditText inputSearch = findViewById(R.id.inputSearch);

        button_change_notes_view = findViewById(R.id.button_change_notes_view);



        //notesAdapter.pushNotes();



        for(int i = 0;i<4;i++){
            int finalI = i;
            buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputSearch.clearFocus();
                    intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityIfNeeded(intents[finalI], 0);


                    /*try{

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        startActivityIfNeeded(intents[finalI], 0);
                        overridePendingTransition(0,0);
                    } catch (Exception e) {
                        startActivity(intents[finalI]);
                        overridePendingTransition(0, 0);
                        e.printStackTrace();
                    }*/
                }
            });
        }

        /*ImageView imageBackFromNotes = findViewById(R.id.imageBackFromNotes);
        imageBackFromNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        /*imageAddWebLink = findViewById(R.id.imageAddWebLink);
        imageAddWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NotesActivity.this, "Мне лень прикручивать бесполезную функцию ;)", Toast.LENGTH_SHORT).show();
            }
        });*/

        button_change_notes_view.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                if (notesViewMode == 2) {
                    notesViewMode = 1;
                    button_change_notes_view.setImageResource(R.drawable.ic_dashboard);
                } else {
                    notesViewMode = 2;
                    button_change_notes_view.setImageResource(R.drawable.ic_table_rows);
                }
                notesRecyclerView.setLayoutManager(
                        new StaggeredGridLayoutManager(notesViewMode, StaggeredGridLayoutManager.VERTICAL)
                );
            }
        });


        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(notesViewMode, StaggeredGridLayoutManager.VERTICAL)
        );

        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList, this);
        notesRecyclerView.setAdapter(notesAdapter);

        getNotes(REQUEST_CODE_SHOW_NOTES, false);


        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notesAdapter.canselTimer();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (noteList.size() != 0) {
                    notesAdapter.searchNotes(editable.toString());
                }
            }
        });

        /*findViewById(R.id.imageAddNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(getApplicationContext(), CreateNoteActivity.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });

        findViewById(R.id.imageAddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            NotesActivity.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                } else {
                    selectImage();
                }
            }
        });*/

        //System.out.println(notesAdapter.list);

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(0,0);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        } else finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Пермишн денайд!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver()
                .query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        if (note.getSubtitle().trim().isEmpty())  {
            Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
            intent.putExtra("isViewOrUpdate", true);
            intent.putExtra("note", note);
            startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
        } else {
            Intent intent = new Intent(getApplicationContext(), CreateNoteActivitySecond.class);
            intent.putExtra("isViewOrUpdate", true);
            intent.putExtra("note", note);
            startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
        }
    }

    private void getNotes(final int requestCode, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase
                        .getDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if(requestCode == REQUEST_CODE_SHOW_NOTES) {
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_NOTE) {
                    noteList.add(0, notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                    notesRecyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
                    noteList.remove(noteClickedPosition);
                    if (isNoteDeleted) {
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                    } else {
                        noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                        notesAdapter.notifyItemChanged(noteClickedPosition);
                    }

                }
            }
        }

        new GetNotesTask().execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        } else if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        String selectedImagePath = getPathFromUri(selectedImageUri);
                        Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
                        intent.putExtra("isFromQuickActions", true);
                        intent.putExtra("quickActionType", "image");
                        intent.putExtra("imagePath", selectedImagePath);
                        startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
                    }catch (Exception exception) {
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}


/*
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        {
            RelativeLayout[] buttons_menu = new RelativeLayout[4];
            Intent[] intents = new Intent[4];
            buttons_menu[0] = findViewById(R.id.BotNavButton_search);
            buttons_menu[1] = findViewById(R.id.BotNavButton_news);
            buttons_menu[2] = findViewById(R.id.BotNavButton_home);
            buttons_menu[3] = findViewById(R.id.BotNavButton_settings);
            intents[0] = new Intent(this, SearchActivity.class);
            intents[1] = new Intent(this, NewsActivity.class);
            intents[2] = new Intent(this, HomeActivity.class);
            intents[3] = new Intent(this, SettingsActivity.class);
            for(int i = 0;i<4;i++){
                int finalI = i;
                buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityIfNeeded(intents[finalI], 0);


                        try{

                            intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                            startActivityIfNeeded(intents[finalI], 0);
                            overridePendingTransition(0,0);
                        } catch (Exception e) {
                            startActivity(intents[finalI]);
                            overridePendingTransition(0, 0);
                            e.printStackTrace();
                        }
                    }
                });
            }




        }





    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(0,0);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }*/
