package com.fishdemon.sbt.netproxy.socket;

import cn.hutool.core.io.IoUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * https://www.jianshu.com/p/8f7c52553dce
 * https://blog.csdn.net/A3536232/article/details/71056864?locationNum=15&fps=1
 *
 * 抓包 https://www.jianshu.com/p/16eb8ee4b6b8?
 */
public class SocketThread extends Thread{
    // 原始客户端
    private Socket socketClient;
    private InputStream isClient;
    private OutputStream osClient;
    // 代理
    private Socket socketProxy;
    private InputStream isProxy;
    private OutputStream osProxy;

    public SocketThread(Socket socket){
        this.socketClient = socket;
    }

    private byte[] buffer = new byte[4096];
    private static final byte[] VER ={0x5,0x0};
    private static final byte[] CONNECT_OK ={0x5,0x0,0x0,0x1,0,0,0,0,0,0};

    public void run(){
        try{
            System.out.println("\n\na client connect "+ socketClient.getInetAddress()+":"+ socketClient.getPort());
            isClient = socketClient.getInputStream();
            osClient = socketClient.getOutputStream();
//            int len = isClient.read(buffer);
//            String content = IoUtil.read(isClient, Charset.forName("utf-8"));
//            System.out.println(content);
//            System.out.println("< "+ bytesToHexString(buffer,0, len));
//            osClient.write(VER);
//            osClient.flush();
//            System.out.println("> "+ bytesToHexString(VER,0, VER.length));
//            len = isClient.read(buffer);
//            System.out.println("< "+ bytesToHexString(buffer,0, len));
//
//            // 查找目标服务器的主机和端口
//            String host = findHost(buffer,4,7);
//            int port = findPort(buffer,8,9);
//            System.out.println("host="+ host +",port="+ port);

            String line;
            String host = "";
            BufferedReader lineBuffer = new BufferedReader(new InputStreamReader(isClient));
            StringBuilder headStr = new StringBuilder();
            //读取HTTP请求头，并拿到HOST请求头和method
            while (null != (line = lineBuffer.readLine())) {
                System.out.println(line);
                headStr.append(line + "\r\n");
                if (line.length() == 0) {
                    break;
                } else {
                    String[] temp = line.split(" ");
                    if (temp[0].contains("Host")) {
                        host = temp[1];
                    }
                }
            }
            String type = headStr.substring(0, headStr.indexOf(" "));
            //根据host头解析出目标服务器的host和port
            String[] hostTemp = host.split(":");
            host = hostTemp[0];
            int port = 80;
            if (hostTemp.length > 1) {
                port = Integer.valueOf(hostTemp[1]);
            }

            // 连接目标服务器
            socketProxy =new Socket(host, port);
            isProxy = socketProxy.getInputStream();
            osProxy = socketProxy.getOutputStream();
            //
            for(int i =4; i <=9; i++){
                CONNECT_OK[i]= buffer[i];
            }
            osClient.write(CONNECT_OK);
            osClient.flush();
            System.out.println("> "+ bytesToHexString(CONNECT_OK,0, CONNECT_OK.length));

            SocketThreadOutput out=new SocketThreadOutput(isClient, osProxy);
            out.start();
            SocketThreadInput in=new SocketThreadInput(isProxy, osClient);
            in.start();
            out.join();
            in.join();
        }catch(Exception e){
            System.out.println("a client leave");
        }finally{
            try{
                if(socketClient !=null){
                    socketClient.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        System.out.println("socket close");
    }

    public static String findHost(byte[] bArray,int begin,int end){
        StringBuffer sb =new StringBuffer();
        for(int i =begin; i <=end; i++){
            sb.append(Integer.toString(0xFF& bArray[i]));
            sb.append(".");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static int findPort(byte[] bArray,int begin,int end){
        int port =0;
        for(int i =begin; i <=end; i++){
            port <<=16;
            port += bArray[i];
        }
        return port;
    }

    // 4A 7D EB 69
    // 74 125 235 105
    public static final String bytesToHexString(byte[] bArray,int begin,int end){
        StringBuffer sb =new StringBuffer(bArray.length);
        String sTemp;
        for(int i =begin; i <end; i++){
            sTemp =Integer.toHexString(0xFF& bArray[i]);
            if(sTemp.length()<2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            sb.append(" ");
        }
        return sb.toString();
    }
}



