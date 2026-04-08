package com.api.login;

import com.api.common.error.exceptions.UnauthorizedException;
import com.api.common.util.HashUtils;
import com.api.common.util.JwtUtils;
import com.api.login.dto.LoginRequest;
import com.api.login.dto.UserDto;
import com.api.login.mapper.UserMapper;
import com.api.student.StudentRepository;
import com.api.teacher.Teacher;
import com.api.teacher.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LoginService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserMapper userMapper;

    public LoginService(StudentRepository studentRepository, TeacherRepository teacherRepository, UserMapper userMapper) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public boolean isEmailAvailable(String email){
        return this.studentRepository.findByEmailIgnoreCase(email)
                .map(student -> (User) student)
                    .or(() -> this.teacherRepository.findByEmailIgnoreCase(email)
                        .map(teacher -> (User) teacher)).isEmpty();
    }

    @Transactional
    public UserDto login(LoginRequest request) {
        User user = this.studentRepository.findByEmailIgnoreCase(request.getEmail())
                .map(student -> (User) student)
                .or(() -> this.teacherRepository.findByEmailIgnoreCase(request.getEmail())
                        .map(teacher -> (User) teacher))
                .orElseThrow(() -> new UnauthorizedException("Invalid email"));
        if(HashUtils.check(request.getPassword(),user.getPassword())){
            UserDto dto = this.userMapper.toDto(user);
            dto.setToken(JwtUtils.tokenize(dto));
            return dto;
        }else{
            throw new UnauthorizedException("Invalid password");
        }
    }

    @Transactional
    public UserDto me(Auth auth) {
        System.out.println("LoginService.me.auth: "+auth);
        User user = this.studentRepository.findByEmailIgnoreCase(auth.getEmail())
                .map(student -> (User) student)
                .or(() -> this.teacherRepository.findByEmailIgnoreCase(auth.getEmail())
                        .map(teacher -> (User) teacher))
                .orElseThrow(() -> new UnauthorizedException("Invalid email"));

        return this.userMapper.toDto(user);
    }

    @Transactional
    public boolean createAdmin(){
        if(this.teacherRepository.findAll().isEmpty()){
            Teacher teacher = new Teacher();
            teacher.setFirstName("BetterJaksec");
            teacher.setLastName("Admin");
            teacher.setEmail("admin@betterjaksec.com");
            teacher.setPassword(HashUtils.hash("adminpassword"));
            teacher.setAdmin(true);
            this.teacherRepository.save(teacher);
            return true;
        }
        return false;
    }

}
