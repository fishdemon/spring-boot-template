package priv.maanjin.shirojwt.service;

import org.springframework.stereotype.Service;

import priv.maanjin.shirojwt.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * @author anjin.ma
 * @date 2019/7/23
 * @description
 */

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public String getPassword(String username){
        return  userMapper.getPassword(username);
    }

    public int checkUserBanStatus(String username){
        return userMapper.checkUserBanStatus(username);
    }

    public String getRole(String username){
        return userMapper.getRole(username);
    }

    public String getRolePermission(String username){
        return userMapper.getRolePermission(username);
    }

    public String getPermission(String username){
        return userMapper.getPermission(username);
    }

}
