package genius.monitorwechat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seojk on 2016-01-02.
 */
public class MyBus {


    public MyBus(){
        target_name = new ArrayList<String>();
    }


    /**
     1、onEvent  <br/>
     2、onEventMainThread <br/>
     3、onEventBackgroundThread <br/>
     4、onEventAsync <br/>
     */
    public int type=1;


    /**
     * Define what you want to do
     */
    public int action=0;

    /**
     * Target class Name <br/>
     */
    public List<String> target_name=null;

    /**
     * Target to all
     */
    public boolean target_all=false;


    /**
     * Your data
     */
    public Object data;
}
