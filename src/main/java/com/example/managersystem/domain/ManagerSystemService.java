package com.example.managersystem.domain;

public interface ManagerSystemService {
    void addUser(AddUserCommand command, UserInfo commandBy) throws NoAccessException;
    String getResource(GetResourceQuery query, UserInfo queryBy) throws NoAccessException;
}
