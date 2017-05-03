package lwq.com.eventbusdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lwq.com.eventbusdemo.Event.MessageEventC;
import lwq.com.eventbusdemo.R;

public class BackgroundDemoActivity extends AppCompatActivity {

    @BindView(R.id.btn_bg_from_main)
    Button btnBgFromMain;
    @BindView(R.id.btn_bg_from_sub)
    Button btnBgFromSub;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private Context mContext;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_demo);
        ButterKnife.bind(this);
        mContext = this;
        //注册EventBus
        EventBus.getDefault().register(this);
        index = 0;
    }

    @Override
    protected void onDestroy() {
        //取消EvenBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.btn_bg_from_main, R.id.btn_bg_from_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bg_from_main:
                // 主线程发送Event - 开子线程执行  (注意：内部维护线程池！！！)
                EventBus.getDefault().post(new MessageEventC("Hi, background boy! I'm from UI Thread."));
                break;
            case R.id.btn_bg_from_sub:// 子线程发送Event - 子线程立刻执行
                new Thread() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new MessageEventC("Hi, background boy! I'm from Sub Thread."));
                    }
                }.start();
                break;
        }
    }

    /**
     * BACKGROUND线程模型：事件如果是在子线程发布，onBackgroundEvent方法就在该子线程执行，事件如果是在
     * 主线程中发布，onBackgroundEvent方法就在EventBus内部的线程池中执行
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventC event) {
        final String content = event.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName();

        // 确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(content);
            }
        });
    }
}
