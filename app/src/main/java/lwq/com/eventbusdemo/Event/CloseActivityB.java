package lwq.com.eventbusdemo.Event;

/**
 * ventBus的事件：信息
 */

public class CloseActivityB {

    private String msg;

    public CloseActivityB(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

