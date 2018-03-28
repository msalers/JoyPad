package com.example.maria.controller;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button connect, disconnect;
    Client client;
    TextView textView1, textView2, textView3, textView4, textView5;
    EditText name;
    JoyStickClass js;
    RelativeLayout layout_joystick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //JOYPAD
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);

        js = new JoyStickClass(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {
                    textView1.setText("X : " + String.valueOf(js.getX()));
                    textView2.setText("Y : " + String.valueOf(js.getY()));
                    textView3.setText("Angle : " + String.valueOf(js.getAngle()));
                    textView4.setText("Distance : " + String.valueOf(js.getDistance()));

                    int direction = js.get8Direction();
                    if (direction == JoyStickClass.STICK_UP) {
                        textView5.setText("Direction : Up");
                        client.setUp(true);
                        client.setRight(false);
                        client.setLeft(false);
                        client.setDown(false);
                    } else if (direction == JoyStickClass.STICK_UPRIGHT) {
                        textView5.setText("Direction : Up Right");
                        client.setUp(true);
                        client.setRight(true);
                        client.setLeft(false);
                        client.setDown(false);
                    } else if (direction == JoyStickClass.STICK_RIGHT) {
                        textView5.setText("Direction : Right");
                        client.setUp(false);
                        client.setRight(true);
                        client.setLeft(false);
                        client.setDown(false);
                    } else if (direction == JoyStickClass.STICK_DOWNRIGHT) {
                        textView5.setText("Direction : Down Right");
                        client.setUp(false);
                        client.setRight(true);
                        client.setLeft(false);
                        client.setDown(true);
                    } else if (direction == JoyStickClass.STICK_DOWN) {
                        textView5.setText("Direction : Down");
                        client.setUp(false);
                        client.setRight(false);
                        client.setLeft(false);
                        client.setDown(true);
                    } else if (direction == JoyStickClass.STICK_DOWNLEFT) {
                        textView5.setText("Direction : Down Left");
                        client.setUp(false);
                        client.setRight(false);
                        client.setLeft(true);
                        client.setDown(true);
                    } else if (direction == JoyStickClass.STICK_LEFT) {
                        textView5.setText("Direction : Left");
                        client.setUp(false);
                        client.setRight(false);
                        client.setLeft(true);
                        client.setDown(false);
                    } else if (direction == JoyStickClass.STICK_UPLEFT) {
                        textView5.setText("Direction : Up Left");
                        client.setUp(true);
                        client.setRight(false);
                        client.setLeft(true);
                        client.setDown(false);
                    } else if (direction == JoyStickClass.STICK_NONE) {
                        textView5.setText("Direction : Center");
                        client.setUp(false);
                        client.setRight(false);
                        client.setLeft(false);
                        client.setDown(false);
                    } else {
                        client.setUp(false);
                        client.setRight(false);
                        client.setLeft(false);
                        client.setDown(false);
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    textView1.setText("X :");
                    textView2.setText("Y :");
                    textView3.setText("Angle :");
                    textView4.setText("Distance :");
                    textView5.setText("Direction :");
                }
                return true;
            }
        });

        //FINE JOYPAD
        name = (EditText) findViewById(R.id.name);

        connect = (Button) findViewById(R.id.connect);

        disconnect = (Button) findViewById(R.id.disconnect);
        disconnect.setEnabled(false);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client = new Client(name.getText().toString(), MainActivity.this);
                client.execute();


                connect.setEnabled(false);
                disconnect.setEnabled(true);
            }
        });

        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connect.setEnabled(true);
                disconnect.setEnabled(false);
            }
        });
    }

}