package org.elasticsearch.http.netty4;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.http.HttpResponse;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.netty4.Netty4Utils;

public class Netty4HttpResponse extends DefaultFullHttpResponse implements HttpResponse {

    private final HttpHeaders requestHeaders;

    Netty4HttpResponse(HttpHeaders requestHeaders, HttpVersion version, RestStatus status, BytesReference content) {
        super(version, HttpResponseStatus.valueOf(status.getStatus()), Netty4Utils.toByteBuf(content));
        this.requestHeaders = requestHeaders;
    }

    @Override
    public void addHeader(String name, String value) {
        headers().add(name, value);
    }

    @Override
    public boolean containsHeader(String name) {
        return headers().contains(name);
    }

    public HttpHeaders requestHeaders() {
        return requestHeaders;
    }
}
