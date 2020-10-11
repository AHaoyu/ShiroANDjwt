package ShiroANDjwt.dao;

import ShiroANDjwt.Dto.UserDto;
import ShiroANDjwt.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public List<UserDto> qryUserByUserName(String userName) {
        return userMapper.qryUserByUserName(userName);
    }
}
