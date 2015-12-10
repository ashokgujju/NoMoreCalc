package com.ashokgujju.nomorecalc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private int difficultyLevel;
    private int levelMaxValue;
    private int levelMinValue;

    private final int LEVEL_EASY = 0;
    private final int LEVEL_MEDIUM = 1;
    private final int LEVEL_HARD = 2;

    private final int ADDITION = 0;
    private final int SUBSTRACTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        random = new Random();

        updateLevelInfo();
        nextQ();
    }

    private void updateLevelInfo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        difficultyLevel = preferences.getInt(getString(R.string.pref_level), LEVEL_MEDIUM);
        updateLevelRange();
    }

    private void updateLevelRange() {
        switch (difficultyLevel) {
            case LEVEL_EASY:
                levelMinValue = 0;
                levelMaxValue = 10;
                break;
            case LEVEL_MEDIUM:
                levelMinValue = 10;
                levelMaxValue = 100;
                break;
            case LEVEL_HARD:
                levelMinValue = 100;
                levelMaxValue = 1000;
                break;
        }
    }

    private void nextQ() {
        int num1 = random.nextInt(levelMaxValue - levelMinValue) + levelMinValue;
        int num2 = random.nextInt(levelMaxValue - levelMinValue) + levelMinValue;

        switch (getOperation()) {
            case ADDITION:
                answer = num1 + num2;
                mQuestion.setText(num1 + " + " + num2 + " = ?");
                break;
            case SUBSTRACTION:
                answer = Math.abs(num1 - num2);
                mQuestion.setText("|" + num1 + " - " + num2 + "|" + " = ?");
                break;
        }
    }

    private int getOperation() {
        return random.nextInt(2);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.difficulty:
                chooseDifficultyLevel();
                break;
        }
        return true;
    }

    private void chooseDifficultyLevel() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_choose_difficulty);
        builder.setSingleChoiceItems(R.array.levels, difficultyLevel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int level) {
                savePreference(level);
                updateLevelInfo();
                dialog.dismiss();
                nextQ();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void savePreference(int level) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putInt(getString(R.string.pref_level), level).commit();
    }
}