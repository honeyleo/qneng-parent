package cn.lfy.qneng.service;

import java.util.List;

import cn.lfy.qneng.model.Station;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

public interface StationService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Long add(Station record);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 根据条件查询记录集
     */
    List<Station> findListByCriteria(Criteria criteria);
    
    /**
     * 根据条件查询记录集
     */
    PageInfo<Station> findListByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    Station findById(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByIdSelective(Station record);
    
    /**
     * 缓存
     * @param id
     * @return
     */
    Station getByIdInCache(Long id);
}