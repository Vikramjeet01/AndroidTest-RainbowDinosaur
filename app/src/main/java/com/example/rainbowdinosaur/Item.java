package com.example.rainbowdinosaur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Item {

    // PROPERTIES:
    // Image
    // Hitbox
    private Bitmap image;
    //private Bitmap imageP;
    ///private Bitmap imageR;
    private Rect hitbox;

    private int xPosition;
    private int yPosition;

    public Item(Context context, int x, int y) {
        // 1. set up the initial position of the Enemy
        this.xPosition = x;
        this.yPosition = y;

        // 2. Set the default image - all enemies have same image
        this.image = BitmapFactory.decodeResource(context.getResources(), R.drawable.candy32);
        //this.imageP = BitmapFactory.decodeResource(context.getResources(), R.drawable.poop64);
        //this.imageR = BitmapFactory.decodeResource(context.getResources(), R.drawable.rainbow64);

        // 3. Set the default hitbox - all enemies have same hitbox
        this.hitbox = new Rect(
                this.xPosition,
                this.yPosition,
                this.xPosition + this.image.getWidth(),
                this.yPosition + this.image.getHeight()
        );

        //this.hitbox = new Rect(
         //       this.xPosition,
          //      this.yPosition,
          //      this.xPosition + this.imageP.getWidth(),
          //      this.yPosition + this.imageP.getHeight()
        //);

        //this.hitbox = new Rect(
        //        this.xPosition,
        //        this.yPosition,
         //       this.xPosition + this.imageR.getWidth(),
         //       this.yPosition + this.imageR.getHeight()
        //);
    }

    // Getter and setters
    // Autogenerate this by doing Right Click --> Generate --> Getter&Setter

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }



    public Rect getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rect hitbox) {
        this.hitbox = hitbox;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
