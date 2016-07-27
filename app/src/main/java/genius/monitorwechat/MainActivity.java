package genius.monitorwechat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    public void  onEvent(MyBus myBus){

        if(text!=null){
            text.setText(myBus.data+"\n"+text.getText());
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView) findViewById(R.id.text);

        try {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        try {
            //이벤트 버스 해제
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void stopmonit(View v){
        SqUtils     sqUtils = new SqUtils(this);
        sqUtils.setValue("adenable",false);
        Intent intent = new Intent(getApplicationContext()
                ,MonitorService.class);
        intent.setAction(MonitorService.STOP);
        startService(intent);
    }

    public void startmonit(View v){

        if(checkAuthority()){
            SqUtils     sqUtils = new SqUtils(this);
            sqUtils.setValue("adenable",true);
            alarm();

            Intent intent = new Intent(getApplicationContext(),MonitorService.class);
            intent.setAction(MonitorService.START);
            startService(intent);
        }

    }


    public  void alarm( ){

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, HellowActReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, 3000, pIntent);
    }
    public  void canclealarm(Context context ){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HellowActReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }





    /**
     * 실행하기 앱인지  ,  실행하기 앱이여서 권한이 열려 있는지 판단 .
     *
     * @return true 면 진행 가능
     */
    private boolean checkAuthority() {

        //권한 안열었다 . 권한이 열려있어야 딤 .
        if (!checkopen(this)) {

            //옵션이 있는지 보기
            if (checkoption(this)) {
                Utils_Alert.showAlertDialog(this, android.R.string.dialog_alert_title, R.string.plz_open_author, true, android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        startActivity(intent);
                    }
                }, 0, null, null);

                return false;
            } else {
                Log.v("d", "no option can use");
                return false;
            }

        }

        return true;
    }


    /**
     * 查看权限是否打开。   版本一下的 默认打开。
     */
    public static boolean checkopen(Context context) {
        try {
            long ts = System.currentTimeMillis();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatses = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, ts);

                if (usageStatses == null || usageStatses.isEmpty()) {
                    return false;
                } else {
                    return true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }


    /**
     * 检查 设备有没有  “查看使用权限的应用” 这个选项
     *
     */
    public static  boolean  checkoption(Context context ) {

        List<ResolveInfo> list = null;
        try {
            PackageManager packageManager =context.getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  list!=null && list.size() > 0;

    }


}
