package lwq.com.eventbusdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
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
import lwq.com.eventbusdemo.Event.MessageEventD;
import lwq.com.eventbusdemo.R;

public class AsyncDemoActivity extends AppCompatActivity {

    @BindView(R.id.btn_async_from_main)
    Button btnAsyncFromMain;
    @BindView(R.id.btn_async_from_sub)
    Button btnAsyncFromSub;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private Context mContext;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_demo);
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

    @OnClick({R.id.btn_async_from_main, R.id.btn_async_from_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_async_from_main:// 主线程发送Event - 开子线程执行  (注意：内部维护线程池！！！)
                EventBus.getDefault().post(new MessageEventD("Hi, background boy! I'm from UI Thread."));
                break;
            case R.id.btn_async_from_sub:// 子线程发送Event - 开子线程执行  (注意：内部维护线程池！！！)
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEventD("Hi, background boy! I'm from Sub Thread."));
                    }
                }.start();
                break;
        }
    }

    /**
     * ASYNC线程模型：不管事件在哪个线程发布，onAsyncEvent方法都在EventBus内部的线程池中执行
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(MessageEventD event) {
        final String content = event.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName() + System.getProperty("line.separator", "\n")
                + Thread.getAllStackTraces().size();

        // 确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(content);
                Toast.makeText(mContext, "子线程沉睡3s!", Toast.LENGTH_SHORT).show();
            }
        });
        SystemClock.sleep(3000);
    }
}
