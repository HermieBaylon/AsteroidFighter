package ASTEROID_FIGHTER_FREE.Audio;

import android.content.Context;
import android.media.MediaPlayer;
import ASTEROID_FIGHTER_FREE.R;

public class Audio_Activity_Menu_Credits extends Audio_Abstract_Class{
    public enum MEDIA_PLAYERS
    {
        BGM_CREDITS
    }

    public Audio_Activity_Menu_Credits(Context theContext)
    {
        super(theContext, 1,
                R.raw.bgm_credits_loop);
    }

    @Override
    public MediaPlayer getMediaPlayer(Object thePlayer) {
        return myPlayers[((MEDIA_PLAYERS)thePlayer).ordinal()];
    }

    @Override
    public void startMedia(Object thePlayer) {
        getMediaPlayer(thePlayer).start();
    }

    @Override
    public void stopMedia(Object thePlayer) {
        getMediaPlayer(thePlayer).stop();
    }
}
