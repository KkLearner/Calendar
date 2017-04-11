package com.gxl.im.base;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gxl.im.utils.IMApiUtils;
import com.gxl.im.wrapper.ResponseWrapper;

public class TokenGenerator {

    private String accessToken;
    private Long expiredAt = -1L;
    private ClientContext context;

    public TokenGenerator() {
    }

    public TokenGenerator(ClientContext context) {
        this.context = context;
    }

    public String request(Boolean force) {
        force = (null == force) ? Boolean.FALSE : force;

        if (isExpired() || force) {
            // Request new token
            if (null == context || !context.isInitialized()) {
                throw new RuntimeException("INVAILID_CONTEXT_MSG");
            }          
            String clientId = ClientContext.getInstance().getClientId();
            String clientSecret = ClientContext.getInstance().getClientSecret();
            ResponseWrapper response = IMApiUtils.getInstance().getAuthToken(clientId, clientSecret);
            if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
                ObjectNode responseBody = (ObjectNode) response.getResponseBody();
                String newToken = responseBody.get("access_token").asText();
                Long newTokenExpire = responseBody.get("expires_in").asLong() * 1000;
                
                this.accessToken = newToken;
                this.expiredAt = System.currentTimeMillis() + newTokenExpire;
            }
        }

        return accessToken;
    }

    public Boolean isExpired() {
        return System.currentTimeMillis() > expiredAt;
    }

    public void setContext(ClientContext context) {
        this.context = context;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpiredAt(Long expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Long getExpiredAt() {
        return expiredAt;
    }

    @Override
    public String toString() {
        return accessToken;
    }

}
