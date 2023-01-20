package org.hse.timetable;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

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

}
