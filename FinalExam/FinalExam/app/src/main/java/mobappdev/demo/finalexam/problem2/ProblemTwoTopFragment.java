package mobappdev.demo.finalexam.problem2;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mobappdev.demo.finalexam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProblemTwoTopFragment extends Fragment {

    public interface SendBelowListener {
        public void sendBelow(String message);
    }

    private static final String TAG = "Prob2TopFragment";

    private SendBelowListener mListener;
    private EditText mMessage;
    private TextView mResponse;

    public ProblemTwoTopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_two_top, container, false);
        Log.d(TAG, "onCreateView called");

        mResponse = (TextView)view.findViewById(R.id.tv_response);

        mMessage = (EditText)view.findViewById(R.id.et_input);
        Button sendBelow = (Button)view.findViewById(R.id.b_send_below);
        sendBelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendBelow(mMessage.getText().toString());
            }
        });
        return view;
    }

    public void updateResponse(String response){
        mResponse.setText(response);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.d(TAG, "onAttach called");

        mListener = (SendBelowListener)getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated called");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "onDestroyView called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d(TAG, "onDetach called");
    }
}
