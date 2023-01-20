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
    @Query("SELECT * FROM 'time_table' WHERE group_id = :group AND :date >= time_start AND :date <= time_end")
    LiveData<List<TimeTableWithTeacherEntity>> getTimetableTeacherByDateAndGroupID(Date date, int group);

    //поиск по дате и id учителя
    @Transaction
    @Query("SELECT * FROM 'time_table' WHERE teacher_id = :teacherId AND :date >= time_start AND :date <= time_end")
    LiveData<List<TimeTableWithTeacherEntity>> getTimetableTeacherByDateAndTeacherID(Date date, int teacherId);


    //поиск id группы по названию группы
    @Query("SELECT * FROM 'group' WHERE name = :groupName")
    LiveData<List<GroupEntity>> getGroupByName(String groupName);

    //расписание на текущий день для группы по id и дню
    @Transaction
    @Query("SELECT * FROM 'time_table' WHERE group_id = :group AND CONVERT(:date,GETDATE()) = CONVERT(time_end,GETDATE())")
    LiveData<List<TimeTableWithTeacherEntity>> getTimeTableGroupOnDay(Date date, int group);


    //поиск id группы по fio учителя
    @Query("SELECT * FROM 'teacher' WHERE fio = :teacherFIO")
    LiveData<List<TeacherEntity>> getTeacherByFIO(String teacherFIO);

    @Insert
    void insertTimeTable(List<TimeTableEntity> data);
}
