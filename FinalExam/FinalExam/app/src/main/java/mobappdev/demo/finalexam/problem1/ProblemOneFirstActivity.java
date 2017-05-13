package mobappdev.demo.finalexam.problem1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mobappdev.demo.finalexam.R;

public class ProblemOneFirstActivity extends AppCompatActivity {
    private static final String TAG = "Prob1FirstActivity";

    private static String KEY_OP1 = "operand_1";
    private static String KEY_OP2 = "operand_2";
    private static String KEY_RESULT = "result";

    private EditText mOp1;
    private EditText mOp2;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_problem_one_first);

        mOp1 = (EditText)findViewById(R.id.et_op1);
        mOp2 = (EditText)findViewById(R.id.et_op2);
        mResult = (TextView)findViewById(R.id.tv_result);
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
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    public void onActivityResult(int req, int resp, Intent data){
        if(req == 1){
            if(resp == RESULT_OK){
                mResult.setText(String.valueOf(data.getDoubleExtra(KEY_RESULT, 0)));
            }
        }
    }

    public void powClicked(View view) {
        Intent intent = new Intent(this, ProblemOneSecondActivity.class);
        intent.putExtra(KEY_OP1, Integer.valueOf(mOp1.getText().toString()));
        intent.putExtra(KEY_OP2, Integer.valueOf(mOp2.getText().toString()));
        startActivityForResult(intent, 1);
    }
}
