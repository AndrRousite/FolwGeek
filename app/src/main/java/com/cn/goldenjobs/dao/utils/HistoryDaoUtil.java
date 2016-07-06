package com.cn.goldenjobs.dao.utils;

import com.cn.goldenjobs.AppControler;
import com.cn.goldenjobs.dao.History;
import com.cn.goldenjobs.dao.HistoryDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * 数据库工具类
 *
 * @author liu-feng 2016-1-16下午4:21:25
 */
public class HistoryDaoUtil {

    private HistoryDaoUtil() {
    }

    /**
     * 添加数据
     */
    public void add(History item) {
        AppControler.getInstance().getDaoSession().getHistoryDao().insert(item);
    }

    /**
     * 查询所有:获取QueryBuilder，可以+限制条件
     */
    public List<History> queryAll() {
        QueryBuilder<History> qb = AppControler.getInstance().getDaoSession().getHistoryDao().queryBuilder();

        //qb.where(Properties.Id.eq(Id),...);  根据字段查询
        //qb.buildCount().count(); 数量
        //qb.offset(pageNum + 1); 分页
        //qb.limit(pageSize)    每页的条数
        //qb.orderDesc(Properties.Time)    根据字段进行jiangxu

        return qb.list();
    }

    /** 查询所有 */
    public List<History> loadAll() {
        return AppControler.getInstance().getDaoSession().getHistoryDao().loadAll();
    }

    /**  根据字段删除 */
    public void deleteByField(Object field){
        QueryBuilder<History> qb = AppControler.getInstance().getDaoSession().getHistoryDao().queryBuilder();
        qb.where(HistoryDao.Properties.Id.eq(field)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /** 删除所有 */
    public void deleteAll() {
        AppControler.getInstance().getDaoSession().getHistoryDao().deleteAll();
    }

    /**
     * 根据主键来更新数据
     * @param item 该数据的ID（主键需要与需要更新的数据ID一致）
     */
    public void updateById(History item){
        AppControler.getInstance().getDaoSession().getHistoryDao().update(item);
    }

    /**
     * 更新数据 有则更新 无则插入 该数据必须有主键
     */
    public void insertAndReplace(History item) {
        AppControler.getInstance().getDaoSession().getHistoryDao().insertOrReplace(item);
    }

}