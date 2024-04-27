package com.example.managersystem.service;

import com.example.managersystem.domain.AddUserCommand;
import com.example.managersystem.domain.GetResourceQuery;
import com.example.managersystem.domain.UserInfo;

public interface ManagerSystemRepository {
    void addUser(AddUserCommand command);

    String getResource(GetResourceQuery query, UserInfo queryBy);
}
