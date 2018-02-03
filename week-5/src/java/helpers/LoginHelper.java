
package helpers;

import java.util.Optional;
import models.User;
import repositories.UsersRepository;

public class LoginHelper {

    /**
     * checking in the DB for authentication
     * @param username
     * @param password
     * @return user is ok
     */
    public static User isAuthorized(String username, String password) {
        
        Optional<User> one = UsersRepository.getInstance().getOne(user -> user.username.equals(username) && user.password.equals(password));
        
        if (one.isPresent()) {
            one.get().lastLoginTime = System.currentTimeMillis();
            UsersRepository.getInstance().save(one.get());
        }
        
        return one.orElse(null);
    }
}
