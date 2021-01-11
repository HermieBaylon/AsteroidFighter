package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;

/**
 * THIS CLASS CONTAINS THE MAIN GAME ACTIVITY
 * INCLUDING THE DISPLAY AND MATH PROBLEMS
 */
public class Activity_Game extends AppCompatActivity {

    /**
     * field for display of the game which updates and draws the game state
     */
    private Game_Display gameDisplay;

    /**
     * field that stores and manages audio for game activity
     */
    public static Audio_Activity_Game myAudio;

    // FIELDS REGARDING MATH PROBLEMS IN THE GAME
    /**
     * field for MathProblems class - contains all the logic behind the math problems
     */
    private Game_MathProblems myMP;

    /**
     * field for the equation to be completed at the top of the screen
     */
    private String myQuestion;

    /**
     * the solution to the equation
     */
    private int myAns;

    /**
     * the list of all the solutions (including wrong) that can be selected for the math problem
     */
    private ArrayList<Integer> myChoices;

    /**
     * field that stores whether the boss has spawned or not
     */
    private boolean isBossStage = false;

    //BUTTONS FOR MATH PROBLEMS
    private Button myButton1;
    private Button myButton2;
    private Button myButton3;
    private Button myButton4;

    /**
     * onCreate is called when this activity is started/made
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //gets rid of notification bar for phone
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        //initializes MediaPlayer devices for audio
        myAudio = new Audio_Activity_Game(this);

        //initializes the display for the game and starts running it
        gameDisplay = new Game_Display(this, point.x +5000, point.y, myAudio);
        //puts the display on the screen
        setContentView(gameDisplay);

        //start the game music
        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.BGM_GAME_LOOP);

        //Layout on top of surface view
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View secondLayerView = LayoutInflater.from(this).inflate(R.layout.activity_game, null, false);
        addContentView(secondLayerView, lp);

        //For math problem class
        //starts with easy questions
        setQuestionAnswerOnDisplay();

        //releases the MediaPlayers from the main menu to free up resources
        Audio_Activity_Menu_Main.releasePlayers();

     /*   ImageView muteButton;
        muteButton = findViewById(R.id.mute_button);
        muteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


            }
        });
*/
    }

    /**
     * displays the questions and answers on the surface view
     */
    public void setQuestionAnswerOnDisplay(){
        //Initial question to be displayed
        myMP = new Game_MathProblems(isBossStage);

        if(!gameDisplay.isBossStage){
            myQuestion = myMP.getEasyQuestions();
            myChoices = new ArrayList<>(myMP.getEasyAnswers());
        }
        //else means its boss stage
        else{
            myQuestion = myMP.getHardQuestions();
            myChoices = new ArrayList<>(myMP.getHardAnswers());
        }
        myAns = myMP.getAnswer();
        initializeQuestionAnswer();
    }

    /**
     * initializes the buttons and the question
     */
    public void initializeQuestionAnswer(){
        TextView queText = findViewById(R.id.id_question);
        queText.setText(myQuestion);

        Collections.shuffle(myChoices);

        myButton1 = findViewById(R.id.ans_button1);
        myButton1.setText(myChoices.get(0) + "");

        myButton2 = findViewById(R.id.ans_button2);
        myButton2.setText(myChoices.get(1) + "");

        myButton3 = findViewById(R.id.ans_button3);
        myButton3.setText(myChoices.get(2) + "");

        myButton4 = findViewById(R.id.ans_button4);
        myButton4.setText(myChoices.get(3) + "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameDisplay.donePlaying();
        myAudio.stopMedia(Audio_Activity_Game.MEDIA_PLAYERS.BGM_GAME_LOOP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameDisplay.resume();
        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.BGM_GAME_LOOP);
    }

    @Override
    protected void onStop(){
        super.onStop();
        gameDisplay.donePlaying();
        myAudio.stopMedia(Audio_Activity_Game.MEDIA_PLAYERS.BGM_GAME_LOOP);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onStart(){
        super.onStart();
        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.BGM_GAME_LOOP);
    }

    /**
     * shows play again screen
     */
    public void gameDonePlayAgain() {
        myAudio.stopMedia(Audio_Activity_Game.MEDIA_PLAYERS.BGM_BOSS);

        Intent intent = new Intent(this, Activity_Game_Over.class);
        Bundle bundle = new Bundle();
        String points;
        points = Integer.toString(gameDisplay.theScore);
        bundle.putString("Score",points);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


    public void onClick(View view) {

            //determines if the question is correct and executes methods accordingly
            //i.e. if question is right, shoot and play correct sound and go to next question
            //if question is wrong, deduct point and play wrong answer sound
        ImageView muteButton;
        muteButton = findViewById(R.id.mute_button);


        switch (view.getId()){

                case R.id.mute_button:
                    if(myAudio.isMuted()){
                        myAudio.unmute();
                        muteButton.setImageResource(R.drawable.unmuted);

                    }
                    else if(myAudio.isMuted() == false){
                        myAudio.mute();
                        muteButton.setImageResource(R.drawable.muted);


                    }

                case R.id.ans_button1:
                    if(myButton1.getText().equals(myAns + "")){
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_CORRECT);
                        gameDisplay.getFlightObj().hasShot = true;
                        setQuestionAnswerOnDisplay();
                    }else{
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_INCORRECT);
                        gameDisplay.deductPoint();
                        break;
                    }

                    break;

                case R.id.ans_button2:
                    if(myButton2.getText().equals(myAns+"")){
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_CORRECT);
                        gameDisplay.getFlightObj().hasShot = true;
                        setQuestionAnswerOnDisplay();
                    }else{
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_INCORRECT);
                        gameDisplay.deductPoint();
                        break;
                    }
                    break;

                case R.id.ans_button3:
                    if(myButton3.getText().equals(myAns+"")){
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_CORRECT);
                        gameDisplay.getFlightObj().hasShot = true;
                        setQuestionAnswerOnDisplay();
                    }else{
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_INCORRECT);
                        gameDisplay.deductPoint();
                        break;
                    }
                    break;

                case R.id.ans_button4:
                    if(myButton4.getText().equals(myAns+"")){
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_CORRECT);
                        gameDisplay.getFlightObj().hasShot = true;
                        setQuestionAnswerOnDisplay();
                    }else{
                        myAudio.playMedia(Audio_Activity_Game.MEDIA_PLAYERS.SFX_PROBLEM_INCORRECT);
                        gameDisplay.deductPoint();
                        break;
                    }
                    break;

                default:
                    try
                    {
                        throw new Exception();
                    }

                    catch(Exception e)
                    {
                        System.out.println(e.getStackTrace() + "\nInvalid button press");
                    }
                    break;
            }
    }
}
