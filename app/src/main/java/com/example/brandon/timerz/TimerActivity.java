package com.example.brandon.timerz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import TimerFiles.*;

/*
 * This class is reponsible for setting the start time and end time of a timer
 * and also writing that information into the respective timer file
 */

public class TimerActivity extends AppCompatActivity {

    private TimersList tList;
    private Timer currentTimer;
    private String currentName, currentDescription;
    private long difference = 0;
    private long hours = 0;
    private long minutes = 0;
    private long seconds = 0;
    private long mili = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Maybe want to pull all of this in a serperate function later? Not sure if onCreate
         * is the best place for it
         */
        int numTimerToLoad;
        Intent intent;

        //Gets the button number that was clicked so we know what timer to load from tList
        intent = this.getIntent();
        numTimerToLoad = intent.getExtras().getInt("timerToLoad");

        //Reads the timers list from file and puts it into tList
        getTList();

        //Retriving name and description from TimersList that was loaded earlier
        currentTimer = tList.timers[numTimerToLoad];
        currentName = currentTimer.getName();
        currentDescription = currentTimer.getDescription();

        //Sets the title and then loads the activity layout
        setTitle(currentName);
        setContentView(R.layout.activity_timer);

        //Sets the description textview at the top of the page
        TextView descriptionView = (TextView) findViewById(R.id.timerPageDescription);
        descriptionView.setText(currentDescription);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTList()
    {
        try{
        FileInputStream fis = this.openFileInput("TimersList");
        ObjectInputStream is = new ObjectInputStream(fis);
        tList = (TimersList) is.readObject();

        is.close();
        fis.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void startTimer(View view)
    {
        long timeInMili = System.currentTimeMillis();
        currentTimer.setStartTime(timeInMili);
        updateTimeView();
    }

    public void endTimer(View view)
    {
        long timeInMili = System.currentTimeMillis();
        currentTimer.setEndTime(timeInMili);
        updateTimeView();
    }

    /*
     * Calculates the time difference and outputs it to screen
     */
    public void updateTimeView()
    {
        String temp;

        difference = currentTimer.getEndTime() - currentTimer.getStartTime();
        hours = difference/(60*60*1000)%24;
        minutes = difference/(60*1000)%60;
        seconds = difference/1000%60;
        mili = difference%100;

        temp = String.valueOf(hours) + ":" + String.valueOf(minutes)+ ":" + String.valueOf(seconds) + "." + String.valueOf(mili);

        TextView descriptionView = (TextView) findViewById(R.id.timerTime);
        descriptionView.setText(temp);

    }
}
