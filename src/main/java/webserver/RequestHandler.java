package webserver;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

import controller.CommandController;
import controller.ControllerMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.HttpSession;
import session.SessionDB;
import util.HttpRequestParser;
import webserver.domain.HttpRequestPatrick;
import webserver.domain.HttpResponsePatrick;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequestPatrick request = HttpRequestParser.parse(in);
            HttpResponsePatrick response = new HttpResponsePatrick(out);

            if(request.getCookie().getCookies("JSESSIONID") == null) {
                response.addHeader("Set-Cookie", "JSESSIONID="+ UUID.randomUUID());
            }

            ControllerMethod controller = CommandController.findController(request);
            if(controller == null) {
                try{
                    response.forward(request.getUrl());
                    response.redirect("/index.html");
                } catch (Exception e) {
                    response.redirect("/index.html");
                }

            }

            controller.run(request, response);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
