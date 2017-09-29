package wangzhongqiu.nettystudying.bio;

import zhongqiu.javautils.ThreadUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzhongqiu on 2017/9/27.
 */
public class Client implements Runnable {
    public static void main(String[] args) {
        int port = 8080;
        int poolSize = 10;
        List<Runnable> timeClientList = new ArrayList<Runnable>();
        for (int i = 0; i < poolSize; i++) {
            timeClientList.add(new Client(port));
        }
        ThreadUtil.fixedThreadPool(poolSize, timeClientList);
    }

    Client(int port) {
        this.port = port;
    }

    private int port;

    public void run() {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY AMOUNT");
            System.out.println("Send order succeed.");
            String resp = in.readLine();
            System.out.println("Amount is : " + resp);
        } catch (Exception e) {
            //不需要处理
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}

