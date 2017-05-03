package lwq.com.eventbusdemo.Event;

/**
 * EventBus的事件：信息
 */

public class MessageEventC {

    private String msg;

    public MessageEventC(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

