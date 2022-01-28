package webserver.domain;

import util.HttpRequestUtils;

import java.util.Map;

public class HttpCookie {
    private Map<String, String> cookies;

    public HttpCookie(String cookie) {
        this.cookies = HttpRequestUtils.parseCookies(cookie);
    }

    public String getCookies(String key) {
        return cookies.get(key);
    }
}
