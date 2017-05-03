package lwq.com.eventbusdemo.Bean;

public class LoginInfoBean {

    public String msg = "第几次发布的信息：";

    public LoginInfoBean(String msg) {
        this.msg = this.msg + msg;
    }
}
