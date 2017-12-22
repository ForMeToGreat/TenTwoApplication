package com.example.myaidl01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/14.
 */

public class MyService extends Service{
    private String TAG = "tag";

    public MyService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new UserBinder();
    }
    class UserBinder extends UserAidl.Stub{
        //向Aidl中传递数据
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG,"basicTypes:"+anInt+aLong+aBoolean+aFloat+aDouble+aString);
        }

        @Override
        public String getStr() throws RemoteException {
            return "北京要下雪咯";
        }

        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1+num2;
        }
    }
}
