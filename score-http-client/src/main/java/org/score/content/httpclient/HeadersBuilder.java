package org.score.content.httpclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HeadersBuilder {
    private String headers;
    private String contentType;

    public HeadersBuilder setHeaders(String headers) {
        this.headers = headers;
        return this;
    }

    public HeadersBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Header[] buildHeaders() {
        ArrayList<Header> headersArr = new ArrayList<Header>();
        if (!StringUtils.isEmpty(headers)) {
            BufferedReader in = new BufferedReader(new StringReader(headers));

            String str;
            try {
                while((str = in.readLine()) != null){
                    CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
                    charArrayBuffer.append(str);
                    headersArr.add(new BufferedHeader(charArrayBuffer));
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        if (!StringUtils.isEmpty(contentType)) {
            headersArr.add(new BasicHeader("Content-Type", contentType));
        }
        return headersArr.toArray(new Header[headersArr.size()]);
    }
}