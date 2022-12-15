package com.guissisoftware.Zeko.services;

import com.guissisoftware.Zeko.api.model.RegistrationBody;
import com.guissisoftware.Zeko.exception.UserAlreadyExist;
import com.guissisoftware.Zeko.model.LocalUser;
import com.guissisoftware.Zeko.model.dao.LocalUserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private LocalUserDAO localUserDAO;
    public UserService(LocalUserDAO localUserDAO) {
        this.localUserDAO = localUserDAO;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExist {
        if(localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent() ||
                localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExist();
        }
        LocalUser user = new LocalUser();
        user.setUserName(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setEmail(registrationBody.getEmail());
        //Password need to be encrypted
        user.setPassword(registrationBody.getPassword());
        return localUserDAO.save(user);
    }
}
