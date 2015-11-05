package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.qneng.model.Module;

import com.manager.model.Criteria;

public interface ModuleDAO {

	/**
     * 根据条件查询记录总数
     */
    int countByExample(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Module record);

    /**
     * 根据条件查询记录集
     */
    List<Module> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    Module selectByPrimaryKey(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Module record);
    
    /**
     * 根据组件no查询记录
     * @param no 设备唯一码
     */
    Module selectByNo(String no);
}
