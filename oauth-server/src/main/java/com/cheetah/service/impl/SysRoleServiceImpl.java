package com.cheetah.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cheetah.service.ISysRoleService;
import com.cheetah.domain.entity.SysRole;
import com.cheetah.mapper.ISysRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author cheetah
 * @date 2022-11-10
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<ISysRoleMapper, SysRole> implements ISysRoleService {

}
