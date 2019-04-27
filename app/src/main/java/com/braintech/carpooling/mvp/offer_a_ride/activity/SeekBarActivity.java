package com.braintech.carpooling.mvp.offer_a_ride.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.braintech.carpooling.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class SeekBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);

        RangeSeekBar rangeSeekBar = (RangeSeekBar)findViewById(R.id.rangeSeekbar);

        rangeSeekBar.setRangeValues(0, 500);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                Toast.makeText(getApplicationContext(), minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
            }
        });

// Get noticed while dragging
        rangeSeekBar.setNotifyWhileDragging(true);
    }
}
