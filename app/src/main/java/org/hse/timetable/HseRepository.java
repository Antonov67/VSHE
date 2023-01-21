package org.hse.timetable;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

public class HseRepository {
    private DatabaseManager databaseManager;
    private HseDao dao;

    public HseRepository(Context context){
        databaseManager = DatabaseManager.getInstance(context);
        dao = databaseManager.getHseDao();
    }

    public LiveData<List<GroupEntity>> getGroups(){
        return dao.getAllGroup();
    }

    public LiveData<List<TeacherEntity>> getTeachers(){
        return dao.getAllTeacher();
    }

    public LiveData<List<TimeTableWithTeacherEntity>> getTimeTableTeacherByDate(Date date, int group){
        return dao.getTimetableTeacherByDateAndGroupID(date, group);
    }


    //поиск id группы по названию группы
    public LiveData<List<GroupEntity>> getGroupByName(String groupName){
        return dao.getGroupByName(groupName);
    };

    //поиск id учителя по его fio
    public LiveData<List<TeacherEntity>> getTeacherByFIO(String teacherFIO){
        return dao.getTeacherByFIO(teacherFIO);
    };

    //поиск по дате и id учителя
    public LiveData<List<TimeTableWithTeacherEntity>> getTimetableTeacherByDateAndTeacherID(Date date, int teacherId){
        return dao.getTimetableTeacherByDateAndTeacherID(date, teacherId);
    };

    //расписание на текущий период для группы по id
    public LiveData<List<TimeTableWithTeacherEntity>> getTimeTableGroupOnPeriod(Date date1, Date date2, int group){
        return dao.getTimeTableGroupOnPeriod(date1,date2,group);
    };

    //расписание на текущий период для учителя по id
    public LiveData<List<TimeTableWithTeacherEntity>> getTimeTableTeacherOnPeriod(Date date1, Date date2, int teacherId){
        return dao.getTimeTableTeacherOnPeriod(date1, date2, teacherId);
    };

}
