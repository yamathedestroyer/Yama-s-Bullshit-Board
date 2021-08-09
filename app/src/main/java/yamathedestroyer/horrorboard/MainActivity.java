package yamathedestroyer.horrorboard;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import yamathedestroyer.horrorboard.adapters.CardsListViewAdapter;
import yamathedestroyer.horrorboard.audiostack.MediaPlayFramework;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> audioNames = new ArrayList<>();
    String[] audiolist;

    CardsListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Custom title font
        final TextView titleText = (TextView) findViewById(R.id.titleText);
        Typeface ralewayFont = Typeface.createFromAsset(getAssets(), "fonts/raleway_medium.ttf");
        titleText.setTypeface(ralewayFont);

        datasetInit();

        //set up the recyclerview
        RecyclerView recyclerView = findViewById(R.id.cardsListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardsListViewAdapter(this, audioNames);
        adapter.setClickListener(new CardsListViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MediaPlayFramework mpv = new MediaPlayFramework("audio/" + audiolist[position], MainActivity.this);

                if (mpv.playback() == 0) {
                    Log.e(this.getClass().getName(), "Playback failed");
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void datasetInit(){
        AssetManager am = getAssets();
        try {
            audiolist = am.list("audio");
            String[] parts;

            for (int i = 0; i < audiolist.length; i++) {
                parts = audiolist[i].split("\\.");
                //Get the audio names into a list / first bracket
                audioNames.add(parts[0]);
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
