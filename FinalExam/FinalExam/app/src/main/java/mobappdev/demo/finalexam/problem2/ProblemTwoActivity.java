package mobappdev.demo.finalexam.problem2;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mobappdev.demo.finalexam.R;

public class ProblemTwoActivity extends AppCompatActivity implements ProblemTwoTopFragment.SendBelowListener, ProblemTwoBottomFragment.SendResponseListener{
    private static final String TAG = "ProblemTwoActivity";

    private ProblemTwoTopFragment mTopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_two);
        mTopFragment = (ProblemTwoTopFragment)getFragmentManager().findFragmentById(R.id.top_fragment);
    }

    public void sendBelow(String message){
        ProblemTwoBottomFragment bottom = ProblemTwoBottomFragment.newInstance(message);
        getFragmentManager().beginTransaction().replace(R.id.fl_bottom, bottom).commit();
    }

    public void sendResponse(String response){
        mTopFragment.updateResponse(response);
    }

}
