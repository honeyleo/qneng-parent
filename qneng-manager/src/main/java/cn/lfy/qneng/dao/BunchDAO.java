package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.qneng.model.Bunch;

import com.manager.model.Criteria;
/**
 * 数据库操作组串相关接口
 * @author leo.liao
 *
 */
public interface BunchDAO {

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
    int insert(Bunch record);

    /**
     * 根据条件查询记录集
     */
    List<Bunch> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    Bunch selectByPrimaryKey(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Bunch record);
}
