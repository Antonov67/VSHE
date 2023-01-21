package org.hse.timetable;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TimeResponseFromServer implements TimeResponseInt{

    private final static String TAG = "BaseActivity";
    public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";

    private OkHttpClient client = new OkHttpClient();
    Date dateTime;



    @Override
    public LiveData<Date> getTime() {
        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            public void onResponse (Call call, Response response) throws IOException {
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


                    dateTime = simpleDateFormat.parse(currentTimeVal);
                    Log.d(TAG, dateTime + "");


                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }

            public void onFailure(Call call, IOException e){
                Log.e(TAG, "getTime", e);
            }
        });

        Log.d(TAG, "DateFromServer: " + dateTime);

        LiveData<Date> liveData = new LiveData<Date>() {
            @Nullable
            @Override
            public Date getValue() {
                return dateTime;
            }
        };

       return liveData;

    }

}
