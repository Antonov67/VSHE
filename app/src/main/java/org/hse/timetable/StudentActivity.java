package org.hse.timetable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentActivity extends BaseActivity{


    private TextView status, subject, cabinet, corp, teacher, time;

    private Spinner spinner;

    protected MainViewModel mainViewModel;

    private int groupId;


    ArrayAdapter adapter;

    String[] course = {"БИ","ПИ","Э","УБ","И","Ю","МБ","РИС","ИЯ"};
    int[] year = {19,20,21,22};
    int[] groupNumber = {1,2,3};
    List<StudentActivity.Group> mock = new ArrayList<>();

    private final static String TAG = "BaseActivity";


    void enumeration (String[] course, int[] year, int[] groupNumber)
    {
        int count = 1;
        for(String s: course)
        {
            for(int i: year)
            {
                for(int j: groupNumber)
                {
                    mock.add(new StudentActivity.Group(count, assembly(s,i,j)));
                    count++;
                }
            }
        }
    }

    String assembly(String course, int year, int groupNumber)
    {
        return course + "-" + year + "-" + groupNumber;
    }




    static class Group
    {
        private Integer id;
        private String name;

        public Group(Integer id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String toString()
        {
            return name;
        }

        public String getName()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        enumeration(course,year,groupNumber);
        time = findViewById(R.id.id_tv_time_Student);

        //viewModel для работы с liveData

        mainViewModel = new ViewModelProvider( this).get(MainViewModel.class);

        spinner = findViewById(R.id.spinnerGroup);


        List<Group> groups = new ArrayList<>();
       // initGroupList(groups, mock);
        initGroupList(groups);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            //@Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId)
            {
                Object item = adapter.getItem(selectedItemPosition);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                try {
                    //передадим дату 1.02.2021 и название группы
                    showTime(simpleDateFormat.parse("2021-02-01 16:00"),String.valueOf(item));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //@Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //
            }
        });

        time = findViewById(R.id.id_tv_time_Student);


        status = findViewById(R.id.textView4);
        subject = findViewById(R.id.textView5);
        cabinet = findViewById(R.id.textView6);
        corp = findViewById(R.id.textView7);
        teacher = findViewById(R.id.textView8);

        initData();

        View button3 = findViewById(R.id.button3);
        View button4 = findViewById(R.id.button4);

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //showStudent();
                toast("Расписание на день");
            };
        });

        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //showTeacher();
                toast("Расписание на неделю");
            };
        });

        View scheduleDay = findViewById(R.id.button3);
        scheduleDay.setOnClickListener(v -> {showSchedule(ScheduleType.DAY);});
        View scheduleWeek = findViewById(R.id.button4);
        scheduleWeek.setOnClickListener(v -> {showSchedule(ScheduleType.WEEK);});

        //получии дату и время от сервера с помощью liveData и MVVM
        mainViewModel.getTime().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                currentTime = date;
                Log.d(TAG, "BaseActivity,DateFromServer: " + date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                //запишем дату в поле вывода в акивити
                time.setText(simpleDateFormat.format(date));
            }
        });
    }

    //с помощью liveData и запроса к бд получим список групп
    private void initGroupList(final List<Group> groups){
        mainViewModel.getGroups().observe(this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(List<GroupEntity> list) {
                List<Group> groupResult = new ArrayList<>();
                for (GroupEntity groupEntity : list){
                    groupResult.add(new Group(groupEntity.id, groupEntity.name));
                }
                adapter.clear();
                adapter.addAll(groupResult);
            }

        });
    }


    private void initData(){
        initDataFromTimeTable(null);
    }

    //метод записи данных из запроса в поля на активити
    private void initDataFromTimeTable(TimeTableWithTeacherEntity timeTableTeacherEntity){
        if (timeTableTeacherEntity == null){
            status.setText("Нет пар");
            subject.setText("Дисциплина");
            cabinet.setText("Кабинет");
            corp.setText("Корпус");
            teacher.setText("Преподаватель");
            return;
        }
        status.setText("Идет пара");
        TimeTableEntity timeTableEntity = timeTableTeacherEntity.timeTableEntity;

        subject.setText(timeTableEntity.subjName);
        cabinet.setText(timeTableEntity.cabinet);
        corp.setText(timeTableEntity.corp);
        teacher.setText(timeTableTeacherEntity.teacherEntity.fio);
    }

    @Override
    protected void showTime(Date dateTime, String... groupNameOrTeacher) {

        //запрос для поиска id группы по ее названию
        mainViewModel.getGroupByName(groupNameOrTeacher[0]).observe(StudentActivity.this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(List<GroupEntity> list) {
                for (GroupEntity groupEntity: list) {
                    groupId = groupEntity.id;
                }
                // вывод данных о занятии группы если оно идет в данныц момент
                mainViewModel.getTimeTableTeacherByDateAndGroupId(dateTime,groupId).observe(StudentActivity.this, new Observer<List<TimeTableWithTeacherEntity>>() {
                    @Override
                    public void onChanged(List<TimeTableWithTeacherEntity> list) {
                        if (list.size() == 0){
                            initDataFromTimeTable(null);
                        }else{
                            for (TimeTableWithTeacherEntity teacherEntity: list) {
                                Log.d(TAG,teacherEntity.timeTableEntity.subjName + " " + teacherEntity.timeTableEntity.groupId);

                                initDataFromTimeTable(teacherEntity);
                            }
                        }

                    }
                });
            }
        });
    }

    private void toast(String text)
    {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }


    private void showSchedule(ScheduleType type){
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Group)) {
            return;
        }
        showScheduleImpl(ScheduleMode.STUDENT, type, (Group) selectedItem, currentTime);
    }

    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, Group group, Date date){
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, group.getName());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            intent.putExtra(ScheduleActivity.ARG_DATE, simpleDateFormat.parse("2021-02-02 16:00"));
        }catch (Exception e){

        }

        startActivity(intent);
    }

}