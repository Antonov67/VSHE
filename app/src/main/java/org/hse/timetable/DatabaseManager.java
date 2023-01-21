package org.hse.timetable;

import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class DatabaseManager {

    private DatabaseHelper db;
    private static DatabaseManager instance;

    private DatabaseManager(Context context) {
        db = Room.databaseBuilder(context,
                DatabaseHelper.class, DatabaseHelper.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                initData(context);
                            }
                        });
                    }
                })
                .build();
    }

    public static DatabaseManager getInstance(Context context) {
        if (instance == null){
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    public HseDao getHseDao(){
        return db.hseDao();
    }

    private void initData(Context context){
        List<GroupEntity> groups = new ArrayList<>();
        GroupEntity group = new GroupEntity();
        group.id = 1;
        group.name = "Группа-18-1";
        groups.add(group);
        group = new GroupEntity();
        group.id = 2;
        group.name = "Группа-18-2";
        groups.add(group);
        group = new GroupEntity();
        group.id = 3;
        group.name = "Группа-18-3";
        groups.add(group);
        group = new GroupEntity();
        group.id = 4;
        group.name = "Группа-18-4";
        groups.add(group);
        DatabaseManager.getInstance(context).getHseDao().insertGroup(groups);

        List<TeacherEntity> teachers = new ArrayList<>();
        TeacherEntity teacher = new TeacherEntity();
        teacher.id = 1;
        teacher.fio = "Петров Петр Петрович";
        teachers.add(teacher);
        teacher = new TeacherEntity();
        teacher.id = 2;
        teacher.fio = "Степанов Степан Степанович";
        teachers.add(teacher);
        teacher = new TeacherEntity();
        teacher.id = 3;
        teacher.fio = "Сидоров Сидор Сидорович";
        teachers.add(teacher);
        teacher = new TeacherEntity();
        teacher.id = 4;
        teacher.fio = "Иванов Иван Иванович";
        teachers.add(teacher);
        DatabaseManager.getInstance(context).getHseDao().insertTeacher(teachers);

        List<TimeTableEntity> timeTables = new ArrayList<>();

        //2021-02-01

        TimeTableEntity timeTable = new TimeTableEntity();
        timeTable.id = 1;
        timeTable.cabinet = "Кабинет 1";
        timeTable.subGroup = "ПИ";
        timeTable.subjName = "Философия2";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-01 10:00");
        timeTable.timeEnd = dateFromString("2021-02-01 11:30");
        timeTable.groupId = 1;
        timeTable.teacherId = 1;
        timeTables.add(timeTable);

        timeTable = new TimeTableEntity();
        timeTable.id = 2;
        timeTable.cabinet = "Кабинет 2";
        timeTable.subGroup = "ПИ";
        timeTable.subjName = "Мобильная разработка2";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-01 13:00");
        timeTable.timeEnd = dateFromString("2021-02-01 15:00");
        timeTable.groupId = 1;
        timeTable.teacherId = 2;
        timeTables.add(timeTable);


        timeTable = new TimeTableEntity();
        timeTable.id = 3;
        timeTable.cabinet = "Кабинет 3";
        timeTable.subGroup = "ПИ-3";
        timeTable.subjName = "Мобильная разработка3";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-01 16:00");
        timeTable.timeEnd = dateFromString("2021-02-01 18:00");
        timeTable.groupId = 2;
        timeTable.teacherId = 3;
        timeTables.add(timeTable);


        timeTable = new TimeTableEntity();
        timeTable.id = 4;
        timeTable.cabinet = "Кабинет 4";
        timeTable.subGroup = "ПИ-4";
        timeTable.subjName = "Философия4";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-01 16:00");
        timeTable.timeEnd = dateFromString("2021-02-01 18:00");
        timeTable.groupId = 3;
        timeTable.teacherId = 4;
        timeTables.add(timeTable);

        // 2021-02-02

         timeTable = new TimeTableEntity();
        timeTable.id = 5;
        timeTable.cabinet = "Кабинет 1";
        timeTable.subGroup = "ПИ";
        timeTable.subjName = "Философия2";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-02 10:00");
        timeTable.timeEnd = dateFromString("2021-02-02 11:30");
        timeTable.groupId = 1;
        timeTable.teacherId = 1;
        timeTables.add(timeTable);

        timeTable = new TimeTableEntity();
        timeTable.id = 6;
        timeTable.cabinet = "Кабинет 2";
        timeTable.subGroup = "ПИ";
        timeTable.subjName = "Мобильная разработка2";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-02 13:00");
        timeTable.timeEnd = dateFromString("2021-02-02 15:00");
        timeTable.groupId = 1;
        timeTable.teacherId = 2;
        timeTables.add(timeTable);


        timeTable = new TimeTableEntity();
        timeTable.id = 7;
        timeTable.cabinet = "Кабинет 3";
        timeTable.subGroup = "ПИ-3";
        timeTable.subjName = "Мобильная разработка3";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-02 16:00");
        timeTable.timeEnd = dateFromString("2021-02-02 18:00");
        timeTable.groupId = 2;
        timeTable.teacherId = 3;
        timeTables.add(timeTable);


        timeTable = new TimeTableEntity();
        timeTable.id = 8;
        timeTable.cabinet = "Кабинет 4";
        timeTable.subGroup = "ПИ-4";
        timeTable.subjName = "Философия4";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-02 16:00");
        timeTable.timeEnd = dateFromString("2021-02-02 18:00");
        timeTable.groupId = 4;
        timeTable.teacherId = 4;
        timeTables.add(timeTable);


        // 2021-02-04

        timeTable = new TimeTableEntity();
        timeTable.id = 9;
        timeTable.cabinet = "Кабинет 1";
        timeTable.subGroup = "ПИ";
        timeTable.subjName = "Философия2";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-04 10:00");
        timeTable.timeEnd = dateFromString("2021-02-04 11:30");
        timeTable.groupId = 1;
        timeTable.teacherId = 1;
        timeTables.add(timeTable);

        timeTable = new TimeTableEntity();
        timeTable.id = 10;
        timeTable.cabinet = "Кабинет 2";
        timeTable.subGroup = "ПИ";
        timeTable.subjName = "Мобильная разработка2";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-04 13:00");
        timeTable.timeEnd = dateFromString("2021-02-04 15:00");
        timeTable.groupId = 1;
        timeTable.teacherId = 2;
        timeTables.add(timeTable);


        timeTable = new TimeTableEntity();
        timeTable.id = 11;
        timeTable.cabinet = "Кабинет 3";
        timeTable.subGroup = "ПИ-3";
        timeTable.subjName = "Мобильная разработка3";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-04 16:00");
        timeTable.timeEnd = dateFromString("2021-02-04 18:00");
        timeTable.groupId = 2;
        timeTable.teacherId = 3;
        timeTables.add(timeTable);


        timeTable = new TimeTableEntity();
        timeTable.id = 12;
        timeTable.cabinet = "Кабинет 4";
        timeTable.subGroup = "ПИ-4";
        timeTable.subjName = "Философия4";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2021-02-04 16:00");
        timeTable.timeEnd = dateFromString("2021-02-04 18:00");
        timeTable.groupId = 3;
        timeTable.teacherId = 4;
        timeTables.add(timeTable);



        DatabaseManager.getInstance(context).getHseDao().insertTimeTable(timeTables);
    }

    private Date dateFromString(String val){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            return simpleDateFormat.parse(val);
        }catch (ParseException e){

        }
        return null;
    }
}
