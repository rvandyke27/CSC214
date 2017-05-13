package mobappdev.demo.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mobappdev.demo.finalexam.problem1.ProblemOneFirstActivity;
import mobappdev.demo.finalexam.problem2.ProblemTwoActivity;
import mobappdev.demo.finalexam.problem3.AActivity;
import mobappdev.demo.finalexam.problem3.BActivity;
import mobappdev.demo.finalexam.problem4.ImageDownloaderActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void problemOneClicked(View view) {
        Intent problem1 = new Intent(this, ProblemOneFirstActivity.class);
        startActivity(problem1);
    }

    public void problemTwoClicked(View view) {
        Intent problem2 = new Intent(this, ProblemTwoActivity.class);
        startActivity(problem2);
    }

    public void problemThreeAClicked(View view) {
        Intent problem3a = new Intent(this, AActivity.class);
        startActivity(problem3a);
    }

    public void problemThreeBClicked(View view) {
        Intent problem3b = new Intent(this, BActivity.class);
        startActivity(problem3b);
    }

    public void problemFourClicked(View view) {
        Intent problem4 = new Intent(this, ImageDownloaderActivity.class);
        startActivity(problem4);
    }
}
