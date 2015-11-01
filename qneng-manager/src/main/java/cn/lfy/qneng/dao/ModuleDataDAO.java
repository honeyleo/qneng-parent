package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.qneng.model.ModuleData;

import com.manager.model.Criteria;

public interface ModuleDataDAO {

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
    int insert(ModuleData record);

    /**
     * 根据条件查询记录集
     */
    List<ModuleData> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    ModuleData selectByPrimaryKey(Long id);

}
