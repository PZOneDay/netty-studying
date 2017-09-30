package wangzhongqiu.nettystudying.pretendio;

import wangzhongqiu.nettystudying.bio.ServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wangzhongqiu on 2017/9/29.
 * http://ifeve.com/netty-definitive-guide-2-2/
 * 通过对输入和输出流的API文档进行分析，我们了解到读和写操作都是同步阻塞的，阻塞的时间取决于对方IO线程的处理速度和网络IO的传输速度。本质上来讲，我们无法保证生产环境的网络状况和对端的应用程序能够足够快，如果我们的应用程序依赖对方的处理速度，它的可靠性就非常差。也许在实验室进行的性能测试结果令大家满意，但是一旦上线运行，面对恶劣的网络环境和良莠不齐的第三方系统，问题就会如火山一样喷发。
 * 伪异步IO实际上仅仅只是对之前IO线程模型的一个简单优化，它无法从根本上解决同步IO导致的通信线程阻塞问题。
 * 下面我们就简单分析下如果通信对方返回应答时间过长引起的级联故障：
 * <p/>
 * 服务端处理缓慢，返回应答消息耗费60S，平时只需要10MS；
 * 采用伪异步IO的线程正在读取故障服务节点的响应，由于读取输入流是阻塞的，因此，它将会被同步阻塞60S；
 * 假如所有的可用线程都被故障服务器阻塞，那后续所有的IO消息都将在队列中排队；
 * 由于线程池采用阻塞队列实现，当队列积满之后，后续入队列的操作将被阻塞；
 * 由于前端只有一个Accptor线程接收客户端接入，它被阻塞在线程池的同步阻塞队列之后，新的客户端请求消息将被拒绝，客户端会发生大量的连接超时；
 * 由于几乎所有的连接都超时，调用者会认为系统已经崩溃，无法接收新的请求消息。
 */
public class PretendIOServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            ServerHandlerExecutePool singleExecutor = new ServerHandlerExecutePool(
                    50, 10000);// 创建IO任务线程池
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new ServerHandler(socket));
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
