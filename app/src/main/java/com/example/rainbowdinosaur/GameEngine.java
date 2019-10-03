package com.example.rainbowdinosaur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    final static String TAG="DINO-RAINBOWS";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    // represent the TOP LEFT CORNER OF THE GRAPHIC

    int playerXPosition;
    int playerYPosition;
    Bitmap playerImage;
    Rect playerHitbox;

    Bitmap item1Image;
    int item1XPosition;
    int item1YPosition;
    Rect item1Hitbox;

    Bitmap item2Image;
    int item2XPosition;
    int item2YPosition;
    Rect item2Hitbox;

    Bitmap item3Image;
    int item3XPosition;
    int item3YPosition;
    Rect item3Hitbox;

    Bitmap item4Image;
    int item4XPosition;
    int item4YPosition;
    Rect item4Hitbox;

    int line1XPosition;
    int line1YPosition;

    int line2XPosition;
    int line2YPosition;

    int line3XPosition;
    int line3YPosition;

    int line4XPosition;
    int line4YPosition;

    // ----------------------------
    // ## GAME STATS
    // ----------------------------

    int lives = 10;
    int score = 0;


    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.printScreenInfo();

        this.playerImage = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.dino64);
        this.playerXPosition = this.screenWidth - 200;
        this.playerYPosition = 475;

        this.playerHitbox = new Rect(this.screenWidth - 200,
                475,
                this.screenWidth-200+playerImage.getWidth(),
                475+playerImage.getHeight()
        );

        this.item1Image = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.candy64);
        this.item1XPosition =  100;
        this.item1YPosition = 25;
        this.item1Hitbox = new Rect(100,
                25,
                100+playerImage.getWidth(),
                25+playerImage.getHeight()
        );

        this.item2Image = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.rainbow64);
        this.item2XPosition = 100;
        this.item2YPosition = 175;
        this.item2Hitbox = new Rect(100,
                175,
                100+playerImage.getWidth(),
                175+playerImage.getHeight()
        );

        this.item3Image = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.poop64);
        this.item3XPosition = 100;
        this.item3YPosition = 325;
        this.item3Hitbox = new Rect(100,
                325,
                100+playerImage.getWidth(),
                325+playerImage.getHeight()
        );

        this.item4Image = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.rainbow64);
        this.item4XPosition = 100;
        this.item4YPosition = 475;
        this.item4Hitbox = new Rect(100,
                475,
                100+playerImage.getWidth(),
                475+playerImage.getHeight()
        );

        this.line1XPosition = 100;
        this.line1YPosition = 160;

        this.line2XPosition = 100;
        this.line2YPosition = 310;

        this.line3XPosition = 100;
        this.line3YPosition = 460;

        this.line4XPosition = 100;
        this.line4YPosition = 610;

    }



    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void updateHitbox(Rect hitbox, Bitmap image, int xPos, int yPos) {
        hitbox.left  = xPos;
        hitbox.top = yPos;
        hitbox.right  = xPos
                + image.getWidth();
        hitbox.bottom = yPos
                + image.getHeight();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    String personTapped = "";

    public void updatePositions() {

        if (this.personTapped == "up") {
            // if mousedown, then move player up
            // Make the UP movement > than down movement - this will
            // make it look like the player is moving up alot
            this.playerYPosition = this.playerYPosition - 50;

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);
        }

        else if (this.personTapped == "down") {
            // if mousedown, then move player up
            // Make the UP movement > than down movement - this will
            // make it look like the player is moving up alot
            this.playerYPosition = this.playerYPosition + 50;

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);
        }

        if(this.playerYPosition <= 0){

            this.playerXPosition = this.screenWidth - 200;
            this.playerYPosition = 475;

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);

        }

        if(this.playerYPosition+playerImage.getHeight() > this.screenHeight){

            this.playerXPosition = this.screenWidth - 200;
            this.playerYPosition = 25;

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);

        }



        this.item1XPosition = this.item1XPosition + 20;
        // MOVE THE HITBOX (recalcluate the position of the hitbox)
        updateHitbox(item1Hitbox,
                item1Image,
                item1XPosition,
                item1YPosition);

        if(this.item1XPosition >= this.screenWidth){
            this.item1XPosition =  100;
            this.item1YPosition = 25;

            updateHitbox(item1Hitbox,
                    item1Image,
                    item1XPosition,
                    item1YPosition);
        }

        this.item2XPosition = this.item2XPosition + 25;
        // MOVE THE HITBOX (recalcluate the position of the hitbox)
        updateHitbox(item2Hitbox,
                item2Image,
                item2XPosition,
                item2YPosition);

        if(this.item2XPosition >= this.screenWidth){
            this.item2XPosition =  100;
            this.item2YPosition = 175;

            updateHitbox(item2Hitbox,
                    item2Image,
                    item2XPosition,
                    item2YPosition);
        }

        this.item3XPosition = this.item3XPosition + 15;
        // MOVE THE HITBOX (recalcluate the position of the hitbox)
        updateHitbox(item3Hitbox,
                item3Image,
                item3XPosition,
                item3YPosition);

        if(this.item3XPosition >= this.screenWidth){
            this.item3XPosition =  100;
            this.item3YPosition = 325;

            updateHitbox(item3Hitbox,
                    item3Image,
                    item3XPosition,
                    item3YPosition);
        }

        this.item4XPosition = this.item4XPosition + 30;
        // MOVE THE HITBOX (recalcluate the position of the hitbox)
        updateHitbox(item4Hitbox,
                item4Image,
                item4XPosition,
                item4YPosition);

        if(this.item4XPosition >= this.screenWidth){
            this.item4XPosition =  100;
            this.item4YPosition = 475;

            updateHitbox(item4Hitbox,
                    item4Image,
                    item4XPosition,
                    item4YPosition);
        }

        if(this.playerHitbox.intersect(this.item1Hitbox) == true){
            this.item1XPosition = 100;
            this.item1YPosition = 25;

            this.item1Hitbox = new Rect(100,
                    25,
                    100+this.item1Image.getWidth(),
                    25+this.item1Image.getHeight());

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);

            score = score + 1;
        }

        if(this.playerHitbox.intersect(this.item2Hitbox) == true){
            this.item2XPosition = 100;
            this.item2YPosition = 175;

            this.item2Hitbox = new Rect(100,
                    175,
                    100+this.item1Image.getWidth(),
                    175+this.item1Image.getHeight());

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);

            score = score + 2;
        }

        if(this.playerHitbox.intersect(this.item3Hitbox) == true){
            this.item3XPosition = 100;
            this.item3YPosition = 325;

            this.item3Hitbox = new Rect(100,
                    325,
                    100+this.item1Image.getWidth(),
                    325+this.item1Image.getHeight());

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);

            lives = lives - 1;
        }

        if(this.playerHitbox.intersect(this.item4Hitbox) == true){
            this.item4XPosition = 100;
            this.item4YPosition = 475;

            this.item4Hitbox = new Rect(100,
                    475,
                    100+this.item1Image.getWidth(),
                    475+this.item1Image.getHeight());

            updateHitbox(playerHitbox,
                    playerImage,
                    playerXPosition,
                    playerYPosition);

            score = score + 2;
        }
    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);

            paintbrush.setColor(Color.GREEN);
            this.canvas.drawRect(this.line1XPosition,
                    this.line1YPosition,
                    this.line1XPosition + 800,     // 400 is width of racket
                    this.line1YPosition + 5,    // 50 is height of racket
                    paintbrush);

            this.canvas.drawRect(this.line2XPosition,
                    this.line2YPosition,
                    this.line2XPosition + 800,     // 400 is width of racket
                    this.line2YPosition + 5,    // 50 is height of racket
                    paintbrush);

            this.canvas.drawRect(this.line3XPosition,
                    this.line3YPosition,
                    this.line3XPosition + 800,     // 400 is width of racket
                    this.line3YPosition + 5,    // 50 is height of racket
                    paintbrush);

            this.canvas.drawRect(this.line4XPosition,
                    this.line4YPosition,
                    this.line4XPosition + 800,     // 400 is width of racket
                    this.line4YPosition + 5,    // 50 is height of racket
                    paintbrush);


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

            canvas.drawBitmap(playerImage, playerXPosition, playerYPosition, paintbrush);
            // draw the player's hitbox
            canvas.drawRect(this.playerHitbox, paintbrush);

            canvas.drawBitmap(item1Image, item1XPosition, item1YPosition, paintbrush);
            // draw the player's hitbox
            canvas.drawRect(this.item1Hitbox, paintbrush);

            canvas.drawBitmap(item2Image, item2XPosition, item2YPosition, paintbrush);
            // draw the player's hitbox
            canvas.drawRect(this.item2Hitbox, paintbrush);

            canvas.drawBitmap(item3Image, item3XPosition, item3YPosition, paintbrush);
            // draw the player's hitbox
            canvas.drawRect(this.item3Hitbox, paintbrush);

            canvas.drawBitmap(item4Image, item4XPosition, item4YPosition, paintbrush);
            // draw the player's hitbox
            canvas.drawRect(this.item4Hitbox, paintbrush);

            paintbrush.setColor(Color.RED);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(2);
            paintbrush.setTextSize(30);
            canvas.drawText("Lives remaining: " + lives,
                    700,
                    20,
                    paintbrush
            );

            canvas.drawText("SCORE: " + score, 500, 20, paintbrush);



            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {

            float fingerXPosition = event.getX();
            float fingerYPosition = event.getY();
            Log.d(TAG, "Person's pressed: "
                    + fingerXPosition + ","
                    + fingerYPosition);


            // 2. Compare position of tap to middle of screen
            int middleOfScreen = this.screenHeight / 2;
            if (fingerYPosition <= middleOfScreen) {
                // 3. If tap is on left, racket should go left
                personTapped = "up";
            }
            else if (fingerYPosition > middleOfScreen) {
                // 4. If tap is on right, racket should go right
                personTapped = "down";
            }

        }
        else if (userAction == MotionEvent.ACTION_UP) {

        }

        return true;
    }
}
