package wangzhongqiu.nettystudying.pretendio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangzhongqiu on 2017/9/29.
 */
public class ServerHandlerExecutePool {
    private ExecutorService executor;

    //Runtime.getRuntime().availableProcessors()方法说明
    //虚拟机其实不清楚什么是处理器，它只是去请求操作系统返回一个值。同样的，操作系统也不知道怎么回事，它是去问的硬件设备。
    //硬件会告诉它一个值，通常来说是硬件线程数。操作系统相信硬件说的，而虚拟机又相信操作系统说的。
    public ServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime()
                .availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(queueSize));
    }

    public void execute(java.lang.Runnable task) {
        executor.execute(task);
    }
}
