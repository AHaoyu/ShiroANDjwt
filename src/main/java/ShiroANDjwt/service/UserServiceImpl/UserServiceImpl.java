package ShiroANDjwt.service.UserServiceImpl;

import ShiroANDjwt.Dto.PermissionDto;
import ShiroANDjwt.Dto.RoleDto;
import ShiroANDjwt.Dto.TokenDto;
import ShiroANDjwt.Dto.UserDto;
import ShiroANDjwt.JWT.JwtAuthenticator;
import ShiroANDjwt.Shiro.AuthConstant;
import ShiroANDjwt.Vo.PermissionVo;
import ShiroANDjwt.Vo.RoleVo;
import ShiroANDjwt.dao.PermissionDao;
import ShiroANDjwt.dao.RoleDao;
import ShiroANDjwt.dao.UserDao;
import ShiroANDjwt.pojo.UserQuery;
import ShiroANDjwt.Vo.UserVo;
import ShiroANDjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    //登录，根据用户信息查询用户并关联角色和权限
    public List<UserVo> login(UserQuery userQuery) {
        Map<Long, UserVo> userVoMap = new HashMap<Long, UserVo>();
        //根据查询条件查出用户
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        if(userQuery.getUserName()!=null && !userQuery.getUserName().equals("")){
            userDtoList = userDao.qryUserByUserName(userQuery.getUserName());
        }
        if(userDtoList.size() <= 0){
            return new ArrayList<UserVo>(userVoMap.values());
        }
        List<Long> userIds = new ArrayList<>();
        for(UserDto u : userDtoList){
            userIds.add(u.getUserId());
            userVoMap.put(u.getUserId(), new UserVo(u));
        }
        List<Long> roleIds = new ArrayList<>();
        Map<Long, RoleVo> roleVoMap = new HashMap<>();
        // 查出用户的全部角色，然后添加到用户的角色列表中
        List<RoleDto> roleDtoList = roleDao.qryRoleByUserIds(userIds);
        for(RoleDto role : roleDtoList) {
            roleIds.add(role.getRoleId());
            RoleVo roleVo = new RoleVo(role);
            roleVoMap.put(role.getRoleId(), roleVo);
            UserVo userVo = userVoMap.get(role.getUserId());
            if (null == userVo) {
                continue;
            }
            if (null == userVo.getRoleVoList()) {
                userVo.setRoleVoList(new ArrayList<RoleVo>());
            }
            userVo.getRoleVoList().add(roleVo);
        }
        if(roleIds.size() <= 0){
            return new ArrayList<UserVo>(userVoMap.values());
        }
        // 查出角色对应的资源权限，添加到对应的角色中
        List<PermissionDto> permissionDtos = permissionDao.qryPermissionByRoleIds(roleIds);
        for(PermissionDto permission : permissionDtos) {
            RoleVo roleVo = roleVoMap.get(permission.getRoleId());
            if (null == roleVo) {
                continue;
            }
            List<PermissionVo> permissionVoList = roleVo.getPermissionVoList();
            if (null == permissionVoList) {
                permissionVoList = new ArrayList<PermissionVo>();
            }
            permissionVoList.add(DataTranslationUtils.trans(permission, PermissionVo.class));
            roleVo.setPermissionVoList(permissionVoList);
        }
        return new ArrayList<UserVo>(userVoMap.values());
    }

    //根据用户名查询用户，不关联角色
    @Override
    public List<UserVo> qryUserByUserName(String userName) {
        List<UserDto> userDtoList = userDao.qryUserByUserName(userName);
        return DataTranslationUtils.trans(userDtoList, UserVo.class);
    }

    //处理token,调用生成token的方法，返回token，在这里引入了redis,如果有必要的话想将token保存到redis里面。
    @Override
    public String saveToken(UserVo userVo) {
        try{
            Date setTime = new Date();
            Date expireTime = new Date();
            expireTime.setTime(setTime.getTime() + AuthConstant.EXPIRE_TIME);
            String token = JwtAuthenticator.sign(userVo, expireTime);
            //如果JedisPoll存在，将token存储起来。
            if(jedisUtil.getJedisPool() != null){
                TokenDto tokenDto = new TokenDto();
                tokenDto.setUserId(userVo.getUserId());
                tokenDto.setToken(token);
                tokenDto.setUpdateTime(setTime);
                tokenDto.setExpireTime(expireTime);
                jedisUtil.set(
                        String.valueOf(userVo.getUserId()).getBytes(),
                        SerializeUtil.serialize(tokenDto),
                        JedisConfig.database);
            }
            return token;
        }catch (Exception e){
        }
        return null;
    }

    //删除token，主要是用户注销的时候，设置token立马过期，并删除Jedis里面存储的token
    @Override
    public void deleteToken(UserVo userVo) {
        Date currentTime = new Date();
        JwtAuthenticator.sign(userVo, currentTime);
        if(jedisUtil.getJedisPool() != null){
            jedisUtil.del(JedisConfig.database,String.valueOf(userVo.getUserId()).getBytes());
        }
    }
}
