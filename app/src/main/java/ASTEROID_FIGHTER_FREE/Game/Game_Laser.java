
package ASTEROID_FIGHTER_FREE.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import ASTEROID_FIGHTER_FREE.R;

import static ASTEROID_FIGHTER_FREE.Game.Game_Display.screenRatioX;
import static ASTEROID_FIGHTER_FREE.Game.Game_Display.screenRatioY;

/**
 * the laser of the ship
 */
public class Game_Laser {

    int x, y, width, height;
    Bitmap bullet;
    //field for how many pixels the bullet travels per update
    int speed = 150;

    public Game_Laser(Resources res) {
        //initializes dimensions of the laser
        bullet = BitmapFactory.decodeResource(res, R.drawable.laser);

        width = bullet.getWidth() / 1;
        height = bullet.getHeight() / 8;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
    Bitmap getBullet() {
        return bullet;
    }
}
