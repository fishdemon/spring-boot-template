package com.fishdemon.sbt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class HttpClient {

    private final static String proxyIp = "localhost";
    private final static int proxyPort = 10000;

    public static void main(String[] args) {
        httpURLConectionGET("http://www.jianshu.com/p/16eb8ee4b6b8", "");
    }

    /**
     * @描述：HttpURLConnection 接口调用 GET方式
     * @param strUrl 请求地址
     * @param param 请求参数拼接
     * @return 请求结果集
     */
    public static String httpURLConectionGET(String strUrl, String param) {
        StringBuffer sb = new StringBuffer("");

        BufferedReader br =null;
        HttpURLConnection connection =null;
        try {
            strUrl = strUrl + "?" + param.trim();
            URL url = new URL(strUrl);    // 把字符串转换为URL请求地址

            // 实例化本地代理对象
            Proxy proxy= new Proxy(Proxy.Type.SOCKS,new InetSocketAddress(proxyIp, proxyPort));
//            Authenticator.setDefault(new SimpleAuthenticator(proxyUserName,proxyPassword));

            connection = (HttpURLConnection) url.openConnection(proxy);// 打开连接
            connection.setConnectTimeout(60000);
            connection.setDoOutput(true);
            connection.connect();// 连接会话

            if(connection.getResponseCode()==200){
                // 获取输入流
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {// 循环读取流
                    sb.append(line);
                }
            }else{
                System.out.println("请求失败!HTTP Status："+connection.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }finally{
            try {
                if(br != null){
                    br.close();// 关闭流
                }
                if(connection != null){
                    connection.disconnect();// 断开连接
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }


    /**
     * 将字符串发送到指定url地址
     * @param url 请求地址
     * @param content 请求内容
     * @return 请求结果集
     */
    public static String httpPost(String url, String content) {
        String strResult = "";
        CloseableHttpClient httpclient =null;
        HttpEntity resEntity;
        CloseableHttpResponse response;
        try {
            StringEntity myEntity = new StringEntity(content, "UTF-8");
            HttpPost httpPost = new HttpPost(url);


            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(proxyIp, proxyPort),
                    new UsernamePasswordCredentials(proxyUserName, proxyPassword));

            HttpHost proxy = new HttpHost(proxyIp,proxyPort);

            RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy)
                    .setConnectTimeout(120000).setConnectionRequestTimeout(120000)
                    .setSocketTimeout(120000).build();

            httpclient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credsProvider)
                    .setDefaultRequestConfig(requestConfig)
                    .build();

            httpPost.addHeader("Content-Type", "text/xml; charset=UTF-8");
            httpPost.setEntity(myEntity);
            response = httpclient.execute(httpPost);

            resEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode()==200 && resEntity != null) {
                strResult = EntityUtils.toString(resEntity, "UTF-8");
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("接口异常：" + e.getMessage());
        } finally {
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("关闭httpclient异常：" + e.getMessage());
                }
            }
        }
        return strResult;
    }

    /**
     * @描述：接口调用参数拼接POST方式
     * @param strUrl 请求地址
     * @param param 请求参数拼接
     * @return 请求结果集
     */
    public static String httpURLConectionParamPOST(String strUrl, String param) {
        StringBuffer sb = new StringBuffer("");
        BufferedReader br =null;
        HttpURLConnection connection =null;
        Proxy proxy = null;
        try {
            strUrl = strUrl + "?" + param.trim();
            URL url = new URL(strUrl);    // 把字符串转换为URL请求地址

            proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyIp,proxyPort));  // 实例化本地代理对象

            Authenticator.setDefault(new SimpleAuthenticator(proxyUserName,proxyPassword));

            connection = (HttpURLConnection) url.openConnection(proxy);// 打开连接


            connection.setConnectTimeout(60000);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.connect();// 连接会话


            if(connection.getResponseCode()==200){
                // 获取输入流
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {// 循环读取流
                    sb.append(line);
                }
            }else{
                System.out.println("请求失败!HTTP Status："+connection.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求异常!");
        }finally{
            try {
                if(br != null){
                    br.close();// 关闭流
                }
                if(connection != null){
                    connection.disconnect();// 断开连接
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("连接关闭异常!");
            }

        }
        return sb.toString();
    }


}
