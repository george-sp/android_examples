package com.codeburrow.file_chooser_example;

import android.app.ListActivity;
import android.os.Bundle;

import java.io.File;

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

    }
}