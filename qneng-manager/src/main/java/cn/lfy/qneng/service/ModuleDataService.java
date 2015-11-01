package cn.lfy.qneng.service;

import java.util.List;

import cn.lfy.qneng.model.ModuleData;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

public interface ModuleDataService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Long add(ModuleData record);

    /**
     * 根据条件查询记录集
     */
    List<ModuleData> findListByCriteria(Criteria criteria);
    
    /**
     * 根据条件查询记录集
     */
    PageInfo<ModuleData> findListByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    ModuleData findById(Long id);

    
}