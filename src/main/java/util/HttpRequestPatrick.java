package util;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestPatrick {
    private String url;
    private String method;
    private Map<String, String> params;
    private Map<String, String> header = new HashMap<>();


    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addHeader(String k, String v) {
        header.put(k, v);
    }

    public String getHeaderValue(String k) {
        return header.get(k);
    }
}
