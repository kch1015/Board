package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import study.board.entity.User;
import study.board.repository.UserRepository;
import study.board.service.dto.UserCreateForm;
import study.board.service.dto.UserUpdateForm;
import study.board.service.dto.UserInform;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long join(UserCreateForm form) {
        validateDuplicateUsername(form.getUsername());
        User user = userRepository.save(new User(form.getUsername()));
        return user.getId();
    }

    @Transactional(readOnly = true)
    public void validateDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username))
            throw new IllegalStateException("already exists username");
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("no such user"));
    }

    @Transactional(readOnly = true)
    public List<UserInform> findAllToUserInform() {
       return userRepository.findAll()
               .stream()
               .map(user -> new UserInform(user))
               .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> findAllToUsername() {
        return userRepository.findAll()
                .stream()
                .map(user -> user.getUsername())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserInform toUserInform(Long id) {
        return new UserInform(findById(id));
    }

    public void modify(UserUpdateForm form, Long userId) {
        validateDuplicateUsername(form.getUsername());
        User user = findById(userId);
        user.updateUsername(form.getUsername());
    }

    public void withdraw(Long id) {
        userRepository.deleteById(id);
    }

}
