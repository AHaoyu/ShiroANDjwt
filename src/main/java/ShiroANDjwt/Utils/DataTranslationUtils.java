package ShiroANDjwt.Utils;

import ShiroANDjwt.Dto.PermissionDto;
import ShiroANDjwt.Dto.UserDto;
import ShiroANDjwt.Vo.PermissionVo;
import ShiroANDjwt.Vo.UserVo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class DataTranslationUtils {

    public static PermissionVo trans(PermissionDto permission, Class<PermissionVo> permissionVoClass) {
        PermissionVo pv = new PermissionVo();
        pv.setPermissionName(permission.getPermissionName());
        pv.setPermissionId(permission.getPermissionId());
        return pv;
    }

    public static List<UserVo> trans(List<UserDto> userDtoList, Class<UserVo> userVoClass) {
        List<UserVo> uv = new ArrayList<>();
        for(UserDto ut : userDtoList) {
            uv.add(new UserVo(ut));
        }
        return uv;
    }
}
