package ShiroANDjwt.mapper;

import ShiroANDjwt.Dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UserDto> qryUserByUserName(String userName);
}
