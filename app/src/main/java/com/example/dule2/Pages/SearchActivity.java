package com.example.dule2.Pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dule2.R;
import com.example.dule2.SearchItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    ImageButton categoryopen_button, clearall_button;
    LinearLayout categoryll;
    Spinner[] cat_spinner = new Spinner[5];
    int[] cat_arrays = new int[5];
    String[] tmp_text;
    String check_string = "";
    RecyclerView recyclerView;
    SearchView searchView;
    private final List<SearchItem> SearchItems = new ArrayList<>();
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new SearchItemAdapter(this.SearchItems);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        categoryopen_button = findViewById(R.id.category_button);
        clearall_button = findViewById(R.id.search_clear);
        categoryll = findViewById(R.id.category);
        cat_spinner[0] = findViewById(R.id.time_spinner);
        cat_spinner[1] = findViewById(R.id.type_spinner);
        cat_spinner[2] = findViewById(R.id.week_spinner);
        cat_spinner[3] = findViewById(R.id.day_spinner);
        cat_spinner[4] = findViewById(R.id.course_spinner);
        cat_arrays[0] = R.array.times;
        cat_arrays[1] = R.array.type;
        cat_arrays[2] = R.array.week;
        cat_arrays[3] = R.array.day;
        cat_arrays[4] = R.array.course;

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.RecyclerView);

        searchView.setEnabled(false);
        for (int i = 0; i < cat_spinner.length; i++) {
            ArrayAdapter<CharSequence> adapter_spinner_time = ArrayAdapter.createFromResource(this, cat_arrays[i], android.R.layout.simple_spinner_item);
            adapter_spinner_time.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cat_spinner[i].setAdapter(adapter_spinner_time);
        }




        categoryopen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryll.getVisibility() != View.GONE) {
                    Animation categoryclose = AnimationUtils.loadAnimation(SearchActivity.this, R.anim.category_close);
                    categoryll.startAnimation(categoryclose);
                    categoryll.setVisibility(View.GONE);
                    for(int i = 0;i<5;i++)
                    {
                        cat_spinner[i].setEnabled(false);
                    }
                } else if (categoryll.getVisibility() == View.GONE) {
                    Animation categoryopen = AnimationUtils.loadAnimation(SearchActivity.this, R.anim.category_open);
                    categoryll.setVisibility(View.VISIBLE);
                    categoryll.startAnimation(categoryopen);
                    for(int i = 0;i<5;i++)
                    {
                        cat_spinner[i].setEnabled(true);
                    }

                }
            }
        });




        {
            RelativeLayout[] buttons_menu = new RelativeLayout[4];
            Intent[] intents = new Intent[4];
            buttons_menu[0] = findViewById(R.id.BotNavButton_home);
            buttons_menu[1] = findViewById(R.id.BotNavButton_news);
            buttons_menu[2] = findViewById(R.id.BotNavButton_note);
            buttons_menu[3] = findViewById(R.id.BotNavButton_settings);
            intents[0] = new Intent(this, HomeActivity.class);
            intents[1] = new Intent(this, NewsActivity.class);
            intents[2] = new Intent(this, NotesActivity.class);
            intents[3] = new Intent(this, SettingsActivity.class);
            for (int i = 0; i < 4; i++) {
                int finalI = i;
                buttons_menu[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        intents[finalI].setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityIfNeeded(intents[finalI], 0);

                    }
                });
            }


        }

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        downloadtxt();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchinfo(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });



        for(int i = 0; i < cat_spinner.length; i++) {
            cat_spinner[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        searchinfo(searchView.getQuery().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    clearall_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            for(int i = 0; i < cat_spinner.length; i++)
            {
                cat_spinner[i].setSelection(0);
            }
        }
    });

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(0,0);
        //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void searchinfo(String s)
    {

        if (s != check_string) {
            SearchItems.clear();
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
            recyclerView.setAdapter(adapter);
            check_string = s;

            for(int i = 0; i < cat_spinner.length; i++)
            {
                if(cat_spinner[i].getSelectedItemPosition() != 0)
                    s = s + " " + cat_spinner[i].getSelectedItem().toString();

            }
            if(s.length() >= 3) {
                String[] strings = s.split(" ");
                for (int i = 0; i < tmp_text.length; i++) {
                    int cheker = 0;
                    for (int l = 0; l < strings.length; l++) {
                        if (tmp_text[i].toLowerCase().contains(strings[l].toLowerCase()))
                            cheker++;
                    }
                    if (cheker == strings.length) {
                        SearchItems.add(new SearchItem(tmp_text[i]));
                        adapter.notifyItemInserted(SearchItems.size() - 1);
                    }

                }

            }

        }

    }

    private static final class SearchItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final List<SearchItem> SearchItems;

        public SearchItemAdapter(List<SearchItem> SearchItems) {

            this.SearchItems = SearchItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_item, parent, false)
            ) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            try {
                TextView name = holder.itemView.findViewById(R.id.Name);
                TextView time = holder.itemView.findViewById(R.id.Time);
                TextView type = holder.itemView.findViewById(R.id.Type);
                TextView teacher = holder.itemView.findViewById(R.id.Teacher);
                TextView room = holder.itemView.findViewById(R.id.Room);
                TextView dayofweek = holder.itemView.findViewById(R.id.DayOfWeek);
                TextView course = holder.itemView.findViewById(R.id.Course);
                name.setText(this.SearchItems.get(position).getName());
                time.setText(this.SearchItems.get(position).getTime());
                type.setText(this.SearchItems.get(position).getTypeSubject());
                teacher.setText(this.SearchItems.get(position).getTeacher());
                room.setText(this.SearchItems.get(position).getRoom());
                dayofweek.setText(this.SearchItems.get(position).getDayOfWeek());
                course.setText(this.SearchItems.get(position).getCourse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return this.SearchItems.size();
        }


    }


    private void downloadtxt() {


        final AsyncHttpClient[] client = {new AsyncHttpClient()};
        client[0].get("https://github.com/lulislaw/excelfilesguu/blob/main/search.txt?raw=true", new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Log.d("mylog", "fail");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                        searchView.setEnabled(true);
                try {
                    FileInputStream fin = new FileInputStream(file);
                    FileReader tfr = new FileReader(file);
                    String text = "";
                    char[] buffer = new char[8096];
                    int chars = tfr.read(buffer);
                    while (chars != -1) {
                        text = text + String.valueOf(buffer, 0, chars);
                        chars = tfr.read(buffer);
                    }

                    tmp_text = text.split("X");


                } catch (FileNotFoundException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });


    }


}