package broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MyBroadCast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"我接收到了广播发送的消息", Toast.LENGTH_SHORT).show();
    }
}
