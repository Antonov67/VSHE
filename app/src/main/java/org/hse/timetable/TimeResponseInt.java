package org.hse.timetable;

import androidx.lifecycle.LiveData;

import java.util.Date;

public interface TimeResponseInt {

    LiveData<Date> getTime();
}
