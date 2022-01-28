package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponsePatrick {

    private static final Logger log = LoggerFactory.getLogger(HttpResponsePatrick.class);

    private DataOutputStream dos = null;
    private int statusCode;
    private String redirectUrl;
    private String responseUrl;
    private String body;
    private Map<String, String> header = new HashMap<>();

    public HttpResponsePatrick(OutputStream out) {
        dos = new DataOutputStream(out);
    }


    public void forward(String url) {
        try {
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            if(url.endsWith(".css")) {
                header.put("Content-Type", "text/css");
            } else if(url.endsWith(".js")) {
                header.put("Content-Type", "application/javascript");
            } else {
                header.put("Content-Type", "text/html;charset=utf-8");
            }
            header.put("Content-Length", body.length + "");
            response200Header(body.length);
            responseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forwardBody(String body) {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        header.put("Content-Type", "text/html;charset=utf-8");
        header.put("Content-Length", bytes.length + "");
        response200Header(bytes.length);
        responseBody(bytes);
    }

    public void redirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            handleHeader();
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            handleHeader();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void handleHeader() {
        header.keySet().stream().forEach(key -> {
            try {
                dos.writeBytes(key + ": " + header.get(key) + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public DataOutputStream getDao() {
        return dos;
    }

    public void setDao(DataOutputStream dos) {
        this.dos = dos;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
