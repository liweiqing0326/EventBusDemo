package lwq.com.eventbusdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lwq.com.eventbusdemo.Event.CloseAllActivity;
import lwq.com.eventbusdemo.R;

public class Demo1Activity extends AppCompatActivity {

    @BindView(R.id.btn_open_b)
    Button btnOpenB;
    @BindView(R.id.btn_close)
    Button btnClose;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        ButterKnife.bind(this);
        mContext = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.btn_open_b, R.id.btn_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_open_b:
                startActivity(new Intent(mContext, ActivityB.class));  // 打开界面B
                break;
            case R.id.btn_close://关掉本界面
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void closeActivity(CloseAllActivity event) {
        finish();
    }
}
