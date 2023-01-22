package org.hse.timetable;




import androidx.fragment.app.FragmentActivity;
import android.widget.TextView;



import java.util.Date;



public abstract class BaseActivity extends FragmentActivity {

    enum ScheduleType{
        DAY,
        WEEK
    }

    enum ScheduleMode{
        STUDENT,
        TEACHER
    }


    protected TextView time;
    protected Date currentTime;


    protected abstract void showTime(Date dateTime, String... groupNameOrTeacher);

}