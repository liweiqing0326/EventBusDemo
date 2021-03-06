package lwq.com.eventbusdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lwq.com.eventbusdemo.Bean.LoginInfoBean;
import lwq.com.eventbusdemo.R;

public class Demo2Activity extends AppCompatActivity {

    @BindView(R.id.tv_sticky_event)
    TextView tvStickyEvent;
    @BindView(R.id.btn_remove_sticky)
    Button btnRemoveSticky;
    @BindView(R.id.btn_open_e)
    Button btnOpenE;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        ButterKnife.bind(this);
        mContext = this;
        /**
         * 注意：当注册的时候，粘性事件会立刻被post给订阅者，所以注意注册的位置，
         * 如果在findviewbyid之前注册，那么注册时候就会执行下面订阅中的方法，导致空指针异常！
         */
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.btn_remove_sticky, R.id.btn_open_e})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_remove_sticky://手动移除粘性事件
                //1. 获取参数是LoginInfoBean的发布的最新的粘性事件
                LoginInfoBean loginInfoBean = EventBus.getDefault().getStickyEvent(LoginInfoBean.class);
                //2. 如果有改事件，就删除它
                if (loginInfoBean != null) {
                    EventBus.getDefault().removeStickyEvent(LoginInfoBean.class);
                    Toast.makeText(mContext, "粘性事件已经被移除!", Toast.LENGTH_SHORT).show();
                }
                //3. 也可以直接移除所有的粘性事件
                break;
            case R.id.btn_open_e://手动移除粘性事件
                startActivity(new Intent(mContext, ActivityE.class));  // 打开界面D
                finish();
                break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getLoginInfo(LoginInfoBean event) {
        tvStickyEvent.setText("登陆名：代高凯\n登陆时间：2016-6-6\n登陆地点：北京\n经度：100\n纬度：100\n" + event.msg);
    }
}
