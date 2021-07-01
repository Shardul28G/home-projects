package com.example.watertankmonitor.textUpdate;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watertankmonitor.R;

public class textUpdateImpl extends AppCompatActivity implements  textUpdate {
    @Override
    public void updateText(String mess) {
        ((TextView)findViewById(R.id.smallWaterTankLevelValue)).setText(mess);
    }
}
