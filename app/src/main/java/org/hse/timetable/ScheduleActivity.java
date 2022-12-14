package org.hse.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {

    public final static String ARG_ID = "ID";
    public final static String ARG_TYPE = "TYPE";
    public final static String ARG_MODE = "MODE";
    public static final String ARG_DATE = "DATE";
    private static final int DEFAULT_ID = 1;
    RecyclerView recyclerView;
    ItemAdapter adapter;
    Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        BaseActivity.ScheduleType type = (BaseActivity.ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        BaseActivity.ScheduleMode mode = (BaseActivity.ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        date = (Date) getIntent().getSerializableExtra(ARG_DATE);
        int id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);

        TextView title = findViewById(R.id.main_title);
        title.setText(mode + " №" + id + " on " + type);



        recyclerView = findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        adapter = new ItemAdapter(this::onSheduleItemClick);
        recyclerView.setAdapter(adapter);



        initData(type);
    }

    private void initData(BaseActivity.ScheduleType type) {
        List<ScheduleItem> list = new ArrayList<>();

        ScheduleItemHeader header = new ScheduleItemHeader();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM", Locale.forLanguageTag("ru"));
        header.setTitle(simpleDateFormat.format(date));

        list.add(header);

        if (type == BaseActivity.ScheduleType.DAY){

            ScheduleItem item = new ScheduleItem();
            item.setStart("10:00");
            item.setEnd("11:00");
            item.setType("Практическое задание");
            item.setName("Анализ данных (анг)");
            item.setPlace("Ауд. 503, Кончовский пр-д, д.3");
            item.setTeacher("Пред. Гущим Михаил Иванович");
            list.add(item);

            item = new ScheduleItem();
            item.setStart("12:00");
            item.setEnd("13:00");
            item.setType("Практическое задание");
            item.setName("Анализ данных (анг)");
            item.setPlace("Ауд. 503, Кончовский пр-д, д.3");
            item.setTeacher("Пред. Гущим Михаил Иванович");
            list.add(item);
        }else {

            for (int i=1; i<=7; i++){
                ScheduleItem item = new ScheduleItem();
                item.setStart("10:00");
                item.setEnd("11:00");
                item.setType("Практическое задание");
                item.setName("Анализ данных (анг)");
                item.setPlace("Ауд. 503, Кончовский пр-д, д.3");
                item.setTeacher("Пред. Гущим Михаил Иванович");
                list.add(item);

                ScheduleItemHeader header2 = new ScheduleItemHeader();
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEEE, dd MMMM", Locale.forLanguageTag("ru"));
                header2.setTitle(simpleDateFormat2.format(date));

                Date dateNext = (Date) date.clone();

                Log.d("777",date.toString());
                Calendar c = Calendar.getInstance();
                c.setTime(dateNext);
                c.add(Calendar.DATE, i);
                dateNext = c.getTime();
                Log.d("777",date.toString());
                header2.setTitle(simpleDateFormat2.format(dateNext));
                list.add(header2);

            }




        }



        adapter.setDataList(list);

    }

    private void onSheduleItemClick(ScheduleItem scheduleItem) {
    }

    public final static class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final static int TYPE_ITEM = 0;
        private final static int TYPE_HEADER = 1;

        private List<ScheduleItem> dataList = new ArrayList<>();
        private OnItemClick onItemClick;

        public ItemAdapter(OnItemClick onItemClick) {
            this.onItemClick = onItemClick;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);


            if (viewType == TYPE_ITEM){

               View contactView = inflater.inflate(R.layout.item_schedule, parent,false);

               return new ViewHolder(contactView, context, onItemClick);
            } else if (viewType == TYPE_HEADER){

                View contactView = inflater.inflate(R.layout.item_schedule_header, parent,false);

                return new ViewHolderHeader(contactView, context, onItemClick);

            }
            throw new IllegalArgumentException("Invalid view type");

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ScheduleItem data = dataList.get(position);
            if (holder instanceof ViewHolder){
                ((ViewHolder)holder).bind(data);
            }else if (holder instanceof ViewHolderHeader){
                ((ViewHolderHeader) holder).bind((ScheduleItemHeader) data);
            }

        }

        public int getItemViewType(int position){
            ScheduleItem data = dataList.get(position);
            if (data instanceof ScheduleItemHeader){
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void setDataList(List<ScheduleItem> list) {

            dataList.clear();
            dataList.addAll(list);

        }
    }

    public static class ViewHolderHeader extends RecyclerView.ViewHolder {

        private Context context;
        private OnItemClick onItemClick;
        private TextView title;

        public ViewHolderHeader(@NonNull View itemView, Context context, OnItemClick onItemClick) {
            super(itemView);
            this.context = context;
            this.onItemClick = onItemClick;
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(final ScheduleItemHeader data) {
            title.setText(data.getTitle());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private Context context;
        private OnItemClick onItemClick;
        private TextView start;
        private TextView end;
        private TextView type;
        private TextView name;
        private TextView place;
        private TextView teacher;


        public ViewHolder(@NonNull View itemView, Context context, OnItemClick onItemClick) {
            super(itemView);
            this.context = context;
            this.onItemClick = onItemClick;
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            place = itemView.findViewById(R.id.place);
            teacher = itemView.findViewById(R.id.teacher);


        }

        public void bind(final ScheduleItem data){
            start.setText(data.getStart());
            end.setText(data.getEnd());
            type.setText(data.getType());
            name.setText(data.getName());
            place.setText(data.getPlace());
            teacher.setText(data.getTeacher());
        }
    }
}