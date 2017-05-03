package lwq.com.eventbusdemo.Event;

/**
 * EventBus的事件：信息
 */

public class MessageEventA {
    private String msg;

    public MessageEventA(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
