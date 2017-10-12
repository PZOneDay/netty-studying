package wangzhongqiu.nettystudying.nio;

/**
 * Created by wangzhongqiu on 2017/10/12.
 */
public class NioClient {
    /**
     * @param args
     */
    public static void main(String[] args) {

        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new Thread(new NioClientHandle("127.0.0.1", port), "TimeClient-001")
                .start();
    }
}
