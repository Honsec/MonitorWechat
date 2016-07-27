package genius.monitorwechat;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import de.greenrobot.event.EventBus;

/**
 * Created by Hongsec on 2016-07-18.
 */
public class MonitorService extends Service {


    public final static String START ="start";
    public final static String STOP ="stop";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private boolean isStart = false;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent!=null && intent.getAction()!=null){
            if(intent.getAction().equalsIgnoreCase("start") && !isStart){

                isStart = true;
                posstMessage("Start!!");
                roopCheck();


            }else if(intent.getAction().equalsIgnoreCase("stop")){

                isStart = false;
                stopSelf();
                posstMessage("End!!");
            }else{
                posstMessage("check started");
            }

        }


        return super.onStartCommand(intent, flags, startId);
    }

    private String getTime(){
        try {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
            return simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    private void posstMessage(String msg) {
        MyBus myBus =  new MyBus();
        myBus.target_name.add(MainActivity.class.getSimpleName());
        myBus.action = 1;
        myBus.type =1;
        myBus.data =getTime()+":"+ msg;
        EventBus.getDefault().post(myBus);
    }

    Handler handler = new Handler();
    int count  =0;
    boolean pkg_booted = false;
    private void roopCheck() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isStart){

                    if(!checkAuthority()) return;
                    String runningForeGround = getRunningForegroundApp(MonitorService.this);

                    if(TextUtils.isEmpty(runningForeGround)){
                        roopCheck();
                        return;
                    }
                    Log.d("d",runningForeGround);
                    posstMessage(runningForeGround);

                    if(runningForeGround.contains("launcher")){
                        if(pkg_booted){
                            pkg_booted = false;
                            Intent intent = new Intent(MonitorService.this,HellowAct.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MonitorService.this.startActivity(intent);
                            count++;
                        }

                    }else{
                        if(!MonitorService.this.getPackageName().equalsIgnoreCase(runningForeGround)){
                            pkg_booted = true;
                        }else{
                            pkg_booted =false;
                        }
                    }
                    roopCheck();
                }
            }
        },500);


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

    /**
     * http://blog.csdn.net/chaozhung_no_l/article/details/50416901
     * 要检测 是否开启  , 需要 特殊开启
     * @return
     */
    public static String getRunningForegroundApp(Context context) {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            long ts = System.currentTimeMillis();
            try {
                UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatses = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, ts - 7*1000 , ts);

                if (usageStatses == null || usageStatses.isEmpty()) {
                    return null;
                } else {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStat : usageStatses) {
                        mySortedMap.put(usageStat.getLastTimeUsed(), usageStat);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        return mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


            String componentInfo = getRunningForeGround(context);
            if (componentInfo != null) return componentInfo;

        return null;
    }



    @Nullable
    private static String getRunningForeGround(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);

            ComponentName componentInfo = taskInfo.get(0).topActivity;

            return componentInfo.getPackageName();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

}
