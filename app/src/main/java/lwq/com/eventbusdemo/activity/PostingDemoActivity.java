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
import lwq.com.eventbusdemo.Event.MessageEventA;
import lwq.com.eventbusdemo.R;

public class PostingDemoActivity extends AppCompatActivity {

    @BindView(R.id.btn_post_from_main)
    Button btnPostFromMain;
    @BindView(R.id.btn_post_from_sub)
    Button btnPostFromSub;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private Context mContext;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_demo);
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

    @OnClick({R.id.btn_post_from_main, R.id.btn_post_from_sub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_post_from_main:// 主线程发送Event - 主线程接收
                EventBus.getDefault().post(new MessageEventA("Hi,post girl! I'm from UI Thread"));
                break;
            case R.id.btn_post_from_sub:// 子线程发送Event - 子线程发送子线程接收
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post(new MessageEventA("Hi, post girl! I'm from Sub Thread."));
                    }
                }.start();
                break;
        }
    }

    /**
     * POSTING线程模型：在哪个线程发布事件，就在哪个线程执行onPostingEvent方法
     *
     * @param eventA
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageEventA eventA) {
        final String content = eventA.getMsg() + System.getProperty("line.separator", "\n")
                + "from  Thread-Id：" + Thread.currentThread().getId()
                + " , Name：" + Thread.currentThread().getName();

        //确保修改UI界面是在UI线程进行
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(content);
            }
        });
    }

}
