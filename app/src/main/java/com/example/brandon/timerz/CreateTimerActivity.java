package com.example.brandon.timerz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import TimerFiles.*;

public class CreateTimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creater_timer);
        setTitle("Create New Timer");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creater_timer, menu);
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

    /*
        Called by the create button and creates a new timer
        with the information that the user has input. Also
        creates a new text file with the name and writes
        it to file.
     */

    public void createNewTimer(View view) throws IOException, ClassNotFoundException {
        //Probably want to do some error checking to make sure
        // that the name isn't already taken

        Boolean timerListCreated = checkTimerListFile();
        TimersList tList = new TimersList();

        if(timerListCreated == false)
        {
            createTimersListFile();
        }

        // Opening a file stream to read from TimersList file so we can work
        // with it
        FileInputStream fis = this.openFileInput("TimersList");
        ObjectInputStream is = new ObjectInputStream(fis);
        tList = (TimersList) is.readObject();

        is.close();
        fis.close();

        //Creates a new timer object
        Timer newTimer = new Timer();

        //Some variables needed
        String temp;
        EditText editText;

        //Gets the text that the user input and set it as name
        editText = (EditText) findViewById(R.id.newTimerName);
        temp = editText.getText().toString();
        newTimer.setName(temp);

        //Gets the text that the user input and set it as description
        editText = (EditText) findViewById(R.id.newTimerDescription);
        temp = editText.getText().toString();
        newTimer.setDescription(temp);

        //Now i want to create a text file and write this object to it
        try {
            //Creating and opening a new file and object stream
            FileOutputStream outputStream;
            FileOutputStream fos = this.openFileOutput(newTimer.getName(), Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            //Writes the object to filer named after new timer
            os.writeObject(newTimer);

            //Close both streams
            os.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Add this object to the timers list
        tList.timers[tList.lastEmptySpot] = newTimer;
        tList.lastEmptySpot++;

        //Opens new streams to update TimersListFile
        FileOutputStream fos = openFileOutput("TimersList", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);

        //Writes the object to filer named after new timer
        os.writeObject(tList);

        //Close both streams
        os.close();
        fos.close();

        //Should take you back to home page/main page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    /*
     * Checks to see if the TimersList file exists
     */
    private boolean checkTimerListFile()
    {
        File file = this.getFileStreamPath("TimersList");
        if(file.exists() == false) {
            return false;
        }
        return true;

    }

    /*
     * Gets called if we recieved false from checkTimersListFile so
     * that we can create the file
     */
    private void createTimersListFile() throws IOException {

        FileOutputStream fos = this.openFileOutput("TimersList", Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);

        TimersList list = new TimersList();
        os.writeObject(list);

        os.close();
        fos.close();

    }

}
