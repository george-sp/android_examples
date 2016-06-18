package com.codeburrow.file_chooser_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileChooserActivity extends AppCompatActivity {

    private static final String LOG_TAG = FileChooserActivity.class.getSimpleName();

    // Declare a variable for the current directory.
    private File currentDir;
    // The custom file ArrayAdapter
    private FileArrayAdapter mFileArrayAdapter;
    // The ListView
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);

        // Get the ListView from the layout
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected file/folder properties.
                Properties properties = mFileArrayAdapter.getItem(position);

                if (properties.getData().equalsIgnoreCase("folder") || properties.getData().equalsIgnoreCase("parent directory")) {
                    onFolderSelected(properties);
                } else {
                    onFileSelected(properties);
                }
            }
        });

        // Initialize currentDir to the root of the SD card.
        currentDir = new File("/sdcard/");
        // Get the all files/folders of the currentDir.
        fillFromDir(currentDir);
    }

    /**
     * Get all the files and folders from the current directory.
     *
     * @param file The current directory
     */
    private void fillFromDir(File file) {
        // Get an array of all files and folders in the current dir
        File[] dirs = file.listFiles();
        this.setTitle("Current Dir: " + file.getName());

        // Create array list for folders
        ArrayList<Properties> folders = new ArrayList<>();
        // Create array list for folders
        ArrayList<Properties> files = new ArrayList<>();

        // Add files and folders to the appropriate array list.
        try {
            for (File ff : dirs) {
                if (ff.isDirectory())
                    folders.add(new Properties(ff.getName(), "Folder", ff.getAbsolutePath()));
                else {
                    files.add(new Properties(ff.getName(), "File Size: " + ff.length(), ff.getAbsolutePath()));
                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception caught: " + e.getMessage());
        }

        // Sort the array lists alphabetically.
        Collections.sort(folders);
        Collections.sort(files);

        // Add files array list to the folders(array list).
        folders.addAll(files);

        // Assign a new FileArrayAdapter to our adapter.
        mFileArrayAdapter = new FileArrayAdapter(this, folders);
        // Set mFileAdapter to the mListView.
        mListView.setAdapter(mFileArrayAdapter);

        if (!file.getName().equalsIgnoreCase("sdcard"))
            folders.add(0, new Properties("..", "Parent Directory", file.getParent()));
    }

    private void onFolderSelected(Properties properties) {
        currentDir = new File(properties.getPath());
        fillFromDir(currentDir);

        Log.e(LOG_TAG, "Name: " + properties.getName() + ", Data: " + properties.getData() + ", Path: " + properties.getPath());
    }

    private void onFileSelected(Properties properties) {
        Log.e(LOG_TAG, "Name: " + properties.getName() + ", Data: " + properties.getData() + ", Path: " + properties.getPath());

        Toast.makeText(FileChooserActivity.this, "File Clicked: " + properties.getName(), Toast.LENGTH_SHORT).show();
    }

}