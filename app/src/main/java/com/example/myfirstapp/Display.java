/**
 * This class represents the display of the game
 */
package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Display extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Asteroid asteroid;
    private Random random;
    private List<Bullet> bullets;
    private Flight flight;
    private Activity activity;
    private Background background1, background2;

    // initializes fields
    public Display(Activity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        flight = new Flight(this, screenY, getResources());
        bullets = new ArrayList<>();
        background2.x = screenX;
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
        asteroid = new Asteroid(getResources());
        random = new Random();
}

    // summary method
    @Override
    public void run() {
        while (isPlaying) {
            update ();
            draw ();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // allows movements of the ship, background, bullet, and asteroids
    private void update () {
        background1.x -= 10 * screenRatioX;
        background2.x -= 10 * screenRatioX;
        if (background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }
        if (background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }

        flight.y = (int) (screenY / 2) - 100;

        List<Bullet> trash = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRatioX;
            if (Rect.intersects(asteroid.getCollisionShape(),
                    bullet.getCollisionShape())) {
                score++;
                asteroid.x = -500;
                bullet.x = screenX + 500;
                asteroid.wasShot = true;
            }
        }
        for (Bullet bullet : trash)
            bullets.remove(bullet);
        // actions when ship is hit
        asteroid.x -= asteroid.speed;
            if (asteroid.x + asteroid.width < 0) {
                if (!asteroid.wasShot) {
                    isGameOver = true;
                    return;
                }
                int bound = (int) (30 * screenRatioX);
                asteroid.speed = random.nextInt(bound);
                if (asteroid.speed < 10 * screenRatioX)
                    asteroid.speed = (int) (10 * screenRatioX);
                asteroid.x = screenX;
                asteroid.y = (screenY - asteroid.height) / 2;
                asteroid.wasShot = false;
            }
            if (Rect.intersects(asteroid.getCollisionShape(), flight.getCollisionShape())) {
                isGameOver = true;
                return;
            }
    }

    // allow for asteroid, bullet, background visibility
    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
            canvas.drawBitmap(asteroid.getAsteroid(), asteroid.x, asteroid.y, paint);
            canvas.drawText(score + "", screenX / 2f, 164, paint);
            if (isGameOver) {
                isPlaying = false;
                goBack();
                return;
            }
            canvas.drawBitmap(flight.getFlight(), flight.x, flight.y, paint);
            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    // goes back to main menu
    private void goBack() {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void donePlaying() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // allows movement for the ship
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < screenX / 2) {
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                if (event.getX() > screenX / 2)
                    flight.toShoot++;
                break;
        }
        return true;
    }

    // produces bullets
    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);
    }
}