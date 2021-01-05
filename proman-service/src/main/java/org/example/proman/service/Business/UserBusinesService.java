package org.example.proman.service.Business;

import org.example.proman.service.Entity.RoleEntity;
import org.example.proman.service.Entity.UserAuthTokenEntity;
import org.example.proman.service.Entity.UserEntity;
import org.example.proman.service.dao.UserDao;
import org.example.proman.service.exception.ResourceNotFoundException;
import org.example.proman.service.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBusinesService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;
    public UserEntity getUser(final String userUuid,final String authorizationToken) throws ResourceNotFoundException,UnauthorizedException {
        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(authorizationToken);
        RoleEntity role = userAuthTokenEntity.getUser().getRole();
        if(role!=null || role.getUuid() == 101){
            UserEntity userEntity =  userDao.getUser(userUuid);
            if(userEntity == null){
                throw new ResourceNotFoundException("USR-001", "User not found");
            }
            return userEntity;
        }
        throw new UnauthorizedException("ATH-002", "you are not authorized to fetch user details");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity createUser(final UserEntity userEntity){

        String password = userEntity.getPassword();
        if(password == null){
            userEntity.setPassword("proman@123");
        }
        String[] encryptedText = passwordCryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);
        return userDao.createUser(userEntity);
    }
}
