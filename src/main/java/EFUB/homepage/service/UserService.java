package EFUB.homepage.service;

import EFUB.homepage.domain.User;
import EFUB.homepage.dto.UserRequestDto;
import EFUB.homepage.dto.UserResponseDto;
import EFUB.homepage.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto checkAndSaveUser(UserRequestDto userRequestDto) {
        User user = userRepository.findByNameAndPhoneNoAndPassword(
                userRequestDto.getName(),
                userRequestDto.getPhoneNo(),
                userRequestDto.getPassword()
                );

        if (user == null) { //기존에 저장되지 않은 유저
            User newUser = new User(userRequestDto.getName(),
                userRequestDto.getStudentId(),
                userRequestDto.getDepartment(),
                userRequestDto.getPhoneNo(),
                userRequestDto.getPassword(),
                null,
                null,
                false,
                userRequestDto.getPosition());
            userRepository.save(newUser); // 저장
            return new UserResponseDto(newUser); //userId랑 saveFinal 값 반환
        }

        return new UserResponseDto(user);



    }

	public boolean isSaveFinal(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return user.get().getSaveFinal();
        return false;
	}

	@Transactional
	public void saveFinal(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		optionalUser.ifPresent(User::saveFinal);
	}


}