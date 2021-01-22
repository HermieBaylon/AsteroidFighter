package ASTEROID_FIGHTER_FREE.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ASTEROID_FIGHTER_FREE.Audio.Audio_Activity_Game_Over;
import ASTEROID_FIGHTER_FREE.Audio.Audio_Activity_Game_Victory;
import ASTEROID_FIGHTER_FREE.Audio.Audio_Master_Control;
import ASTEROID_FIGHTER_FREE.Database.UserContract.UserEntry;
import ASTEROID_FIGHTER_FREE.R;

/**
 * class that shows game victory screen at end of play
 */
public class Activity_Game_Victory extends Activity implements View.OnClickListener {

    String userName;
    int userHighScore = Activity_Game.HIGH_SCORE;

    TextView gameVictoryTitle;
    TextView gameVictoryText;
    TextView score;
    TextView askUserName;
    EditText userNameEdit;

    Audio_Activity_Game_Victory myAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myAudio = new Audio_Activity_Game_Victory(this);
        Audio_Master_Control.checkMuteStatus(myAudio);
        myAudio.startMedia(Audio_Activity_Game_Victory.MEDIA_PLAYERS.BGM_VICTORY_LOOP);
        //puts game over screen on the screen
        setContentView(R.layout.activity_game_victory);

        //gets rid of notification bar on top of phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.victory_animation);

        final TextView victoryName = findViewById(R.id.gameVictoryMessage);
        victoryName.setSelected(true);

        victoryName.startAnimation(animation);

        //initialize text based on xml
        gameVictoryTitle = findViewById(R.id.gameVictoryTitile);
        gameVictoryText = findViewById(R.id.gameVictoryMessage);
        score = findViewById(R.id.scoreLabel);
        askUserName = findViewById(R.id.askUserName);
        userNameEdit = findViewById(R.id.userName);

        final Button okButton = findViewById(R.id.okButton);

        //Extract the data…
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("Score");
        score.setText(text);

        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;
        Intent j;

        i = new Intent(this, Activity_Menu_Main.class);
        j = new Intent(this, Activity_High_Scores.class);

        switch (view.getId()) {
            case R.id.playAgainButton:
                finish();
                startActivity(i);
                break;

            case R.id.okButton:
                try {
                    myAudio.releasePlayers();
                    saveScore();
                    finish();
                    startActivity(j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.myWebSearch_bt:
                Uri uri = Uri.parse("https://www.tutordudes.com/intern"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
        }
    }

    private void saveScore(){
        userName = userNameEdit.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_USER_NAME, userName);
        values.put(UserEntry.COLUMN_HIGH_SCORE, userHighScore);

        // for the future use
        values.put(UserEntry.COLUMN_LAST_GAME_SCORE, userHighScore);

        // user details
        Uri newUri = getContentResolver().insert(UserEntry.CONTENT_URI, values);

        // validation
        if (newUri != null) {
            // success
            Toast.makeText(getApplicationContext(), "Thanks, " + userNameEdit.getText().toString() + "!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


    //called when application stops
    @Override
    protected void onPause() {
        super.onPause();
        myAudio.pauseLoopers();
    }
    //called when application starts/resumes
    @Override
    protected void onResume() {
        super.onResume();
        myAudio.resumeLoopers();
    }

    //called when application stops
    @Override
    protected void onStop(){
        super.onStop();
    }

    //i think this is called when display turns back on.
    //if you press the power button and turn the emulator off and press again to start
    //it calls this method. not sure if power button on emulator just turns off screen or turns
    //off emulator
    @Override
    protected void onRestart(){
        super.onRestart();
        myAudio.releasePlayers();
        myAudio = new Audio_Activity_Game_Victory(this);
    }
    //called when application starts/resumes
    @Override
    protected void onStart(){
        super.onStart();

    }
}