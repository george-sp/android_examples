package com.codeburrow.file_chooser_example;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileChooserActivity extends ListActivity {

    private static final String LOG_TAG = FileChooserActivity.class.getSimpleName();

    // Declare a variable for the current directory.
    private File currentDir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Log.e(LOG_TAG, e.getMessage());
        }

        // Sort the array lists alphabetically.
        Collections.sort(folders);
        Collections.sort(files);

        // Add files array list to the folders(array list).
        folders.addAll(files);

        // An array list for debugging.
        ArrayList<String> directoriesNames = new ArrayList<>();
        for (Properties properties : folders) {
            directoriesNames.add(properties.getName());
        }

        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, directoriesNames));

        if (!file.getName().equalsIgnoreCase("sdcard"))
            folders.add(0, new Properties("..", "Parent Directory", file.getParent()));
    }

}