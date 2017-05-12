package com.example.advancedrendering.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.advancedrendering.R;
import com.example.advancedrendering.view.SweepGradientView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SweepGradientViewActivity extends AppCompatActivity {


    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.sweep_gradient_view)
    SweepGradientView sweepGradientView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep_gradient_view);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_start, R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                sweepGradientView.startScann();
                break;
            case R.id.btn_stop:
                sweepGradientView.stopScann();
                break;
        }
    }


}
