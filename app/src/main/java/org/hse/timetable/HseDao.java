package org.hse.timetable;




import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;
@Dao
public interface HseDao {

   @Query("SELECT * FROM 'group'")
   LiveData<List<GroupEntity>> getAllGroup();

    @Insert
    void insertGroup(List<GroupEntity> data);

    @Delete
    void delete(GroupEntity data);

    @Query("SELECT * FROM 'teacher'")
    LiveData<List<TeacherEntity>> getAllTeacher();

    @Insert
    void insertTeacher(List<TeacherEntity> data);

    @Delete
    void delete(TeacherEntity data);

    @Query("SELECT * FROM 'time_table'")
    LiveData<List<TimeTableEntity>> getAllTimetable();

    @Transaction
    @Query("SELECT * FROM 'time_table'")
    LiveData<List<TimeTableWithTeacherEntity>> getTimetableTeacher();

    @Transaction
    @Query("SELECT * FROM 'time_table' WHERE :date >= time_start AND :date <= time_end")
    LiveData<List<TimeTableWithTeacherEntity>> getTimetableTeacherByDate(Date date);

    @Insert
    void insertTimeTable(List<TimeTableEntity> data);
}
