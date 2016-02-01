package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.qneng.model.Alarm;
import cn.lfy.qneng.vo.AlarmQuery;
import cn.lfy.qneng.vo.AlarmVo;

import com.manager.model.Criteria;
/**
 * 数据库操作告警相关接口
 * @author leo.liao
 *
 */
public interface AlarmDAO {

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
    int insert(Alarm record);

    /**
     * 根据条件查询记录集
     */
    List<Alarm> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    Alarm selectByPrimaryKey(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Alarm record);
    /**
     * 根据条件查询
     * @param alarmQuery
     * @return
     */
    List<AlarmVo> list(AlarmQuery alarmQuery);
    
    /**
     * 根据条件查询警告数量
     * @param alarmQuery
     * @return
     */
    Integer getAlarmCount(AlarmQuery alarmQuery);
}
