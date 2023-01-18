package org.hse.timetable;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    }
}
