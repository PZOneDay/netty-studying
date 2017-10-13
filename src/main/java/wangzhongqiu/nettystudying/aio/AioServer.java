package wangzhongqiu.nettystudying.aio;

import java.io.IOException;

/**
 * Created by wangzhongqiu on 2017/10/13.
 */
public class AioServer {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        AsyncServerHandler timeServer = new AsyncServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}
