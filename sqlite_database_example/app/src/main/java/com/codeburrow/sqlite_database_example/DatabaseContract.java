package com.codeburrow.sqlite_database_example;

import android.provider.BaseColumns;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/20/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class DatabaseContract {

    private static final String LOG_TAG = DatabaseContract.class.getSimpleName();

    /**
     * Inner class that defines the table contents of the Contacts table
     */
    public static final class ContactsEntry implements BaseColumns {

        // Contacts Table Name.
        public static final String TABLE_NAME = "Contacts";
        // Column with the ID of the Contacts.
        public static final String COLUMN_ID = "ID";
        // Column with the Name of the Contacts.
        public static final String COLUMN_NAME = "name";
        // Column with the Phone Number of the Contacts.
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
    }
}
