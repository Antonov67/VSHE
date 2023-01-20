package org.hse.timetable;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private HseRepository repository;

    public MainViewModel(@NonNull Application application){
        super(application);
        repository = new HseRepository(application);
    }

    public LiveData<List<GroupEntity>> getGroups(){
        return repository.getGroups();
    }

    public LiveData<List<TeacherEntity>> getTeachers(){
        return repository.getTeachers();
    }

    public LiveData<List<TimeTableWithTeacherEntity>> getTimeTableTeacherByDateAndGroupId(Date date, int group) {
        return repository.getTimeTableTeacherByDate(date, group);
    }


    //поиск id группы по названию группы
    public LiveData<List<GroupEntity>> getGroupByName(String groupName){
        return repository.getGroupByName(groupName);
    };
}
