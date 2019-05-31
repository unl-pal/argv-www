/*
* (C) 2015 Yandex LLC (https://yandex.com/)
*
* The source code of Java SDK for Yandex.Disk REST API
* is available to use under terms of Apache License,
* Version 2.0. See the file LICENSE for the details.
*/

package com.yandex.disk.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* package */ class RestClientIO {

    private static final String ETAG_HEADER = "Etag";
    private static final String SHA256_HEADER = "Sha256";
    private static final String SIZE_HEADER = "Size";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String CONTENT_RANGE_HEADER = "Content-Range";

    private static final String METHOD_GET = "GET";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_PUT = "PUT";

    private static final Pattern CONTENT_RANGE_HEADER_PATTERN = Pattern.compile("bytes\\D+(\\d+)-\\d+/(\\d+)");

    private Object buildRequest() {
        return new Object();
    }

    /* package */ private static class ContentRangeResponse {

        private final long start, size;

        ContentRangeResponse(long start, long size) {
            this.start = start;
            this.size = size;
        }

        long getStart() {
            return start;
        }

        long getSize() {
            return size;
        }
    }
}
