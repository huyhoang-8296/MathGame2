package com.huy.mathgame;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    Button btn_start, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_questions, tv_timer, tv_bottommessage;
    ProgressBar prog_timer;

    Game g = new Game();

    int secondRemaining = 30;
    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondRemaining--;
            tv_timer.setText(Integer.toString(secondRemaining) + "sec");
            prog_timer.setProgress(30 - secondRemaining);
        }
        @Override
        public void onFinish() {
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);
            tv_bottommessage.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);
                }
            }, 4000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);

        tv_score = findViewById(R.id.tv_score);
        tv_bottommessage = findViewById(R.id.tv_bottommessage);
        tv_questions = findViewById((R.id.tv_questions));
        tv_timer = findViewById(R.id.tv_timer);

        prog_timer = findViewById(R.id.prog_timer);
        tv_timer.setText("0 Sec");
        tv_questions.setText("");
        tv_bottommessage.setText("GO!");
        tv_score.setText("0 points");

        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;
                start_button.setVisibility(View.INVISIBLE);
                secondRemaining = 30;
                g = new Game();
                nextTurn();
                timer.start();
            }
        };

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;
                // doesnt work first, so we need to use translate the text to an integer => type Interger.ParseInt, still doesn't work
                // cant use string and character sequence, so we have to go to getText() and convert it toString()
                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
                // Toast.makeText(MainActivity.this, "AnsweredSelected= " + answerSelected, Toast.LENGTH_SHORT).show();
                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()) + "points");
                nextTurn();
            }
        };

        btn_start.setOnClickListener(startButtonClickListener);

        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);
    }

    private void nextTurn(){
        // create a new question
        // set text on answer button
        // enable answer buttons
        g.makeNewQuestion();
        int [] answer = g.getCurrentQuestion().getAnswerArray();

        btn_answer0.setText(Integer.toString(answer[0]));
        btn_answer1.setText(Integer.toString(answer[1]));
        btn_answer2.setText(Integer.toString(answer[2]));
        btn_answer3.setText(Integer.toString(answer[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);

        tv_questions.setText(g.getCurrentQuestion().getQuestionPhrase());
        
        tv_bottommessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
    }
}