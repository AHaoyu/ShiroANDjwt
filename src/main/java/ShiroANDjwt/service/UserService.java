package ShiroANDjwt.service;

import ShiroANDjwt.pojo.UserQuery;
import ShiroANDjwt.Vo.UserVo;

import java.util.List;

public interface UserService {
    public List<UserVo> login(UserQuery userQuery);

    public List<UserVo> qryUserByUserName(String userName);

    public String saveToken(UserVo userVo);

    public void deleteToken(UserVo userVo);

}
