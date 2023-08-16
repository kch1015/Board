package study.board.common.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import study.board.entity.User;
import study.board.service.dto.user.UserInform;

import java.util.List;

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private final UserInform userInform;

    public UserAdapter(User user) {
        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(UserPermission.USER.getValue())));
        userInform = new UserInform(user);
    }
}
