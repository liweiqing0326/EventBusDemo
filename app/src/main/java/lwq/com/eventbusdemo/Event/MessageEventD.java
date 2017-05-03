package lwq.com.eventbusdemo.Event;

/**
 * EventBus的事件：信息
 */

public class MessageEventD {

    private String msg;

    public MessageEventD(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
