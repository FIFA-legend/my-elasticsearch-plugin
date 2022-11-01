package org.elasticsearch.http;

public interface HttpPipelinedMessage extends Comparable<HttpPipelinedMessage> {

    /**
     * Get the sequence number for this message.
     *
     * @return the sequence number
     */
    int getSequence();

    @Override
    default int compareTo(HttpPipelinedMessage o) {
        return Integer.compare(getSequence(), o.getSequence());
    }
}
