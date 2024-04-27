package com.example.managersystem.service;

import com.example.managersystem.domain.*;
import com.example.managersystem.repository.ManagerSystemRepositoryImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManagerSystemServiceImplTest {
    private ManagerSystemRepository mockManagerSystemRepository;

    private ManagerSystemService managerSystemService;

    @BeforeEach
    public void setup(){
        mockManagerSystemRepository = mock(ManagerSystemRepository.class);
        managerSystemService = new ManagerSystemServiceImpl(mockManagerSystemRepository);
    }

    @Test
    void addUserSuccess() throws NoAccessException {
        AddUserCommand command = new AddUserCommand();
        command.setUserId(123456L);
        command.setEndpoint(Lists.list(
                "resource A",
                "resource B",
                "resource C"
        ));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("admin");
        managerSystemService.addUser(command, userInfo);
    }

    @Test
    void addUserNoAccess() {
        AddUserCommand command = new AddUserCommand();
        command.setUserId(123456L);
        command.setEndpoint(Lists.list(
                "resource A",
                "resource B",
                "resource C"
        ));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("user");
        try {
            managerSystemService.addUser(command, userInfo);
        } catch (Exception e){
            assertEquals(NoAccessException.class, e.getClass());
        }
    }

    @Test
    void getResourceSuccess() throws NoAccessException {
        GetResourceQuery query = new GetResourceQuery("resource A");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("user");
        when(mockManagerSystemRepository.getResource(query, userInfo)).thenReturn("resource A");
        String actual = managerSystemService.getResource(query, userInfo);
        assertEquals("resource A", actual);
    }

    @Test
    void getResourceNoAccess() {
        GetResourceQuery query = new GetResourceQuery("resource A");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(123456L);
        userInfo.setAccountName("XXXXXXX");
        userInfo.setRole("user");
        when(mockManagerSystemRepository.getResource(query, userInfo)).thenReturn(null);
        try {
            managerSystemService.getResource(query, userInfo);
        } catch (Exception e){
            assertEquals(NoAccessException.class, e.getClass());
        }
    }
}