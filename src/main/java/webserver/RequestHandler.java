package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import controller.CommandController;
import controller.ControllerMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestParser;
import util.HttpRequestPatrick;
import util.HttpResponsePatrick;

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
