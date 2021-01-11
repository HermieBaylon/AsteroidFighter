package com.example.myfirstapp;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * class that manages all the game audio
 */
public class Audio_Activity_Game {

    /**
     * for determining which activity this plays under
     */
    private Context myContext;

    /**
     * list of all MediaPlayers in this class
     */
    public static MediaPlayer[] myActivityPlayers;

    /**
     * initializes all the MediaPlayers
    // * @param theContext activity to play audio in
     */

    private boolean muted = false;

    public void mute(){
        for(MediaPlayer player : Audio_Activity_Game.myActivityPlayers)
        {
            player.setVolume(0, 0);
        }
        muted = true;

    }

    public void unmute(){
        for(MediaPlayer player : Audio_Activity_Game.myActivityPlayers)
        {
            player.setVolume((float).5, (float).5);
        }
        muted = false;

    }

    public boolean isMuted(){
    return muted;
    }



    public Audio_Activity_Game(Context theContext)
    {
        //set looping for the media that needs to loop (background music)
        myContext = theContext;
        MediaPlayer BGM_BOSS = MediaPlayer.create(myContext, R.raw.bgm_boss_loop);
        BGM_BOSS.setLooping(true);
        MediaPlayer BGM_GAME_LOOP = MediaPlayer.create(myContext, R.raw.bgm_game_loop);
        BGM_GAME_LOOP.setLooping(true);

        //initialize array of media players
        myActivityPlayers = new MediaPlayer[]{
                BGM_BOSS,
                BGM_GAME_LOOP,
                MediaPlayer.create(myContext, R.raw.sfx_boss_hit),
                MediaPlayer.create(myContext, R.raw.sfx_explosion_asteroid),
                MediaPlayer.create(myContext, R.raw.sfx_explosion_boss),
                MediaPlayer.create(myContext, R.raw.sfx_level_victory),
                MediaPlayer.create(myContext, R.raw.sfx_problem_correct),
                MediaPlayer.create(myContext, R.raw.sfx_problem_incorrect),
                MediaPlayer.create(myContext, R.raw.sfx_rocket_hit),
                MediaPlayer.create(myContext, R.raw.sfx_rocket_laser),
                MediaPlayer.create(myContext, R.raw.sfx_rocket_lost_all_lives),
                MediaPlayer.create(myContext, R.raw.sfx_rocket_lost_life)
        };
    }

    //enumeration of mediaplayers; to easier access mediaplayers from the array
    public enum MEDIA_PLAYERS
    {
        BGM_BOSS,
        BGM_GAME_LOOP,
        SFX_BOSS_HIT,
        SFX_EXPLOSION_ASTEROID,
        SFX_EXPLOSION_BOSS,
        SFX_LEVEL_VICTORY,
        SFX_PROBLEM_CORRECT,
        SFX_PROBLEM_INCORRECT,
        SFX_ROCKET_HIT,
        SFX_ROCKET_LASER,
        SFX_ROCKET_LOST_ALL_LIVES,
        SFX_ROCKET_LOST_LIFE
    }

    //plays the corresponding media player that is inputted into th method
    public void playMedia(MEDIA_PLAYERS thePlayer)
    {
        switch (thePlayer)
        {
            case BGM_BOSS:
                myActivityPlayers[MEDIA_PLAYERS.BGM_BOSS.ordinal()].start();
                break;
            case BGM_GAME_LOOP:
                myActivityPlayers[MEDIA_PLAYERS.BGM_GAME_LOOP.ordinal()].start();
                break;
            case SFX_BOSS_HIT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_BOSS_HIT.ordinal()].start();
                break;
            case SFX_EXPLOSION_ASTEROID:
                myActivityPlayers[MEDIA_PLAYERS.SFX_EXPLOSION_ASTEROID.ordinal()].start();
                break;
            case SFX_EXPLOSION_BOSS:
                myActivityPlayers[MEDIA_PLAYERS.SFX_EXPLOSION_BOSS.ordinal()].start();
                break;
            case SFX_LEVEL_VICTORY:
                myActivityPlayers[MEDIA_PLAYERS.SFX_LEVEL_VICTORY.ordinal()].start();
                break;
            case SFX_PROBLEM_CORRECT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_PROBLEM_CORRECT.ordinal()].start();
                break;
            case SFX_PROBLEM_INCORRECT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_PROBLEM_INCORRECT.ordinal()].start();
                break;
            case SFX_ROCKET_HIT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_HIT.ordinal()].start();
                break;
            case SFX_ROCKET_LASER:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_LASER.ordinal()].start();
                break;
            case SFX_ROCKET_LOST_ALL_LIVES:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_LOST_ALL_LIVES.ordinal()].start();
                break;
            case SFX_ROCKET_LOST_LIFE:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_LOST_LIFE.ordinal()].start();
                break;
            default:
                try
                {
                    throw new Exception();
                }

                catch(Exception e)
                {
                    System.out.println(e + "\nNot a valid media player");
                }
                break;

        }
    }

    //stops the corresponding media player that is inputted
    public void stopMedia(MEDIA_PLAYERS thePlayer) {
        switch (thePlayer) {
            case BGM_BOSS:
                myActivityPlayers[MEDIA_PLAYERS.BGM_BOSS.ordinal()].stop();
                break;
            case BGM_GAME_LOOP:
                myActivityPlayers[MEDIA_PLAYERS.BGM_GAME_LOOP.ordinal()].stop();
                break;
            case SFX_BOSS_HIT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_BOSS_HIT.ordinal()].stop();
                break;
            case SFX_EXPLOSION_ASTEROID:
                myActivityPlayers[MEDIA_PLAYERS.SFX_EXPLOSION_ASTEROID.ordinal()].stop();
                break;
            case SFX_EXPLOSION_BOSS:
                myActivityPlayers[MEDIA_PLAYERS.SFX_EXPLOSION_BOSS.ordinal()].stop();
                break;
            case SFX_LEVEL_VICTORY:
                myActivityPlayers[MEDIA_PLAYERS.SFX_LEVEL_VICTORY.ordinal()].stop();
                break;
            case SFX_PROBLEM_CORRECT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_PROBLEM_CORRECT.ordinal()].stop();
                break;
            case SFX_PROBLEM_INCORRECT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_PROBLEM_INCORRECT.ordinal()].stop();
                break;
            case SFX_ROCKET_HIT:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_HIT.ordinal()].stop();
                break;
            case SFX_ROCKET_LASER:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_LASER.ordinal()].stop();
                break;
            case SFX_ROCKET_LOST_ALL_LIVES:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_LOST_ALL_LIVES.ordinal()].stop();
                break;
            case SFX_ROCKET_LOST_LIFE:
                myActivityPlayers[MEDIA_PLAYERS.SFX_ROCKET_LOST_LIFE.ordinal()].stop();
                break;
            default:
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println(e + "\nNot a valid media player");
                }
                break;

        }
    }

    //releases the resources associated with all the players
    public static void releasePlayers()
    {
        for(MediaPlayer player : Audio_Activity_Game.myActivityPlayers)
        {
            if(player!=null) {
                if(player.isPlaying())
                    player.stop();
                player.reset();
                player.release();
                player=null;
            }
        }
    }

}
