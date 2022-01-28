package controller;

import util.HttpRequestPatrick;

import java.util.HashMap;
import java.util.Map;

public class CommandController {

    protected static Map<String, ControllerMethod> requestMapping = new HashMap<>();

    static {
        UserController userController = new UserController();
        requestMapping.put("/user/create", userController.joinUser);

    }

    public static ControllerMethod findController(HttpRequestPatrick request) {
        ControllerMethod controllerMethod = requestMapping.get(request.getUrl());
        return controllerMethod;
    }
}
