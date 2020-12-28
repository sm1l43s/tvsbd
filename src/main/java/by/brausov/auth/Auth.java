package by.brausov.auth;

import by.brausov.dao.UserDAO;
import by.brausov.dao.UserDAOImpl;
import by.brausov.encoder.Encoder;
import by.brausov.entities.User;

public class Auth {

    UserDAO userDAO = new UserDAOImpl();

    public boolean Verify(User user) {
        User userFromDB = userDAO.getByName(user.getLogin());
        if(userFromDB.getId() != 0 && userFromDB.getPassword().equals(new Encoder().getEncodedString(user.getPassword()))) {
            return true;
        }
        return false;
    }

}
