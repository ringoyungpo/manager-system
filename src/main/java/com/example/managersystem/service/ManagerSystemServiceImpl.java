package com.example.managersystem.service;

import com.example.managersystem.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ManagerSystemServiceImpl implements ManagerSystemService {
    private final ManagerSystemRepository managerSystemRepository;
    public ManagerSystemServiceImpl(ManagerSystemRepository managerSystemRepository) {
        this.managerSystemRepository = managerSystemRepository;
    }

    @Override
    public void addUser(AddUserCommand command, UserInfo commandBy) throws NoAccessException {
        if(!"admin".equals(commandBy.getRole())){
            throw new NoAccessException();
        }
        managerSystemRepository.addUser(command);
    }

    @Override
    public String getResource(GetResourceQuery query, UserInfo queryBy) throws NoAccessException {
        String resource = managerSystemRepository.getResource(query, queryBy);
        if(resource == null){
            throw new NoAccessException();
        }
        return resource;
    }
}
