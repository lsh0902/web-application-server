package util;

import constant.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

//빌더의 역할을 할거임
public class HttpRequestParser {
    public static HttpRequestPatrick parse(InputStream in) throws IOException {
        HttpRequestPatrick req = new HttpRequestPatrick();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

        String startLine = bufferedReader.readLine();
        setStateLine(startLine, req);

        setHeader(req, bufferedReader);
        if(req.getMethod().equals(HttpMethod.POST.toString())) {
            setBody(req, bufferedReader);
        }

        System.out.println(startLine);
        return req;
    }


    private static void setStateLine(String startLine, HttpRequestPatrick req) {
        String[] parsedStartLine = startLine.split(" ");
        String url = parsedStartLine[1];

        if (url.contains("?")) {
            String[] urlAndParam = req.getUrl().split("\\?");
            url = urlAndParam[0];
            setParams(req, urlAndParam[1]);
        }

        req.setUrl(url);
        req.setMethod(parsedStartLine[0]);
    }

    private static void setHeader(HttpRequestPatrick req, BufferedReader bufferedReader) throws IOException {
        String line;
        while(!(line = bufferedReader.readLine()).equals("")) {
            if(line == null) {
                break;
            }
            String[] parsed = line.split(": ");
            req.addHeader(parsed[0], parsed[1]);
        }
    }

    private static void setParams(HttpRequestPatrick req, String paramData) {
        Map<String, String> param = HttpRequestUtils.parseQueryString(paramData);
        req.setParams(param);
    }

    private static void setBody(HttpRequestPatrick req, BufferedReader bufferedReader) throws IOException {
        int contentLen = Integer.parseInt(req.getHeaderValue("Content-Length"));

        String body = IOUtils.readData(bufferedReader, contentLen);

        setParams(req, body);
    }


}
