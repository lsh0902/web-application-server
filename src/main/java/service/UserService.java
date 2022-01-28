package service;

import db.DataBase;
import model.User;

public class UserService {
    public void join(User user) {
        DataBase.addUser(user);
    }
}
