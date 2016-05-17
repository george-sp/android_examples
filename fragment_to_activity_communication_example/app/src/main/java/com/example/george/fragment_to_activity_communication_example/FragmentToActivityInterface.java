package com.example.george.fragment_to_activity_communication_example;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since May/18/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public interface FragmentToActivityInterface {

    void showToast(String msg);

    void communicateToFragmentB();
}