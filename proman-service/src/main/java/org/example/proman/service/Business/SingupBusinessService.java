package org.example.proman.service.Business;

import org.example.proman.service.Entity.UserEntity;
import org.example.proman.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SingupBusinessService {

    @Autowired
    private UserBusinesService UserBusinesService;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity){
        return UserBusinesService.createUser(userEntity);
    }
}
