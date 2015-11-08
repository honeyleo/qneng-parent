package cn.lfy.qneng.service;

import java.util.List;

import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.vo.BunchInfo;
import cn.lfy.qneng.vo.ModuleInfo;
import cn.lfy.qneng.vo.StationInfo;

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
    Double getStationTotal(Long stationId);

    /**
     * 获取电站年发电量
     * @param stationId
     * @return
     */
    Double  getStationTotalForYear(Long stationId);
    /**
     * 获取电站月发电量
     * @param stationId
     * @return
     */
    Double  getStationTotalForMonth(Long stationId);
    /**
     * 获取当前电站信息
     * @param stationId
     * @return
     */
    StationInfo getStationInfo(Long stationId);
    /**
     * 获取某一天的发电功率数据
     * @param date yyyy-MM-dd
     * @param stationId
     * @return
     */
    double[] getStationPowerForDate(String date, Long stationId);
    /**
     * 获取某月电站发电量数据
     * @param date yyyy-MM
     * @param stationId
     * @return
     */
    double[] getStationForMonth(String date, Long stationId);
    /**
     * 获取某年电站发电量数据
     * @param date yyyy
     * @param stationId
     * @return
     */
    double[] getStationForYear(String date, Long stationId);
    
    //----------------------------组串接口开始-----------------------------------------
    /**
     * 获取总发电量
     * @param bunchId
     * @return
     */
    Double getBunchTotal(Long bunchId);

    /**
     * 获取年发电量
     * @param bunchId
     * @return
     */
    Double  getBunchTotalForYear(Long bunchId);
    /**
     * 获取月发电量
     * @param bunchId
     * @return
     */
    Double  getBunchTotalForMonth(Long bunchId);
    /**
     * 获取当前组串信息
     * @param bunchId
     * @return
     */
    BunchInfo getBunchInfo(Long bunchId);
    /**
     * 获取某一天的发电功率数据
     * @param date yyyy-MM-dd
     * @param bunchId
     * @return
     */
    double[] getBunchPowerForDate(String date, Long bunchId);
    
  //----------------------------组件接口开始-----------------------------------------
    /**
     * 获取总发电量
     * @param moduleId
     * @return
     */
    Double getModuleTotal(Long moduleId);

    /**
     * 获取年发电量
     * @param moduleId
     * @return
     */
    Double  getModuleTotalForYear(Long moduleId);
    /**
     * 获取月发电量
     * @param moduleId
     * @return
     */
    Double  getModuleTotalForMonth(Long moduleId);
    /**
     * 获取当前组串信息
     * @param moduleId
     * @return
     */
    ModuleInfo getModuleInfo(Long moduleId);
    /**
     * 获取某一天的发电功率数据
     * @param date yyyy-MM-dd
     * @param moduleId
     * @return
     */
    double[] getModulePowerForDate(String date, Long moduleId);
}