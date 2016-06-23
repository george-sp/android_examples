package com.codeburrow.sqlite_database_example;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/20/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */

import java.io.Serializable;

/**
 * A class to manipulate single contact as an object.
 *
 * The Data Access Object is basically an object or an interface
 * that provides access to an underlying database or any other persistence storage.
 */
public class ContactDAO implements Serializable{

    private static final String LOG_TAG = ContactDAO.class.getSimpleName();

    /*
     * ContactDAO's variables
     */
    private int id;
    private String name;
    private String phoneNumber;

    /*
     * Empty Constructor
     */
    public ContactDAO() {
    }

    /*
     * Constructor - without id variable
     */
    public ContactDAO(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /*
     * Constructor - all variables
     */
    public ContactDAO(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /*
     * Setters & Getters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
