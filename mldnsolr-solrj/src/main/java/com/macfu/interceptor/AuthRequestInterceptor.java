package com.macfu.interceptor;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * @Author: liming
 * @Date: 2019/01/15 17:38
 * @Description: 认证请求的拦截器
 */
public class AuthRequestInterceptor implements HttpRequestInterceptor {

    // 对于当前的Solr-web服务器而言采用的是Basic模式实现的认证处理
    private ContextAwareAuthScheme authSchema = new BasicScheme();

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        AuthState authState = (AuthState) httpContext.getAttribute(HttpClientContext.TARGET_AUTH_STATE);
        if (authState != null && authState.getAuthScheme() == null) {
            CredentialsProvider provider = (CredentialsProvider) httpContext.getAttribute(HttpClientContext.CREDS_PROVIDER);
            HttpHost targetHost = (HttpHost) httpContext.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
            Credentials credentials = provider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
            if (credentials == null) {
                throw new HttpException("{" + targetHost.getHostName() + "}并没有Http认证处理支持。");
            }
            httpRequest.addHeader(authSchema.authenticate(credentials, httpRequest, httpContext));
        }
    }
}
