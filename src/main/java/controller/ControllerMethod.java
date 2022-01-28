package controller;

import util.HttpRequestPatrick;
import util.HttpResponsePatrick;

@FunctionalInterface
public interface ControllerMethod {
    public void run(HttpRequestPatrick request, HttpResponsePatrick response);
}
