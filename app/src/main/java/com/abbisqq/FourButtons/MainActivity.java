package com.abbisqq.FourButtons;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import layout.StartingFragment;
import layout.gameFragment;
import layout.highScoreFragment;
import layout.settingsFragment;

public class MainActivity extends AppCompatActivity implements StartingFragment.OnStartingFragmentInteractionListener,
        gameFragment.OnGameFragmentInteractionListener, settingsFragment.OnSettingsFragmentInteractionListener,
        highScoreFragment.OnHighscoreFragmentInteractionListener{


    private MediaPlayer mp,start,correct,wrong,bombtick,explode,finalScreenSound;
    private  int maxVolume = 100;
    public boolean gamePlayed=false;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment startingfragment = manager.findFragmentById(R.id.container);
        sharedPreferences = getPreferences(0);

        start = MediaPlayer.create(this,R.raw.startbutton);
        correct = MediaPlayer.create(this,R.raw.correct);
        wrong = MediaPlayer.create(this,R.raw.wrong);
        bombtick = MediaPlayer.create(this,R.raw.bomb);
        explode = MediaPlayer.create(this,R.raw.explode);

        if (startingfragment == null) {
            startingfragment = new StartingFragment();
            manager.beginTransaction().add(R.id.container, startingfragment).commit();
        }
    }
    @Override
    public void OnStartingFragmentInteraction(Uri uri) {

    }


    public void mainsong(int currVolume) {
        int i = sharedPreferences.getInt("music_num", 1);
        if (i == 1) {
            mp = MediaPlayer.create(this, R.raw.arc_north_dusk);
        } else if (i == 2) {
            mp = MediaPlayer.create(this, R.raw.song2);
        } else if (i == 3) {
            mp = MediaPlayer.create(this, R.raw.song3);
        } else if (i == 4) {
            mp = MediaPlayer.create(this, R.raw.song4);
        }
        if (sharedPreferences.getString("muteMusic", "dontMute") == "mute") {
            currVolume=0;
        }

            final float volume = (float) (1 - (Math.log(maxVolume - currVolume) / Math.log(maxVolume)));
            mp.setVolume(volume, volume);
            mp.start();

    }
    public void stopMainsong(){
        try {
            mp.stop();
        }catch (Exception e){

        }

    }

    public void explode(int currVolume){
        if (sharedPreferences.getString("muteButtons", "dontMute") == "mute") {
            currVolume=0;
        }

        final float volume = (float) (1 - (Math.log(maxVolume - currVolume) / Math.log(maxVolume)));
        explode.setVolume(volume, volume);
        explode.start();
    }
    public  void bomb(int currVolume){
        if (sharedPreferences.getString("muteButtons", "dontMute") == "mute") {
            currVolume=0;
        }

        final float volume = (float) (1 - (Math.log(maxVolume - currVolume) / Math.log(maxVolume)));
        bombtick.setVolume(volume, volume);
        bombtick.start();

    }

    public void wrongButton(int currVolume){
        if (sharedPreferences.getString("muteButtons", "dontMute") == "mute") {
            currVolume=0;
        }

        final float volume = (float) (1 - (Math.log(maxVolume - currVolume) / Math.log(maxVolume)));
        wrong.setVolume(volume, volume);
        wrong.start();
    }

    public void correctButton(int currVolume){
        if (sharedPreferences.getString("muteButtons", "dontMute") == "mute") {
            currVolume=0;
        }

        final float volume = (float) (1 - (Math.log(maxVolume - currVolume) / Math.log(maxVolume)));
        correct.setVolume(volume, volume);
        correct.start();
    }

    public void startButton(){
        start.start();
    }

    public void FinalSound(boolean record,int currVolume) {
        if (record) {
            finalScreenSound = MediaPlayer.create(this,R.raw.won);
        }else if(!record){
            finalScreenSound = MediaPlayer.create(this,R.raw.gameover);
        }
        if (sharedPreferences.getString("muteMusic", "dontMute") == "mute") {
            currVolume=0;
        }

        final float volume = (float) (1 - (Math.log(maxVolume - currVolume) / Math.log(maxVolume)));
        finalScreenSound.setVolume(volume, volume);
        finalScreenSound.start();
    }

    public void loadHighScoreScreen(){
        highScoreFragment highScoreFragment =  new highScoreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,highScoreFragment).commit();


   }

    public void loadNewGameScreen(){
        gameFragment gameFragment = new gameFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,gameFragment).addToBackStack(null).commit();
        mainsong(70);
    }
    public void loadSettingsScreen(){
        settingsFragment settingsFragment = new settingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).addToBackStack(null).commit();

    }
    public void loadStaringFragment(){
        stopMainsong();
        StartingFragment startingFragment = new StartingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,startingFragment).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            loadStaringFragment();
    }

    @Override
    public void OnGameFragmentInteraction(Uri uri) {

    }


    @Override
    public void OnSettingsFragmentInteraction(Uri uri) {

    }

    @Override
    public void OnHighscoreFragmentInteraction(Uri uri) {

    }
}
