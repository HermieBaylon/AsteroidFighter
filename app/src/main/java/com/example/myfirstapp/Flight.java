/**
 * Represents the flight class
 */
package com.example.myfirstapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.myfirstapp.Display.screenRatioX;
import static com.example.myfirstapp.Display.screenRatioY;

public class Flight {

    int toShoot = 0;
    boolean isGoingUp = false;
    int x, y, width, height, shootCounter = 1;
    Bitmap flight2, shoot;
    private Display display;

    // initializes the image and size for the spaceship,
    Flight (Display display, int screenY, Resources res) {
        this.display = display;

        flight2 = BitmapFactory.decodeResource(res, R.drawable.ufo);

        width = flight2.getWidth();
        height = flight2.getHeight();

        width /= 6;
        height /= 6;

        flight2 = Bitmap.createScaledBitmap(flight2, width, height, false);

        shoot = BitmapFactory.decodeResource(res, R.drawable.ufo);
        shoot = Bitmap.createScaledBitmap(shoot, width, height, false);

        y = screenY / 2;
        x = (int) (64 * screenRatioX);
    }

    // if we want to shoot manually
    Bitmap getFlight () {
        if (toShoot != 0) {
            shootCounter = 1;
            toShoot--;
            display.newBullet();
            return shoot;
        }
        return flight2;
    }

    // if we want to shoot automatically
//    Bitmap getFlight() {
//        display.newBullet();
//        return shoot;
//    }

    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}
