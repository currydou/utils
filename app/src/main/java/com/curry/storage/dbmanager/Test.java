package com.curry.storage.dbmanager;

import android.content.Context;

import com.curry.storage.dao.UserDao;
import com.curry.storage.entity.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by curry on 2016/10/20.
 */

public class Test {

    private final GreenDaoManager manager;

    public Test(Context context) {
        manager = GreenDaoManager.getInstance();
        manager.init(context);
    }

    public boolean insert(User user) {
        boolean flag = false;
        try {
            flag = manager.getDaoSession().insert(user) != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public void insertMultiplyUser(final List<User> userList) {
        manager.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < userList.size(); i++) {
                    manager.getDaoSession().insertOrReplace(userList.get(i));
                }
            }
        });
    }

    public void deleteUser(User user) {
        try {
            manager.getDaoSession().delete(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            manager.getDaoSession().update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id来查询单个人的信息
     *
     * @param key
     * @return
     */
    public User queryOneUser(Long key) {
        try {
            return manager.getDaoSession().load(User.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取所有user的数据集合
     */
    public List<User> getUserList() {
        try {
            return manager.getDaoSession().loadAll(User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //上面比较好记（增删改，只有具体条件查询比较烦）


    //单个查询，全部查询，条件查询，用查询构建器


    public List<User> query1() {
        try {
            manager.getDaoSession().queryRaw(User.class,
                    "where _id > ? and name like ?", new String[]{"2", "%的%"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> queryBuilder(long key) {
        try {
            QueryBuilder<User> builder = manager.getDaoSession().queryBuilder(User.class);
            builder.where(UserDao.Properties.Id.ge(key)).where(UserDao.Properties.Name.like("%的%"));
            return builder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> queryBuilder2(long key) {
        try {
            QueryBuilder<User> builder = manager.getDaoSession().queryBuilder(User.class);
            builder.where(UserDao.Properties.Id.ge(key), UserDao.Properties.Name.like("%得到%"));
            return builder.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
