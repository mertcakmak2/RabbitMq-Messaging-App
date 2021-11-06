package com.example.chat.service.user;

import com.example.chat.model.User;
import javassist.NotFoundException;

public interface UserService {

    User findUserById(Long userId) throws NotFoundException;
}
