package com.autocrypt.mon.wrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
public class HttpRequestDataWrapper extends HttpServletRequestWrapper {

    private byte[] bytes;
    private String requestData;
    private LocalDateTime requestCreated;

    @Override
    public ServletInputStream getInputStream() {
        if(Objects.isNull(bytes)) {
            return null;
        }

        final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

        return new ServletImpl(bis);
    }

    public HttpRequestDataWrapper(HttpServletRequest request) throws IOException {
        super(request);

        String contentType = request.getContentType();

        if (null != contentType) {
            if (!contentType.isEmpty() && contentType.contains("application/json")) {
                InputStream in = super.getInputStream();
                bytes = IOUtils.toByteArray(in);
                requestData = new String(bytes);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                requestData = objectMapper.writeValueAsString(request.getParameterMap());
            }
            requestCreated = LocalDateTime.now();
        }
    }


    class ServletImpl extends ServletInputStream {
        private InputStream is;

        public ServletImpl(InputStream bis){
            is = bis;
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return is.read(b);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {}
    }

}
