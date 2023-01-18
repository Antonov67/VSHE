package org.hse.timetable;


import androidx.room.TypeConverter;

import java.sql.Date;


public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dataToTimestamp(Date date){
        return date == null ? null : new date.getTime();
    }
}
