package controller;

import model.User;
import service.UserService;
import util.HttpRequestPatrick;
import util.HttpResponsePatrick;

public class UserController {

    private final UserService userService = new UserService();

    public ControllerMethod joinUser = (HttpRequestPatrick request, HttpResponsePatrick response) -> {
        User user = new User(request.getParams().get("userId"),
                request.getParams().get("password"),
                request.getParams().get("name"),
                request.getParams().get("email"));
        userService.join(user);
        System.out.println("join success");

        response.redirect("/index.html");
    };

    public ControllerMethod login = (HttpRequestPatrick request, HttpResponsePatrick response) -> {

    };
}
