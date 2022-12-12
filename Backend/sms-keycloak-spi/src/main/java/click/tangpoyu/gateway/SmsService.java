package click.tangpoyu.gateway;

public interface SmsService {

    void send(String phoneNumber, String message);

}
