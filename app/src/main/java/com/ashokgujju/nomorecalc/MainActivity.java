package com.ashokgujju.nomorecalc;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.question)
    TextView mQuestion;
    @Bind(R.id.answer)
    TextView mAnswer;

    private Random random;
    private int answer;
    private Vibrator vibrator;

    private final int ADDITION = 0;
    private final int SUBSTRACTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        random = new Random();

        nextQ();
    }

    private int getOperation() {
        return random.nextInt(2);
    }

    private void nextQ() {
        int num1 = random.nextInt(100);
        int num2 = random.nextInt(100);

        switch (getOperation()) {
            case ADDITION:
                answer = num1 + num2;
                mQuestion.setText(num1 + " + " + num2 + " = ?");
                break;
            case SUBSTRACTION:
                answer = Math.abs(num1 - num2);
                mQuestion.setText("| " + num1 + " - " + num2 + " |" + " = ?");
                break;
        }
    }

    private void setAnswer(char c) {
        mAnswer.setText(mAnswer.getText().toString() + c);
    }

    @OnClick(R.id.key0)
    public void key0() {
        setAnswer('0');
    }

    @OnClick(R.id.key1)
    public void key1() {
        setAnswer('1');
    }

    @OnClick(R.id.key2)
    public void key2() {
        setAnswer('2');
    }

    @OnClick(R.id.key3)
    public void key3() {
        setAnswer('3');
    }

    @OnClick(R.id.key4)
    public void key4() {
        setAnswer('4');
    }

    @OnClick(R.id.key5)
    public void key5() {
        setAnswer('5');
    }

    @OnClick(R.id.key6)
    public void key6() {
        setAnswer('6');
    }

    @OnClick(R.id.key7)
    public void key7() {
        setAnswer('7');
    }

    @OnClick(R.id.key8)
    public void key8() {
        setAnswer('8');
    }

    @OnClick(R.id.key9)
    public void key9() {
        setAnswer('9');
    }

    @OnClick(R.id.keyDel)
    public void keyDel() {
        if (mAnswer.getText().length() != 0) {
            vibrator.vibrate(100);
            mAnswer.setText(null);
        }

    }

    @OnClick(R.id.keySubmit)
    public void keySubmit() {
        try {
            if (Integer.parseInt(mAnswer.getText().toString()) == answer)
                nextQ();
            else {
                vibrator.vibrate(300);
            }
            mAnswer.setText(null);
        } catch (NumberFormatException e) {
        }
    }
}