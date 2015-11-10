package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.vo.ModuleQuery;
import cn.lfy.qneng.vo.PowerDataInfo;

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
