package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.vo.DataInfo;

import com.manager.model.Criteria;
/**
 * 数据库操作组件相关接口
 * @author leo.liao
 *
 */
public interface ModuleDAO {

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
    int insert(Module record);

    /**
     * 根据条件查询记录集
     */
    List<Module> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    Module selectByPrimaryKey(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Module record);
    
    /**
     * 根据组件no查询记录
     * @param no 设备唯一码
     */
    Module selectByNo(String no);
    /**
     * 查询组串电压、电流、功率、平均温度
     * @param bunchId
     * @return
     */
    DataInfo getBunchDataInfo(Long bunchId);
    /**
     * 查询电站总功率
     * @param query
     * @return
     */
    Double getStationPower(Long stationId);
}
