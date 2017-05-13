package mobappdev.demo.finalexam.problem1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import mobappdev.demo.finalexam.R;

public class ProblemOneSecondActivity extends AppCompatActivity {
    private static final String TAG = "Prob1SecondActivity";

    private static String KEY_OP1 = "operand_1";
    private static String KEY_OP2 = "operand_2";
    private static String KEY_RESULT = "result";

    private TextView mOp1;
    private TextView mOp2;
    private EditText mSubtrahend;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_problem_one_second);

        mOp1 = (TextView)findViewById(R.id.tv_op1);
        mOp2 = (TextView)findViewById(R.id.tv_op2);
        mSubtrahend = (EditText)findViewById(R.id.et_subtract);
        mResult = (TextView)findViewById(R.id.tv_result);

        Intent intent = getIntent();
        double op1 = intent.getIntExtra(KEY_OP1, 1);
        double op2 = intent.getIntExtra(KEY_OP2, 1);
        double result = Math.pow(op1, op2);

        mOp1.setText(String.valueOf(op1));
        mOp2.setText(String.valueOf(op2));

        mResult.setText(String.valueOf(result));
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

    public void subtractClicked(View view) {
        double result = Double.valueOf(mResult.getText().toString()) - Double.valueOf(mSubtrahend.getText().toString());
        Log.d(TAG, "result = " + result);
        Intent data = new Intent();
        data.putExtra(KEY_RESULT, result);
        setResult(RESULT_OK, data);
        finish();
    }
}
