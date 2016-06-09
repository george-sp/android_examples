package com.codeburrow.music_player_example;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/10/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class PlaylistActivity extends ListActivity {

    private static final String LOG_TAG = PlaylistActivity.class.getSimpleName();

    // List with the songs
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        ArrayList<HashMap<String, String>> songsListData = new ArrayList<>();

        SongsManager songsManager = new SongsManager();
        // Get all songs from SD card.
        this.songsList = songsManager.getPlayList();

        // Loop through playlist.
        for (int i = 0; i < songsList.size(); i++) {
            // Create new HashMap.
            HashMap<String, String> song = songsList.get(i);

            // Add HashMap to ArrayList.
            songsListData.add(song);
        }

        // Add menuItems to ListView.
        ListAdapter adapter = new SimpleAdapter(this, songsListData,
                R.layout.playlist_item, new String[]{"songTitle"}, new int[]{
                R.id.songTitle});

        setListAdapter(adapter);

        // Select single ListView item.
        ListView listView = getListView();
        // Listen to song, when list item is clicked.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get list item index.
                int songIndex = position;

                // Start new intent - Start the Player.
                Intent in = new Intent(getApplicationContext(),
                        MusicPlayerActivity.class);
                // Send songIndex to MusicPlayerActivity.
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Finish PlaylistActivity.
                finish();
            }
        });
    }
}