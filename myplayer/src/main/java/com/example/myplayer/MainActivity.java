package com.example.myplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title;
    private Button play;
    private Button stop;
    private Intent intent;
    private String[]musics = {"mai.mp3","hunli.mp3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        intent = new Intent(this,MusicService.class);
        //注册广播
        IntentFilter filter = new IntentFilter("us.mifeng.action.music");
        registerReceiver(new MusicReceiver(),filter);
    }

    private void initView() {
        title = (TextView) findViewById(R.id.playertitle);
        play = (Button) findViewById(R.id.play);
        stop = (Button) findViewById(R.id.stop);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                //1,代表播放
                intent.putExtra("type",1);
                break;
            case R.id.stop:
                //2,代表停止
                intent.putExtra("type",2);
                break;
            case R.id.next:
                intent.putExtra("type",3);
                break;
            case R.id.last:
                intent.putExtra("type",4);
        }
        startService(intent);
    }
    class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取状态数据，并设置相应数据
            int num = intent.getIntExtra("num",-1);
            int state = intent.getIntExtra("state",-1);
            if (num>-1){
                title.setText(musics[num]);
            }
            if (state ==0x111 || state == 0x113){
                play.setText("播放");
            }else{
                play.setText("暂停");
            }
        }
    }
}
