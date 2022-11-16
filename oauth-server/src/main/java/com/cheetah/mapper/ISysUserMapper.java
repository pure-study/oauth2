package com.cheetah.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cheetah.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户Mapper接口
 * 
 * @author cheetah
 * @date 2022-11-10
 */
@Mapper
public interface ISysUserMapper extends BaseMapper<SysUser> {

}