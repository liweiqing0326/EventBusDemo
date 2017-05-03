package lwq.com.eventbusdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lwq.com.eventbusdemo.Bean.LoginInfoBean;
import lwq.com.eventbusdemo.R;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_posting)
    Button btnPosting;
    @BindView(R.id.btn_main)
    Button btnMain;
    @BindView(R.id.btn_background)
    Button btnBackground;
    @BindView(R.id.btn_async)
    Button btnAsync;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    private Context mContext;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick({R.id.btn_posting, R.id.btn_main, R.id.btn_background, R.id.btn_async, R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_posting:
                startActivity(new Intent(mContext,PostingDemoActivity.class));
                break;
            case R.id.btn_main:
                startActivity(new Intent(mContext,MainDemoActivity.class));
                break;
            case R.id.btn_background:
                startActivity(new Intent(mContext,BackgroundDemoActivity.class));
                break;
            case R.id.btn_async:
                startActivity(new Intent(mContext,AsyncDemoActivity.class));
                break;
            case R.id.btn1:
                startActivity(new Intent(mContext,Demo1Activity.class));
                break;
            case R.id.btn2:
                index++;
                EventBus.getDefault().postSticky(new LoginInfoBean("" + index));
                Toast.makeText(mContext, "发布了粘性事件-登陆信息", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, Demo2Activity.class));  // 打开界面D
                break;
            case R.id.btn3:
                break;
        }
    }
}
