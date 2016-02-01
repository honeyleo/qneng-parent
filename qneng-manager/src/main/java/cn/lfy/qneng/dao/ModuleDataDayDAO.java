package cn.lfy.qneng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lfy.qneng.vo.ModuleQuery;
import cn.lfy.qneng.vo.PowerDataInfo;
/**
 * 数据库操作组件发电数据统计相关接口
 * @author leo.liao
 *
 */
public interface ModuleDataDayDAO {

    /**
     * 统计某一时间段的数据到天记录表
     */
    int insertOrUpdate(@Param("startTime") String startTime, @Param("endTime")String endTime);

    /**
     * 查询发电量
     * @param query
     * @return
     */
    Double getCapacity(ModuleQuery query);
    /**
     * 获取某一天功率相关的：电压和电流（必须设置startTime和endTime）
     * @param query
     * @return
     */
    List<PowerDataInfo> getDayPowerData(ModuleQuery query);
    
    /**
     * 获取某一月发电量相关的：发电量（必须设置startTime和endTime）
     * @param query
     * @return
     */
    List<PowerDataInfo> getMonthCapacityData(ModuleQuery query);
    
    /**
     * 获取某一年发电量相关的：发电量（必须设置startTime和endTime）
     * @param query
     * @return
     */
    List<PowerDataInfo> getYearCapacityData(ModuleQuery query);
    

}
