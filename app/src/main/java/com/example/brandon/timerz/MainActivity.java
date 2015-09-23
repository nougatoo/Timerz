package com.example.brandon.timerz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

import TimerFiles.*;

/*
 * TODO: - Create an activity for the timers when we hit the button the main activity so that functionality can be added to the timers
 * TODO: - Add a "loadtimerslist" function to Mainactivity
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer testTimer = null;
        int [] buttonIDs = { R.id.button0, R.id.button1}; //Change when I add more buttons
        int [] buttonDescriptions = { R.id.button0Description, R.id.button1Description}; //Change when I add more buttons
        TimersList tList = new TimersList();

        File file = this.getFileStreamPath("TimersList");

        //If we even have any buttons to make yet
        if(file.exists() == true)
        {

            try {


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

            int numButtonsToMake = tList.lastEmptySpot;

            //Going to need a variable that counts how many buttons there are (using lastEmtpySpot
            //and run a for loop that will create and show all the buttons that we need
            for(int i = 0;i<numButtonsToMake;i++) {

                try {
                    FileInputStream fis = this.openFileInput(tList.timers[i].getName());
                    ObjectInputStream is = new ObjectInputStream(fis);
                    testTimer = (Timer) is.readObject();
                    is.close();
                    fis.close();
                } catch (ClassNotFoundException e) {

                } catch (OptionalDataException e) {

                } catch (FileNotFoundException e) {

                } catch (StreamCorruptedException e) {
                    //e.printStackTrace();
                } catch (IOException e) {
                    //e.printStackTrace();
                }


                Button resetButton = (Button) findViewById(buttonIDs[i]);
                resetButton.setText(testTimer.getName());
                resetButton.setVisibility(View.VISIBLE);

                TextView description = (TextView) findViewById(buttonDescriptions[i]);
                description.setText(testTimer.getDescription());
                description.setVisibility(View.VISIBLE);

            }

        }
        else
        {

            //Instead of doing this we want to get the timer from the list
            TextView textView = new TextView(this);
            textView.setTextSize(40);
            textView.setText("Not working");
            setContentView(textView);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if (id == R.id.create_timer)
        {
            //We want to create a new timer
            openCreateTimer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Method that gets called when we want to opn the createrTimer activity
    public void openCreateTimer()
    {
        Intent intent = new Intent(this, CreateTimerActivity.class);
        startActivity(intent);
    }

    /*
     * When button 0 on main is pressed this function gets called
     * and will transfer us to the TimerActivity while passing in the name
     * or place of the timer to load
     *
     * Not sure how to get info on which button is pressed if i want all the buttons
     * to call the same function to create TimerActivity
     */
    public void handleButton0Press(View view)
    {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("timerToLoad", 0);
        startActivity(intent);
    }

    public void handleButton1Press(View view)
    {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("timerToLoad", 1);
        startActivity(intent);
    }
}
