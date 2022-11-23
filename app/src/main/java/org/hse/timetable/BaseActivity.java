package org.hse.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;



public abstract class BaseActivity extends AppCompatActivity {

    enum ScheduleType{
        DAY,
        WEEK
    }

    enum ScheduleMode{
        STUDENT,
        TEACHER
    }


    private final static String TAG = "BaseActivity";
    public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";


    protected TextView time;
    protected Date currentTime;
    protected Date currentDate;

    private OkHttpClient client = new OkHttpClient();

    protected void getTime(){
        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse (Call call, Response response) throws IOException {
                parseResponse(response);
            }

            public void onFailure(Call call, IOException e){
                Log.e(TAG, "getTime", e);
            }
        });
    }


    protected void initTime(){
        getTime();
    }

    private void showTime(Date dateTime){
        if (dateTime == null) {
            return;
        }
        currentTime = dateTime;
        currentDate = dateTime;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm,EEEE", Locale.forLanguageTag("ru"));
        time.setText(simpleDateFormat.format(currentTime));
    }

    private void parseResponse(Response response){
        Gson gson = new Gson();
        ResponseBody body = response.body();
        try{
            if (body == null){
                return;
            }
            String string = body.string();
            Log.d(TAG, string);
            TimeResponse timeResponse = gson.fromJson(string, TimeResponse.class);
            String currentTimeVal = timeResponse.getTimeZone().getCurrentTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

            Date dateTime = simpleDateFormat.parse(currentTimeVal);

            // run on UI thread
            runOnUiThread(() -> showTime(dateTime));
        } catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }
}