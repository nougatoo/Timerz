package TimerFiles;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Brandon on 20/09/2015.
 *
 * This class does not have to worry about setting the time itself.
 * Timer Activity will do that when it the correct buttons get clicked
 */
public class Timer implements Serializable{



    private String name = "Not working";
    private String description;
    private long startTime = -1;
    private long endTime = -1;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


}
