package com.example.managersystem.repository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.example.managersystem.domain.AddUserCommand;
import com.example.managersystem.domain.GetResourceQuery;
import com.example.managersystem.service.ManagerSystemRepository;
import com.example.managersystem.domain.UserInfo;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ManagerSystemRepositoryImpl implements ManagerSystemRepository {
    private Set<ResourcePo> resources;
    private static final String fileName = "resources.json";

    @PostConstruct
    public void init() {
        // 在这里执行读取文件的操作
        File file = Paths.get(fileName).toFile();
        if (!file.exists()){
            resources = new HashSet<>();
        } else {
            byte[] bytes = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(bytes);
                resources = JSON.parseObject(new String(bytes), new TypeReference<Set<ResourcePo>>(){});
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addUser(AddUserCommand command) {
        command.getEndpoint().stream().map(
                resource ->new ResourcePo(command.getUserId(), resource)
        ).forEach(resources::add);
        try(OutputStream outputStream = Files.newOutputStream(Paths.get(fileName))) {
            outputStream.write(JSON.toJSONBytes(resources));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResource(GetResourceQuery query, UserInfo queryBy) {
        List<ResourcePo> resourcePos = resources.stream().filter(
                resourcePo -> resourcePo.getResource().equals(query.getResource()) &&
                        resourcePo.getuserId().equals(queryBy.getUserId())
        ).collect(Collectors.toList());
        if(resourcePos.isEmpty()){
            return null;
        }
        return resourcePos.get(0).getResource();
    }
}
