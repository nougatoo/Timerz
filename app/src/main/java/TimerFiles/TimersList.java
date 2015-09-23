package TimerFiles;

import java.io.Serializable;

/**
 * Created by Brandon on 20/09/2015.
 *
 * TODO: Add a max number of timers to be created. For now i i just know the max
 */
public class TimersList implements Serializable{

    public Timer timers[] = new Timer[50];
    public int lastEmptySpot = 0;
    public boolean fileCreated = false; //Change when we know the file is created

    public TimersList()
    {
        for(int i = 0;i<50;i++)
        {
            timers[i] = new Timer();
        }
    }

    public void setLastEmptySpot(int num)
    {
        lastEmptySpot = num;
    }

    public int getLastEmptySpot()
    {
        return lastEmptySpot;
    }

    public void getList()
    {

    }


}
