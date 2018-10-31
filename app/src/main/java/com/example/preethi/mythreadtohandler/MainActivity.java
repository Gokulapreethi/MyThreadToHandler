package com.example.preethi.mythreadtohandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.TextView;

import static com.example.preethi.mythreadtohandler.ThreadHandlerActivity.mMessageSender;

public class MainActivity extends Activity {
    private static final String MSG_KEY = "yo";
    static TextView myTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = (TextView) findViewById(R.id.myTextView);

        final Button button = (Button) findViewById(R.id.myButton1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThreadHandlerActivity.class);
                startActivity(intent);
            }
        });

    }

    static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString(MSG_KEY);
            myTextView.setText(string);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        //TODO not sure if this is needed for this use case
        mHandler.removeCallbacks(mMessageSender);
    }


    private String getCurrentTime() {


        return "2018-10-10 00:01:23";
    }

}

class ThreadHandlerActivity extends AppCompatActivity {

    static public int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);
        final Button button = (Button) findViewById(R.id.myButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleButtonClick(v);
            }
        });
    }

    public void handleButtonClick(View view) {
        new Thread(mMessageSender).start();
    }

    static final Runnable mMessageSender = new Runnable() {
        public void run() {
            while (true) {
                try {
                    Log.i("111","Enter value===> "+i);
                    Message msg = MainActivity.mHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("yo", String.valueOf(i));
                    msg.setData(bundle);
                    MainActivity.mHandler.sendMessage(msg);
                    i++;
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }
    };
}
