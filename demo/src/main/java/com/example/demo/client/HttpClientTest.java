package com.example.demo.client;

import com.example.demo.token.RestoreToken;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/25.
 */
public class HttpClientTest {
public static void main(String a[]) throws IOException, JSONException {
    //new HttpClientTest().get();
    //new HttpClientTest().login();
    new HttpClientTest().callSomeRestInterface();

}
    //get token
    public void login() throws IOException, JSONException{
        JSONObject jsonParam = new JSONObject();
        jsonParam.put("userName", "admin");
        jsonParam.put("userPw", "admin");
        HttpEntity entity = post("http://localhost:8081/demo1/login",jsonParam);
        RestoreToken.token = EntityUtils.toString(entity, "UTF-8");
    }

    //get token
    public void callSomeRestInterface() throws IOException, JSONException {
        JSONObject jsonParam = new JSONObject();
        //jsonParam.put("token", RestoreToken.token);
        jsonParam.put("token", "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE0OTU3ODE1MTEsInVzZXJuYW1lIjoiYWRtaW4ifQ.t-bRQ-9SpJVH1q4SozohABsEpq_s9WdA7SkvOC2xMEPuVOBRXDB7vFA2Jcw4JVQ1ddJgL8DMKXWfU0vHYp7G-A");
        jsonParam.put("data", "xxxxx");
        HttpEntity entity = post("http://localhost:8081/demo1/call",jsonParam);
    }

    public HttpEntity post(String url,JSONObject jsonParam) throws IOException, JSONException {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        HttpEntity entity;
      try{
          StringEntity sentity = new StringEntity(jsonParam.toString(),"utf-8");//解决中文乱码问题
          sentity.setContentEncoding("UTF-8");
          sentity.setContentType("application/json");
          httppost.setEntity(sentity);
        CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                entity = response.getEntity();
                if (entity != null) {
                    System.err.println("Response content==" + EntityUtils.toString(entity, "UTF-8"));
                }
            } finally {
                response.close();
            }
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }



    public void get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet("http://localhost:8081/demo1/ggggg");
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    System.out.println("Response content: " + EntityUtils.toString(entity));
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
