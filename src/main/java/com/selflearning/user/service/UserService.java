package com.selflearning.user.service;

import com.selflearning.user.VO.Department;
import com.selflearning.user.VO.RestTemplateVO;
import com.selflearning.user.entity.User;
import com.selflearning.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public RestTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment of UserService");
        RestTemplateVO VO = new RestTemplateVO();
        User user = userRepository.findByUserId(userId);

        //Department department = restTemplate.getForObject("http://localhost:9001/departments/" + user.getDepartmentId(), Department.class);
        Department department = getDepartment(user.getDepartmentId().toString());

        VO.setUser(user);
        VO.setDepartment(department);

        return VO;
    }

    public Department getDepartment(String deprtmentId) {
//        return restTemplate.getForObject(String.format("/address/%s", addressId), Address.class);
        return restTemplate.getForObject(String.format("http://localhost:9001/departments/%s", deprtmentId), Department.class);
    }
}
