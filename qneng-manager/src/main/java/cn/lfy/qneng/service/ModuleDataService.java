package cn.lfy.qneng.service;

import java.util.Date;
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
    /**
     * 获取指定电站总发电量
     * @param stationId
     * @return
     */
    Double getTotal(Long stationId, Long bunchId, Long moduleId);

    /**
     * 获取年发电量
     * @param stationId
     * @return
     */
    Double  getTotalForYear(Long stationId, Long bunchId, Long moduleId);
    /**
     * 获取月发电量
     * @param stationId
     * @return
     */
    Double  getTotalForMonth(Long stationId, Long bunchId, Long moduleId);
    /**
     * 获取某一天的发电功率数据
     * @param date yyyy-MM-dd
     * @param stationId
     * @param bunchId
     * @param moduleId
     * @return
     */
    double[] getPowerForDate(String date, Long stationId, Long bunchId, Long moduleId);
    /**
     * 获取某天发电量
     * @param date yyyy-MM-dd
     * @param stationId
     * @return
     */
    Double getCapacityForDate(String date, Long stationId, Long bunchId, Long moduleId);
    /**
     * 获取某月发电量数据
     * @param date yyyy-MM
     * @param stationId
     * @return
     */
    double[] getCapacityForMonth(Date date, Long stationId, Long bunchId, Long moduleId);
    /**
     * 获取某年发电量数据
     * @param date yyyy
     * @param stationId
     * @return
     */
    double[] getCapacityForYear(String date, Long stationId, Long bunchId, Long moduleId);
    
}