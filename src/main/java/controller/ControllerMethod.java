package controller;

import webserver.domain.HttpRequestPatrick;
import webserver.domain.HttpResponsePatrick;

@FunctionalInterface
public interface ControllerMethod {
    public void run(HttpRequestPatrick request, HttpResponsePatrick response);
}
