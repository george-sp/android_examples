package com.codeburrow.sqlite_database_example;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since Jun/20/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
/**
 * A class to manipulate single contact as an object.
 */
public class Contact {

    private static final String LOG_TAG = Contact.class.getSimpleName();

    /*
     * Contact's variables
     */
    private int id;
    private String name;
    private String phoneNumber;

    /*
     * Empty Constructor
     */
    public Contact() {
    }

    /*
     * Constructor - without id variable
     */
    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /*
     * Constructor - all variables
     */
    public Contact(int id, String name, String phoneNumber) {
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
