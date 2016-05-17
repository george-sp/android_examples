package com.example.george.fragment_to_activity_communication_example;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author George Spiridakis <george@codeburrow.com>
 * @since May/18/2016.
 * ===================================================
 * ---------->    http://codeburrow.com    <----------
 * ===================================================
 */
public class TabFragmentB extends Fragment implements View.OnClickListener {

    private FragmentToActivityInterface mCallback;
    private TextView mTextView1;
    private Button btnFtoA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_b, container, false);
        mTextView1 = (TextView) view.findViewById(R.id.textView1);
        btnFtoA = (Button) view.findViewById(R.id.button);
        btnFtoA.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivityInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public void onRefresh() {
        Toast.makeText(getActivity(), "Fragment 2: Refresh called.",
                Toast.LENGTH_SHORT).show();
    }

    public void fragmentCommunication() {
        mTextView1.setText("Hello from Tab Fragment 1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                mCallback.showToast("Hello from Fragment 2");
                break;
        }
    }
}