package com.cheetah.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheetah.service.ISysUserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cheetah.mapper.ISysUserMapper;
import com.cheetah.domain.entity.SysUser;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 用户Service业务层处理
 * 
 * @author cheetah
 * @date 2022-11-10
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<ISysUserMapper, SysUser> implements ISysUserService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = this.baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        sysUser.setRoleList(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_PRODUCT"));
        return sysUser;
    }
}
