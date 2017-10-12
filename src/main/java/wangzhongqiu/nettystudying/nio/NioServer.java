package wangzhongqiu.nettystudying.nio;

import java.io.IOException;

/**
 * Created by wangzhongqiu on 2017/9/30.
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        MultiplexerServer server = new MultiplexerServer(port);
        new Thread(server, "NIO-MultiplexerServer-001").start();
    }
}
