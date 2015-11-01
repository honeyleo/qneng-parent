package cn.lfy.qneng.service;

import java.util.List;

import cn.lfy.qneng.model.Bunch;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

public interface BunchService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Long add(Bunch record);

    /**
     * 根据条件查询记录集
     */
    List<Bunch> findListByCriteria(Criteria criteria);
    
    /**
     * 根据条件查询记录集
     */
    PageInfo<Bunch> findListByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    Bunch findById(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByIdSelective(Bunch record);
    /**
     * 缓存
     * @param id
     * @return
     */
    Bunch getByIdInCache(Long id);
}