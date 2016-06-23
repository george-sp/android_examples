package com.codeburrow.sqlite_database_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codeburrow.sqlite_database_example.DatabaseContract.ContactsEntry;

import java.util.ArrayList;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/20/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
/**
 * ContactsDBHelper manages a local database for contacts data.
 * It handles all database CRUD (Create, Read, Update, Delete) operations.
 */
public class ContactsDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = ContactsDBHelper.class.getSimpleName();

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contacts.db";

    public ContactsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // The SQL statement to create the Contacts table.
        final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                ContactsEntry.TABLE_NAME + " (" +
                ContactsEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ContactsEntry.COLUMN_NAME + " TEXT, " +
                ContactsEntry.COLUMN_PHONE_NUMBER + " TEXT" + " );";
        // Execute the SQL statement.
        sqLiteDatabase.execSQL(SQL_CREATE_CONTACTS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table, if it exists.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContactsEntry.TABLE_NAME);
        // Create table again.
        onCreate(sqLiteDatabase);
    }

    /*
     * All CRUD (Create, Read, Update, Delete) Operations.
     */

    /**
     * Insert NEW Record-Contact into the SQLite Database.
     *
     * @param contact The ContactDAO to be inserted.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long createContact(ContactDAO contact) {
        // Create and/or open a database that will be used for reading and writing.
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        // Use ContentValues object to store sets of values
        // that the ContentResolver can process.
        ContentValues contentValues = new ContentValues();
        // Put Contact Name into contentValues.
        contentValues.put(ContactsEntry.COLUMN_NAME, contact.getName());
        // Put Contact Phone Number into contentValues.
        contentValues.put(ContactsEntry.COLUMN_PHONE_NUMBER, contact.getPhoneNumber());
        // Insert Row-Contact into SQLiteDatabase.
        long insertResult = sqLiteDatabase.insert(ContactsEntry.TABLE_NAME, null, contentValues);
        // Close SQLiteDatabase connection.
        sqLiteDatabase.close();
        // Return the result of the query.
        return insertResult;
    }

    /**
     * Read SINGLE Record-Contact from the SQLiteDatabase.
     *
     * @param id The ID of the requested contact.
     * @return A Contact Data Access Object.
     */
    public ContactDAO readContact(int id) {
        // Instantiate an empty ContactDAO.
        ContactDAO contact = new ContactDAO();
        // Create and/or open a database.
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // Query the given table, returning a Cursor over the result set.
        Cursor cursor = sqLiteDatabase.query(
                ContactsEntry.TABLE_NAME,
                new String[]{ContactsEntry.COLUMN_ID, ContactsEntry.COLUMN_NAME, ContactsEntry.COLUMN_PHONE_NUMBER},
                ContactsEntry.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            // Move the cursor to the first row.
            cursor.moveToFirst();
            // Get contact attributes.
            int contactId = Integer.parseInt(cursor.getString(0));
            String contactName = cursor.getString(1);
            String contactPhoneNumber = cursor.getString(2);
            // Create a ContactDAO object.
            contact = new ContactDAO(contactId, contactName, contactPhoneNumber);
        }
        // Closes the Cursor, releasing all of its resources and making it completely invalid.
        cursor.close();
        // Close SQLiteDatabase connection.
        sqLiteDatabase.close();
        // Return the queried Contact.
        return contact;
    }

    /**
     * Update SINGLE Record-Contact from the SQLiteDatabase.
     *
     * @param contact The ContactDAO to be updated.
     * @return The number of rows affected.
     */
    public int updateContact(ContactDAO contact) {
        return 0;
    }

    /**
     * Delete SINGLE Record-Contact from the SQLiteDatabase.
     * To remove all rows and get a count pass "1" as the whereClause.
     *
     * @param contact
     * @return The number of rows affected if a whereClause is passed in, 0 otherwise.
     */
    public int deleteContact(ContactDAO contact) {
        return 0;
    }

    /**
     * Read ALL Records-Contacts from the SQLiteDatabase.
     *
     * @return A List with the ContactDAO objects.
     */
    public ArrayList<ContactDAO> readAllContacts() {
        // Instantiate an empty ArrayList to store the ContactDAO objects.
        ArrayList<ContactDAO> contactsList = new ArrayList<>();
        // Query the SQLiteDatabase with a 'SELECT ALL' statement.
        String selectAllQuery = "SELECT * FROM " + ContactsEntry.TABLE_NAME;
        // Create and/or open a database.
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // Runs the provided SQL and returns a Cursor over the result set.
        Cursor cursor = sqLiteDatabase.rawQuery(selectAllQuery, null);
        // Move the cursor to the first row.
        if (cursor.moveToFirst()) {
            // Loop through all cursor's rows.
            do {
                // Add contact to the ArrayList.
                contactsList.add(
                        new ContactDAO(
                                Integer.parseInt(cursor.getString(0)),
                                cursor.getString(1),
                                cursor.getString(2)
                        )
                );
            } while (cursor.moveToNext());
        }
        // Closes the Cursor, releasing all of its resources and making it completely invalid.
        cursor.close();
        // Close SQLiteDatabase connection.
        sqLiteDatabase.close();
        // Return the ArrayList with the ContactsList.
        return contactsList;
    }

    /**
     * Count the Records-Contacts of the SQLiteDatabase.
     *
     * @return The number of rows - stored contacts.
     */
    public int countContacts() {
        // Query the SQLiteDatabase with a 'SELECT ALL' statement.
        String countQuery = "SELECT * FROM " + ContactsEntry.TABLE_NAME;
        // Create and/or open a database.
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        // Runs the provided SQL and returns a Cursor over the result set.
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        // Get the number of contacts in the SQLiteDatabase.
        int numberOfContacts = cursor.getCount();
        // Closes the Cursor, releasing all of its resources and making it completely invalid.
        cursor.close();
        // Close SQLiteDatabase connection.
        sqLiteDatabase.close();
        // Return the number of contacts.
        return numberOfContacts;
    }
}
