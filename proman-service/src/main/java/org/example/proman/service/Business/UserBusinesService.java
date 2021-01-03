package org.example.proman.service.Business;

import org.example.proman.service.Entity.UserEntity;
import org.example.proman.service.dao.UserDao;
import org.example.proman.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinesService {
    @Autowired
    private UserDao userDao;

    public UserEntity getUser(final String userUuid) throws ResourceNotFoundException {
        UserEntity userEntity =  userDao.getUser(userUuid);
        if(userEntity == null){
            throw new ResourceNotFoundException("USR-001", "User not found");
        }
        return userEntity;
    }
}
