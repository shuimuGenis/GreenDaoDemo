package com.example.greendaodemo.bean;

import com.greendao.gen.DaoSession;
import com.greendao.gen.HomeAddressDataDao;
import com.greendao.gen.UserDataDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import com.greendao.gen.BookDataDao;

/**
 * @author shuimu{lwp}
 * @time 2020/3/24  17:35
 * @desc
 */
@Entity
public class UserData {
    @Id
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "age")
    private int age;

    /**
     * 示例 一对一的关系
     */
    private Long addressId;
    @ToOne(joinProperty = "addressId")
    private HomeAddressData address;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1464190928)
    private transient UserDataDao myDao;

    /**
     * 示例 一对多
     * referencedJoinProperty = "userId" 表示：BookData中的userId字段的值 将会等于userData主键ID的值
     * 后续查询 bookData的时候,会把BookData中userID等于指定值的所有BookData数据查询出来。
     */
    @ToMany(referencedJoinProperty = "userId")
    private List<BookData> books;

    @Generated(hash = 1982219307)
    public UserData(Long id, String name, int age, Long addressId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.addressId = addressId;
    }

    @Generated(hash = 1838565001)
    public UserData() {
    }

    @Generated(hash = 1156467801)
    private transient Long address__resolvedKey;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 170871904)
    public HomeAddressData getAddress() {
        Long __key = this.addressId;
        if (address__resolvedKey == null || !address__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            HomeAddressDataDao targetDao = daoSession.getHomeAddressDataDao();
            HomeAddressData addressNew = targetDao.load(__key);
            synchronized (this) {
                address = addressNew;
                address__resolvedKey = __key;
            }
        }
        return address;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1575140526)
    public void setAddress(HomeAddressData address) {
        synchronized (this) {
            this.address = address;
            addressId = address == null ? null : address.getId();
            address__resolvedKey = addressId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 809121455)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserDataDao() : null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1094655810)
    public List<BookData> getBooks() {
        if (books == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BookDataDao targetDao = daoSession.getBookDataDao();
            List<BookData> booksNew = targetDao._queryUserData_Books(id);
            synchronized (this) {
                if (books == null) {
                    books = booksNew;
                }
            }
        }
        return books;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 353255226)
    public synchronized void resetBooks() {
        books = null;
    }
}
