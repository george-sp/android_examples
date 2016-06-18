package com.codeburrow.file_chooser_example;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/18/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
/**
 * This class holds the properties of the files.
 */
public class Properties implements Comparable<Properties> {

    private static final String LOG_TAG = Properties.class.getSimpleName();

    // File Properties
    private String name;
    private String data;
    private String path;

    public Properties(String name, String data, String path) {
        this.name = name;
        this.data = data;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int compareTo(Properties properties) {
        return this.name.toLowerCase().compareTo(properties.getName().toLowerCase());
    }
}
