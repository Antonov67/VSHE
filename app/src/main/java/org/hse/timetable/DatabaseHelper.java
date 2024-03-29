package org.hse.timetable;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {GroupEntity.class, TeacherEntity.class,TimeTableEntity.class},
version = 1,
exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "hse_timerable15";

    public abstract HseDao hseDao();
}
