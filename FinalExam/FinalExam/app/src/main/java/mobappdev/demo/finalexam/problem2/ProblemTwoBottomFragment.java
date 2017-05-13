package mobappdev.demo.finalexam.problem2;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import mobappdev.demo.finalexam.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProblemTwoBottomFragment extends Fragment {

    public interface SendResponseListener{
        public void sendResponse(String response);
    }

    private static final String TAG = "Prob2BottomFragment";

    private static String ARG_MESSAGE = "message";

    private TextView mMessage;
    private SendResponseListener mListener;

    public ProblemTwoBottomFragment() {
        // Required empty public constructor
    }

    public static ProblemTwoBottomFragment newInstance(String message){
        ProblemTwoBottomFragment frag = new ProblemTwoBottomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problem_two_bottom, container, false);

        mMessage = (TextView)view.findViewById(R.id.tv_message);

        Bundle args = getArguments();
        mMessage.setText(args.getString(ARG_MESSAGE));

        Button toUpper = (Button)view.findViewById(R.id.b_upper);
        toUpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendResponse(mMessage.getText().toString().toUpperCase());
            }
        });

        Button toLower = (Button)view.findViewById(R.id.b_lower);
        toLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendResponse(mMessage.getText().toString().toLowerCase());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mListener = (SendResponseListener)getActivity();
    }

}
