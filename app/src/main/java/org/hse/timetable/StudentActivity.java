package org.hse.timetable;

import static android.content.ContentValues.TAG;


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
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

//    @Override
//    protected void initTime ()
//    {
//        currentTime = new Date();
//        Locale locale = new Locale("ru");
//        SimpleDateFormat simpleDate = new SimpleDateFormat("HH:mm", locale);
//        time.setText(simpleDate.format(currentTime));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        enumeration(course,year,groupNumber);
        time = findViewById(R.id.id_tv_time_Student);

        //

        mainViewModel = new ViewModelProvider( this).get(MainViewModel.class);
        //mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        spinner = findViewById(R.id.spinnerGroup);


        List<Group> groups = new ArrayList<>();
       // initGroupList(groups, mock);
        initGroupList(groups);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        initTime();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            //@Override
            public void onItemSelected(AdapterView<?> parent, View itemSelected,
                                       int selectedItemPosition, long selectedId)
            {
                Object item = adapter.getItem(selectedItemPosition);
                Log.d(TAG,"item: " + item);


                Log.d(TAG, "itemSelected " + item);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                try {
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
    }



//    private void initGroupList(List<StudentActivity.Group> groups, List<StudentActivity.Group> list)
//    {
//        for(StudentActivity.Group group: list)
//        {
//            groups.add(new StudentActivity.Group(group.getId(), group.getName()));
//        }
//    }


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

//    @Override
//    protected void initTime()
 //   {
//          super.initTime();
//        currentTime = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, EEEE", Locale.forLanguageTag("ru"));
//        java.util.TimeZone tz = TimeZone.getTimeZone(("GMT+3")); //часовой пояс Москвы
//        simpleDateFormat.setTimeZone(tz);
//        String[] dateFormatSplit = simpleDateFormat.format(currentTime).split(" ");
//        String timeText = "Сегодня: " + dateFormatSplit[0] + " " + dateFormatSplit[1].substring(0,1).toUpperCase() + dateFormatSplit[1].substring(1);
//        time.setText(timeText);
  //  }

//    private  void initData()
//    {
//        status.setText("Нет пар");
//
//        subject.setText("Дисциплина");
//        cabinet.setText("Кабинет");
//        corp.setText("Корпус");
//        teacher.setText("Преподаватель");
//    }

    private void initData(){
        initDataFromTimeTable(null);
    }

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
      //  super.showTime(dateTime);

        Log.d(TAG,"groupNameOrTeacher:" + groupNameOrTeacher[0]);

        mainViewModel.getGroupByName(groupNameOrTeacher[0]).observe(StudentActivity.this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(List<GroupEntity> list) {
                for (GroupEntity groupEntity: list) {
                    groupId = groupEntity.id;
                    Log.d(TAG,"groupId: " + groupId);
                }

                Log.d(TAG, "inGroupID: " + groupId);



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