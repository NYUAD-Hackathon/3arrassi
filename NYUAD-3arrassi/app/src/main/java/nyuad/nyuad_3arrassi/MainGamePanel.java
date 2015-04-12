package nyuad.nyuad_3arrassi;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import android.os.CountDownTimer;

import java.util.Stack;

/**
 * Created by Kong on 4/11/2015.
 */

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    MyAccelerometer accelerometer;
    private MainThread thread;

    private int gameTime = 0;
    private int currentWord = 0;
    private int score = 0;
    private long currentGameTime = 60000;
    private int currentCategory = 1;
    private String countdownTimer = "";

    boolean isGameDone = false;
    boolean isAnimationDone = true;
    boolean pass = false;
    boolean correct = false;
    boolean back = true;
    boolean gameOver = false;

    private ArrayList<Word> wordList = new ArrayList<Word>();

    private CountDownTimer gameTimer;

    private CountDownTimer animationTimer = new CountDownTimer(2000, 300) { // adjust the milli seconds here

        public void onTick(long millisUntilFinished) {
        }

        public void onFinish() {
            isAnimationDone = true;
            gameTimer = new CountDownTimer(currentGameTime, 300) { // adjust the milli seconds here

                public void onTick(long millisUntilFinished) {

                    countdownTimer = ""+String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                    currentGameTime = millisUntilFinished;
                }

                public void onFinish() {
                    countdownTimer = "done!";
                    isGameDone = true;
                    back = false;
                    gameOver = true;
                    endGame();
                }

            }.start();
            pass = false;
            correct = false;
        }
    };


    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);
        accelerometer = new MyAccelerometer(context, this);

        MyDatabase wordDatabase = new MyDatabase(context);
        Cursor cDB = wordDatabase.getWords();

        cDB.moveToFirst();
        while (cDB.isAfterLast() == false) {
            int category = cDB.getInt(1);
            String arabicWord = cDB.getString(2);
            String englishWord = cDB.getString(3);
            String arabicPron = cDB.getString(4);
            String englishPron = cDB.getString(5);

            wordList.add(new Word(category, arabicWord, englishWord, arabicPron, englishPron));
            cDB.moveToNext();
        }

        Log.d(TAG, DatabaseUtils.dumpCursorToString(cDB));
        cDB.close();
        wordDatabase.close();

        wordList.remove(MainActivity.wordList);
        wordList.addAll(MainActivity.wordList);

        Collections.shuffle(wordList);
        while (!(wordList.get(currentWord).getCategory() == 1)) {
            currentWord++;
        }

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        setWillNotDraw(false);
        accelerometer.registerListener();
        gameTimer = new CountDownTimer(currentGameTime, 300) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                countdownTimer = ""+String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                currentGameTime = millisUntilFinished;
            }

            public void onFinish() {
                countdownTimer = "done!";
                isGameDone = true;
                back = false;
                gameOver = true;
                endGame();
            }

        }.start();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        isGameDone = true;

        if(thread !=null){
            thread.interrupt();
            thread = null;
        }
        accelerometer.unregisterListener();

        gameTimer.cancel();
        animationTimer.cancel();

        Log.d(TAG, "Thread was shut down cleanly");
    }

    public void endGame(){
        if (gameOver) {
            Intent intent = new Intent().setClass(getContext(), GameOverActivity.class);
            intent.putExtra("score", Integer.toString(score));
            ((Activity) getContext()).startActivity(intent);
            gameOver = false;
        }
        else {
            if (back) {
                Intent intent = new Intent().setClass(getContext(), MainActivity.class);
                intent.putExtra("score", Integer.toString(score));
                ((Activity) getContext()).startActivity(intent);
                back = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    void pass(){
        pass = true;
        nextWord();
    }

    void correct(){
        correct = true;
        score++;
        nextWord();
    }

    void nextWord(){
        currentWord = (currentWord+1)%wordList.size();
        while (!(wordList.get(currentWord).getCategory() == 1)) {
            currentWord = (currentWord+1)%wordList.size();
        }
        animationTimer.start();
        isAnimationDone = false;
        if (currentGameTime < 10000) {
            gameTimer.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void update(){
        if (currentWord > wordList.size() - 1){
            currentWord = 0;
        }
        //Log.d(TAG, "Game time shenanigans: " + gameTime);
    }

    public void render(Canvas canvas){
        if (!isGameDone) {
            if (isAnimationDone) {
                canvas.drawColor(Color.BLUE);
                Paint textPaint = new Paint();
                textPaint.setTextAlign(Paint.Align.LEFT);
                textPaint.setARGB(200, 255, 255, 255);
                textPaint.setTextSize(300);

                String sampleWord;
                String samplePron;
                sampleWord = wordList.get(currentWord).getArabic();
                samplePron = wordList.get(currentWord).getEnglishPronounce();

                int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleWord, 0, sampleWord.length()) - textPaint.measureText(samplePron, 0, samplePron.length())) / 2.0f);
                int yPos = (int) ((canvas.getHeight() / 1.8) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(sampleWord, xPos, yPos, textPaint);

                xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleWord, 0, sampleWord.length())) / 2.0f);
                yPos = (int) ((canvas.getHeight() / 1.8) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(samplePron, xPos, yPos, textPaint);

                sampleWord = wordList.get(currentWord).getEnglish();
                samplePron = wordList.get(currentWord).getArabicPronounce();

                xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleWord, 0, sampleWord.length()) - textPaint.measureText(samplePron, 0, samplePron.length())) / 2.0f);
                yPos = (int) ((canvas.getHeight() / 3.0) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(sampleWord, xPos, yPos, textPaint);

                xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleWord, 0, sampleWord.length())) / 2.0f);
                yPos = (int) ((canvas.getHeight() / 3.0) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(samplePron, xPos, yPos, textPaint);

                textPaint.setARGB(200, 255, 255, 255);
                textPaint.setTextSize(100);
                //Log.d(TAG, countdownTimer);
                xPos = (int) ((canvas.getWidth() - textPaint.measureText(countdownTimer, 0, countdownTimer.length())) / 2.0f);
                yPos = (int) ((canvas.getHeight() / 1.2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(countdownTimer, xPos, yPos, textPaint);
            } else if (pass) {
                canvas.drawColor(Color.YELLOW);
                Paint textPaint = new Paint();
                String sampleText = "PASS";
                textPaint.setARGB(200, 255, 255, 255);
                textPaint.setTextSize(300);
                int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleText, 0, sampleText.length())) / 2.0f);
                int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(sampleText, xPos, yPos, textPaint);
            } else if (correct) {
                canvas.drawColor(Color.GREEN);
                Paint textPaint = new Paint();
                String sampleText = "CORRECT";
                textPaint.setARGB(200, 255, 255, 255);
                textPaint.setTextSize(300);
                int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleText, 0, sampleText.length())) / 2.0f);
                int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(sampleText, xPos, yPos, textPaint);
            }
        }
    }

}