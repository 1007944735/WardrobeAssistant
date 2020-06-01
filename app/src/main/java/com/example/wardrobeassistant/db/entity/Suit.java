package com.example.wardrobeassistant.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.wardrobeassistant.db.DaoSession;
import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.SuitDao;

//套装
@Entity
public class Suit implements Parcelable{
    @Id(autoincrement = true)
    private Long id;
    //名称
    private String suitName;
    //外套id
    private Long suitOvercoatId;
    @ToOne(joinProperty = "suitOvercoatId")
    private Clothing suitOvercoat;
    //上衣id
    private Long suitOuterwearId;
    @ToOne(joinProperty = "suitOuterwearId")
    private Clothing suitOuterwear;
    //裤子id
    private Long suitTrousersId;
    @ToOne(joinProperty = "suitTrousersId")
    private Clothing suitTrousers;
    //鞋子id
    private Long suitShoesId;
    @ToOne(joinProperty = "suitShoesId")
    private Clothing suitShoes;
    //是否是预设套装
    private boolean suitPreset;
    //创建时间
    private Long suitCreateTime;
    //套装标签
    private String suitTag;
    //套装是否取出
    private Boolean isTakeOut;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 904619652)
    private transient SuitDao myDao;

    @Generated(hash = 839977458)
    public Suit(Long id, String suitName, Long suitOvercoatId, Long suitOuterwearId,
            Long suitTrousersId, Long suitShoesId, boolean suitPreset, Long suitCreateTime,
            String suitTag, Boolean isTakeOut) {
        this.id = id;
        this.suitName = suitName;
        this.suitOvercoatId = suitOvercoatId;
        this.suitOuterwearId = suitOuterwearId;
        this.suitTrousersId = suitTrousersId;
        this.suitShoesId = suitShoesId;
        this.suitPreset = suitPreset;
        this.suitCreateTime = suitCreateTime;
        this.suitTag = suitTag;
        this.isTakeOut = isTakeOut;
    }

    @Generated(hash = 1012954962)
    public Suit() {
    }

    protected Suit(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        suitName = in.readString();
        if (in.readByte() == 0) {
            suitOvercoatId = null;
        } else {
            suitOvercoatId = in.readLong();
        }
        suitOvercoat = in.readParcelable(Clothing.class.getClassLoader());
        if (in.readByte() == 0) {
            suitOuterwearId = null;
        } else {
            suitOuterwearId = in.readLong();
        }
        suitOuterwear = in.readParcelable(Clothing.class.getClassLoader());
        if (in.readByte() == 0) {
            suitTrousersId = null;
        } else {
            suitTrousersId = in.readLong();
        }
        suitTrousers = in.readParcelable(Clothing.class.getClassLoader());
        if (in.readByte() == 0) {
            suitShoesId = null;
        } else {
            suitShoesId = in.readLong();
        }
        suitShoes = in.readParcelable(Clothing.class.getClassLoader());
        suitPreset = in.readByte() != 0;
        if (in.readByte() == 0) {
            suitCreateTime = null;
        } else {
            suitCreateTime = in.readLong();
        }
        suitTag = in.readString();
        byte tmpIsTakeOut = in.readByte();
        isTakeOut = tmpIsTakeOut == 0 ? null : tmpIsTakeOut == 1;
    }

    public static final Creator<Suit> CREATOR = new Creator<Suit>() {
        @Override
        public Suit createFromParcel(Parcel in) {
            return new Suit(in);
        }

        @Override
        public Suit[] newArray(int size) {
            return new Suit[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuitName() {
        return this.suitName;
    }

    public void setSuitName(String suitName) {
        this.suitName = suitName;
    }

    public Long getSuitOvercoatId() {
        return this.suitOvercoatId;
    }

    public void setSuitOvercoatId(Long suitOvercoatId) {
        this.suitOvercoatId = suitOvercoatId;
    }

    public Long getSuitOuterwearId() {
        return this.suitOuterwearId;
    }

    public void setSuitOuterwearId(Long suitOuterwearId) {
        this.suitOuterwearId = suitOuterwearId;
    }

    public Long getSuitTrousersId() {
        return this.suitTrousersId;
    }

    public void setSuitTrousersId(Long suitTrousersId) {
        this.suitTrousersId = suitTrousersId;
    }

    public Long getSuitShoesId() {
        return this.suitShoesId;
    }

    public void setSuitShoesId(Long suitShoesId) {
        this.suitShoesId = suitShoesId;
    }

    public boolean getSuitPreset() {
        return this.suitPreset;
    }

    public void setSuitPreset(boolean suitPreset) {
        this.suitPreset = suitPreset;
    }

    public Long getSuitCreateTime() {
        return this.suitCreateTime;
    }

    public void setSuitCreateTime(Long suitCreateTime) {
        this.suitCreateTime = suitCreateTime;
    }

    @Generated(hash = 1827855941)
    private transient Long suitOvercoat__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1534244991)
    public Clothing getSuitOvercoat() {
        Long __key = this.suitOvercoatId;
        if (suitOvercoat__resolvedKey == null
                || !suitOvercoat__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClothingDao targetDao = daoSession.getClothingDao();
            Clothing suitOvercoatNew = targetDao.load(__key);
            synchronized (this) {
                suitOvercoat = suitOvercoatNew;
                suitOvercoat__resolvedKey = __key;
            }
        }
        return suitOvercoat;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1883482980)
    public void setSuitOvercoat(Clothing suitOvercoat) {
        synchronized (this) {
            this.suitOvercoat = suitOvercoat;
            suitOvercoatId = suitOvercoat == null ? null : suitOvercoat.getId();
            suitOvercoat__resolvedKey = suitOvercoatId;
        }
    }

    @Generated(hash = 702594515)
    private transient Long suitOuterwear__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1610625936)
    public Clothing getSuitOuterwear() {
        Long __key = this.suitOuterwearId;
        if (suitOuterwear__resolvedKey == null
                || !suitOuterwear__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClothingDao targetDao = daoSession.getClothingDao();
            Clothing suitOuterwearNew = targetDao.load(__key);
            synchronized (this) {
                suitOuterwear = suitOuterwearNew;
                suitOuterwear__resolvedKey = __key;
            }
        }
        return suitOuterwear;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1819817886)
    public void setSuitOuterwear(Clothing suitOuterwear) {
        synchronized (this) {
            this.suitOuterwear = suitOuterwear;
            suitOuterwearId = suitOuterwear == null ? null : suitOuterwear.getId();
            suitOuterwear__resolvedKey = suitOuterwearId;
        }
    }

    @Generated(hash = 2119573120)
    private transient Long suitTrousers__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1798966414)
    public Clothing getSuitTrousers() {
        Long __key = this.suitTrousersId;
        if (suitTrousers__resolvedKey == null
                || !suitTrousers__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClothingDao targetDao = daoSession.getClothingDao();
            Clothing suitTrousersNew = targetDao.load(__key);
            synchronized (this) {
                suitTrousers = suitTrousersNew;
                suitTrousers__resolvedKey = __key;
            }
        }
        return suitTrousers;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1854436005)
    public void setSuitTrousers(Clothing suitTrousers) {
        synchronized (this) {
            this.suitTrousers = suitTrousers;
            suitTrousersId = suitTrousers == null ? null : suitTrousers.getId();
            suitTrousers__resolvedKey = suitTrousersId;
        }
    }

    @Generated(hash = 1604000227)
    private transient Long suitShoes__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1896325393)
    public Clothing getSuitShoes() {
        Long __key = this.suitShoesId;
        if (suitShoes__resolvedKey == null
                || !suitShoes__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ClothingDao targetDao = daoSession.getClothingDao();
            Clothing suitShoesNew = targetDao.load(__key);
            synchronized (this) {
                suitShoes = suitShoesNew;
                suitShoes__resolvedKey = __key;
            }
        }
        return suitShoes;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 182198247)
    public void setSuitShoes(Clothing suitShoes) {
        synchronized (this) {
            this.suitShoes = suitShoes;
            suitShoesId = suitShoes == null ? null : suitShoes.getId();
            suitShoes__resolvedKey = suitShoesId;
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
    @Generated(hash = 164046783)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSuitDao() : null;
    }

    @Override
    public String toString() {
        return "Suit{" +
                "id=" + id +
                ", suitName='" + suitName + '\'' +
                ", suitOvercoatId=" + suitOvercoatId +
                ", suitOvercoat=" + suitOvercoat.toString() +
                ", suitOuterwearId=" + suitOuterwearId +
                ", suitOuterwear=" + suitOuterwear.toString() +
                ", suitTrousersId=" + suitTrousersId +
                ", suitTrousers=" + suitTrousers.toString() +
                ", suitShoesId=" + suitShoesId +
                ", suitShoes=" + suitShoes.toString() +
                ", suitPreset=" + suitPreset +
                ", suitCreateTime=" + suitCreateTime +
                ", isTakeOut=" + isTakeOut +
                '}';
    }

    public String getSuitTag() {
        return this.suitTag;
    }

    public void setSuitTag(String suitTag) {
        this.suitTag = suitTag;
    }

    public Boolean getIsTakeOut() {
        return this.isTakeOut;
    }

    public void setIsTakeOut(Boolean isTakeOut) {
        this.isTakeOut = isTakeOut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(suitName);
        if (suitOvercoatId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(suitOvercoatId);
        }
        dest.writeParcelable(suitOvercoat, flags);
        if (suitOuterwearId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(suitOuterwearId);
        }
        dest.writeParcelable(suitOuterwear, flags);
        if (suitTrousersId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(suitTrousersId);
        }
        dest.writeParcelable(suitTrousers, flags);
        if (suitShoesId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(suitShoesId);
        }
        dest.writeParcelable(suitShoes, flags);
        dest.writeByte((byte) (suitPreset ? 1 : 0));
        if (suitCreateTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(suitCreateTime);
        }
        dest.writeString(suitTag);
        dest.writeByte((byte) (isTakeOut == null ? 0 : isTakeOut ? 1 : 2));
    }
}
