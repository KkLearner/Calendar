package com.gxl.im.base;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gxl.im.wrapper.HeaderWrapper;
import com.gxl.im.wrapper.QueryWrapper;
import com.gxl.im.wrapper.ResponseWrapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpClientRestAPIInvoker implements RestAPIInvoker {

    public ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, ContainerNode<?> body, QueryWrapper query) {

        ResponseWrapper responseWrapper = new ResponseWrapper();
        ObjectNode responseNode = JsonNodeFactory.instance.objectNode();

        responseWrapper.setResponseBody(responseNode);

        url = buildQuery(url, query);
        String cacertFilePath = ClientContext.getInstance().getCacertFilePath();
        String cacertFilePassword = ClientContext.getInstance().getCacertFilePassword();
        HttpClient client = RestAPIUtils.getHttpClient(StringUtils.startsWithIgnoreCase(url, "HTTPS"), cacertFilePath, cacertFilePassword);

        URL target;
        try {
            target = new URL(url);
        } catch (MalformedURLException e) {
        	e.printStackTrace();
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        HttpUriRequest request=null;
        HttpResponse response=null;
        try {
            switch (method) {
                case "POST":
                    request = new HttpPost(target.toURI());
                    break;
                case "PUT":
                    request = new HttpPut(target.toURI());
                    break;
                case "GET":
                    request = new HttpGet(target.toURI());
                    break;
                case "DELETE":
                    request = new HttpDelete(target.toURI());
                    break;
                default:
                    throw new RuntimeException();
            }
        } catch (URISyntaxException e) {
        	e.printStackTrace();
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        if (null != body && body.size()>0) {
        	StringEntity entity=new StringEntity(body.toString(), "UTF-8");
            ((HttpEntityEnclosingRequestBase) request).setEntity(entity);
        }
        buildHeader(request, header);
        try {
            response = client.execute(request);
        } catch (IOException e) {
        	e.printStackTrace();
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        responseWrapper = readResponse(responseWrapper, response, false);
        System.out.println(responseWrapper.getResponseBody());
        return responseWrapper;
    }

    public ResponseWrapper uploadFile(String url, HeaderWrapper header, File file) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        ObjectNode responseNode = JsonNodeFactory.instance.objectNode();
        responseWrapper.setResponseBody(responseNode);
        String cacertFilePath = ClientContext.getInstance().getCacertFilePath();
        String cacertFilePassword = ClientContext.getInstance().getCacertFilePassword();
        CloseableHttpClient client = (CloseableHttpClient) RestAPIUtils.getHttpClient(StringUtils.startsWithIgnoreCase(url, "HTTPS"), cacertFilePath, cacertFilePassword);
        CloseableHttpResponse response = null;
        
        try {
            HttpPost httppost = new HttpPost(url);
            buildHeader(httppost, header);

            httppost.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, file.getName()).build());

            response = client.execute(httppost);
            responseWrapper = readResponse(responseWrapper, response, false);

        } catch (Exception e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        } finally {
            try {
                response.close();
                client.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        return responseWrapper;
    }

    public ResponseWrapper downloadFile(String url, HeaderWrapper header) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        ObjectNode responseNode = JsonNodeFactory.instance.objectNode();
        responseWrapper.setResponseBody(responseNode);
        String cacertFilePath = ClientContext.getInstance().getCacertFilePath();
        String cacertFilePassword = ClientContext.getInstance().getCacertFilePassword();
        CloseableHttpClient client = (CloseableHttpClient) RestAPIUtils.getHttpClient(StringUtils.startsWithIgnoreCase(url, "HTTPS"), cacertFilePath, cacertFilePassword);

        HttpGet request = new HttpGet(url);
        HttpResponse response;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            responseWrapper.addError(e.getMessage());
            return responseWrapper;
        }

        responseWrapper = readResponse(responseWrapper, response, true);

        return responseWrapper;
    }

    private void buildHeader(HttpUriRequest request, HeaderWrapper header) {
        if (null != header && !header.getHeaders().isEmpty()) {
            for (NameValuePair nameValuePair : header.getHeaders()) {
                request.addHeader(nameValuePair.getName(), nameValuePair.getValue());
            }
        }
    }

    private String buildQuery(String url, QueryWrapper query) {
        if (null != url && null != query && !query.getQueries().isEmpty()) {
            url += "?";
            for (NameValuePair nameValuePair : query.getQueries()) {
                url += nameValuePair.getName() + "=" + nameValuePair.getValue();
                url += "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    private ResponseWrapper readResponse(ResponseWrapper responseWrapper, HttpResponse response, boolean isFile) {
        ObjectNode responseNode;
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            responseWrapper.setResponseStatus(response.getStatusLine().getStatusCode());

            Object responseContent;
            try {
                if (isFile) {
                    responseContent = entity.getContent();
                } else {
                    responseContent = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
            } catch (ParseException e) {
            	e.printStackTrace();
                responseWrapper.addError(e.getMessage());
                return responseWrapper;
            } catch (IOException e) {
            	e.printStackTrace();
                responseWrapper.addError(e.getMessage());
                return responseWrapper;
            }

            if (!isFile) {
                ObjectMapper mapper = new ObjectMapper();
                JsonFactory factory = mapper.getFactory();
                JsonParser jp;
                try {
                    jp = factory.createParser(responseContent.toString());
                    responseNode = mapper.readTree(jp);
                    responseWrapper.setResponseBody(responseNode);
                } catch (IOException e) {
                	e.printStackTrace();
                    responseWrapper.addError("STR2JSON_ERROR_MSG");
                }
            } else {
                responseWrapper.setResponseBody(responseContent);
            }
        }
        return responseWrapper;
    }

}