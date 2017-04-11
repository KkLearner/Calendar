package com.gxl.im.base;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RestAPIUtils {
	
	private static CloseableHttpClient client = null;
    /**
     * Create a httpClient instance
     *
     * @param isSSL if the request is protected by ssl
     * @return HttpClient instance
     */
    public static HttpClient getHttpClient(boolean isSSL, String CacertFilePath, String CacertFilePassword) {
        if (isSSL&&client==null) {
            try {
            	SSLContext sslContext=custom(CacertFilePath, CacertFilePassword);
            	Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
            	           .register("http", PlainConnectionSocketFactory.INSTANCE)  
            	           .register("https", new SSLConnectionSocketFactory(sslContext))  
            	           .build();  
            	       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
            	       HttpClients.custom().setConnectionManager(connManager);              	  
            	//创建自定义的httpclient对象  
            	client = HttpClients.custom().setConnectionManager(connManager).build();   
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    /**
     * Check illegal String
     *
     * @param regex reg expression
     * @param str   string to be validated
     * @return if matched
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.lookingAt();
    }

    public static SSLContext custom(String keyStorePath, String keyStorepass){  
        SSLContext sc = null;  
        KeyStore trustStore = null; 
        InputStream inputStream =null;
        try {  
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); 
            inputStream = ClientContext.class.getClassLoader().getResourceAsStream(keyStorePath);
            trustStore.load(inputStream, keyStorepass.toCharArray());  
            // 相信自己的CA和所有自签名的证书  
            sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();           
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
            	inputStream.close();  
            } catch (IOException e) {  
            }  
        }  
        return sc;  
    }  

}
