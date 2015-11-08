package cn.lfy.qneng.service;

import java.util.List;

import cn.lfy.qneng.model.Alarm;
import cn.lfy.qneng.vo.AlarmQuery;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

public interface AlarmService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Long add(Alarm record);

    /**
     * 根据条件查询记录集
     */
    List<Alarm> findListByCriteria(Criteria criteria);
    
    /**
     * 根据条件查询记录集
     */
    PageInfo<Alarm> findListByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    Alarm findById(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByIdSelective(Alarm record);
    
    /**
     * 根据条件查询
     * @param alarmQuery
     * @return
     */
    List<Alarm> list(AlarmQuery alarmQuery);
    
}