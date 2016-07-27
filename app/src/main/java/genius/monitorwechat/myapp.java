package genius.monitorwechat;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * Created by Hongsec on 2016-07-18.
 */
public class myapp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SqUtils sqUtils = new SqUtils(this);
        if(sqUtils.getValue("adenable",false)){

            if(checkAuthority()){
                alarm(this);
                Intent intent = new Intent(this,MonitorService.class);
                intent.setAction(MonitorService.START);
                startService(intent);
            }

        }


    }


    public  void alarm(Context context ){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, HellowActReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
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
               /* Utils_Alert.showAlertDialog(this, android.R.string.dialog_alert_title, R.string.plz_open_author, true, android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        startActivity(intent);
                    }
                }, 0, null, null);*/

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
