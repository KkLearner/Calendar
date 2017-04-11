package com.gxl.im.base;

import java.io.File;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.gxl.im.wrapper.HeaderWrapper;
import com.gxl.im.wrapper.QueryWrapper;
import com.gxl.im.wrapper.ResponseWrapper;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, ContainerNode<?> body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header);
}
