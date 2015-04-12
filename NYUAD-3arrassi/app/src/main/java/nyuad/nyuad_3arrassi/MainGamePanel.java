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

/**
 * Created by Kong on 4/11/2015.
 */

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    MyAccelerometer accelerometer;
    private MainThread thread;

    private int currentWordIndex = 0;
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

    private CountDownTimer animationTimer = new CountDownTimer(1000, 300) { // adjust the milli seconds here

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

        Log.d(TAG, Integer.toString(wordList.size()));


        Collections.shuffle(wordList);
        while (!(wordList.get(currentWordIndex).getCategory() == 1)) {
            currentWordIndex++;
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


        currentCategory = CategoryActivity.category;
        currentGameTime = 60000;

        wordList.remove(CategoryActivity.wordList);
        wordList.addAll(CategoryActivity.wordList);

        for (int i = 1; i < wordList.size(); i++) {
            String a1 = wordList.get(i).getEnglish();
            String a2 = wordList.get(i-1).getEnglish();
            if (a1.equals(a2)) {
                wordList.remove(a1);
            }
        }

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
                Intent intent = new Intent().setClass(getContext(), CategoryActivity.class);
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
        currentWordIndex = (currentWordIndex +1)%wordList.size();

        animationTimer.start();
        isAnimationDone = false;
        if (currentGameTime < 60000) {
            gameTimer.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void update(){
        if (currentWordIndex > wordList.size() - 1){
            currentWordIndex = 0;
        }
        while (!(wordList.get(currentWordIndex).getCategory() == currentCategory)) {
            currentWordIndex = (currentWordIndex +1)%wordList.size();
        }
        //Log.d(TAG, "Game time shenanigans: " + gameTime);
    }

    private void renderText(Canvas canvas, String text, float centerX, float centerY) {

        Paint textPaint = new Paint();
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setTextSize(250);

        float textWidth = textPaint.measureText(text);
        float xPos = centerX - textWidth/2.0f;

        float textHeight = textPaint.descent() + textPaint.ascent();
        float yPos = centerY - textHeight/2.0f;

        canvas.drawText(text, xPos, yPos, textPaint);
    }

    public void render(Canvas canvas){
        if (!isGameDone) {
            if (isAnimationDone) {
                canvas.drawARGB(255, 6, 194, 255);

                Word currentWord = wordList.get(currentWordIndex);

                // rendering arabic word and english pronunciation
                renderText(canvas, currentWord.getArabic(), canvas.getWidth()/4, canvas.getHeight()/4);
                renderText(canvas, currentWord.getEnglishPronounce(), canvas.getWidth()*3/4, canvas.getHeight()/4);

                // rendering english word and arabic pronunciation
                renderText(canvas, currentWord.getEnglish(), canvas.getWidth()/4, canvas.getHeight()*0.6f);
                renderText(canvas, currentWord.getArabicPronounce(), canvas.getWidth()*3/4, canvas.getHeight()*0.6f);

                Paint textPaint = new Paint();
                textPaint.setARGB(255, 255, 255, 255);
                textPaint.setTextSize(100);
                //Log.d(TAG, countdownTimer);
                int xPos = (int) ((canvas.getWidth() - textPaint.measureText(countdownTimer, 0, countdownTimer.length())) / 2.0f);
                int yPos = (int) ((canvas.getHeight() / 1.2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(countdownTimer, xPos, yPos, textPaint);
            } else if (pass) {
                canvas.drawColor(Color.RED);
                Paint textPaint = new Paint();
                String sampleText = "PASS";
                textPaint.setARGB(255, 255, 255, 255);
                textPaint.setTextSize(250);
                int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleText, 0, sampleText.length())) / 2.0f);
                int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(sampleText, xPos, yPos, textPaint);
            } else if (correct) {
                canvas.drawColor(Color.GREEN);
                Paint textPaint = new Paint();
                String sampleText = "CORRECT";
                textPaint.setARGB(255, 255, 255, 255);
                textPaint.setTextSize(250);
                int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleText, 0, sampleText.length())) / 2.0f);
                int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
                canvas.drawText(sampleText, xPos, yPos, textPaint);
            }
        }
    }

}