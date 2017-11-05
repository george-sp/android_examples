package com.spyridakis.room_persistence_library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void usersActivity(View view) {
        Intent usersActivityIntent = new Intent(this, UsersActivity.class);
        startActivity(usersActivityIntent);
    }

    public void jankShowUserActivity(View view) {
        Intent jankShowUserIntent = new Intent(this, JankShowUserActivity.class);
        startActivity(jankShowUserIntent);
    }

    public void booksBorrowedByUserActivity(View view) {
        Intent booksBorrowedByUserIntent = new Intent(this, BooksBorrowedByUserActivity.class);
        startActivity(booksBorrowedByUserIntent);
    }

    public void typeConvertersActivity(View view) {
        Intent typeConvertersIntent = new Intent(this, TypeConvertersActivity.class);
        startActivity(typeConvertersIntent);
    }
}
