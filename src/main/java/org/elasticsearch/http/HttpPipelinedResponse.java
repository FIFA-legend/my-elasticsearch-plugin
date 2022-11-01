package org.elasticsearch.http;

public class HttpPipelinedResponse implements HttpPipelinedMessage, HttpResponse {

    private final int sequence;
    private final HttpResponse delegate;

    public HttpPipelinedResponse(int sequence, HttpResponse delegate) {
        this.sequence = sequence;
        this.delegate = delegate;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    @Override
    public void addHeader(String name, String value) {
        delegate.addHeader(name, value);
    }

    @Override
    public boolean containsHeader(String name) {
        return delegate.containsHeader(name);
    }

    public HttpResponse getDelegateRequest() {
        return delegate;
    }
}
