package org.hse.timetable;

import androidx.lifecycle.LiveData;

import java.util.Date;

//интерфейс для получения времени от сервера

public interface TimeResponseInt {

    LiveData<Date> getTime();
}
