package com.example.wardrobeassistant.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.sql.Date;

import org.greenrobot.greendao.annotation.Generated;

//服装
@Entity
public class Clothing implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    //名称
    private String clothingName;
    //色系
    private String clothingColorSystem;
    //类型
    private String clothingType;
    //场合
    private String clothingOccasion;
    //保暖等级
    private String clothingWarmthLevel;
    //位置
    private String clothingLocation;
    //图片位置
    private String clothingImageUrl;
    //放入时间
    private Long clothingInputTime;
    //位置变更时间
    private Long clothingLocationChangeTime;
    //上次查看时间
    private Long clothingViewTime;
    //是否取出
    private Boolean isTakeOut;

    @Generated(hash = 1630593880)
    public Clothing(Long id, String clothingName, String clothingColorSystem,
            String clothingType, String clothingOccasion,
            String clothingWarmthLevel, String clothingLocation,
            String clothingImageUrl, Long clothingInputTime,
            Long clothingLocationChangeTime, Long clothingViewTime,
            Boolean isTakeOut) {
        this.id = id;
        this.clothingName = clothingName;
        this.clothingColorSystem = clothingColorSystem;
        this.clothingType = clothingType;
        this.clothingOccasion = clothingOccasion;
        this.clothingWarmthLevel = clothingWarmthLevel;
        this.clothingLocation = clothingLocation;
        this.clothingImageUrl = clothingImageUrl;
        this.clothingInputTime = clothingInputTime;
        this.clothingLocationChangeTime = clothingLocationChangeTime;
        this.clothingViewTime = clothingViewTime;
        this.isTakeOut = isTakeOut;
    }

    @Generated(hash = 1131756184)
    public Clothing() {
    }

    protected Clothing(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        clothingName = in.readString();
        clothingColorSystem = in.readString();
        clothingType = in.readString();
        clothingOccasion = in.readString();
        clothingWarmthLevel = in.readString();
        clothingLocation = in.readString();
        clothingImageUrl = in.readString();
        if (in.readByte() == 0) {
            clothingInputTime = null;
        } else {
            clothingInputTime = in.readLong();
        }
        if (in.readByte() == 0) {
            clothingLocationChangeTime = null;
        } else {
            clothingLocationChangeTime = in.readLong();
        }
        if (in.readByte() == 0) {
            clothingViewTime = null;
        } else {
            clothingViewTime = in.readLong();
        }
        byte tmpIsTakeOut = in.readByte();
        isTakeOut = tmpIsTakeOut == 0 ? null : tmpIsTakeOut == 1;
    }

    public static final Creator<Clothing> CREATOR = new Creator<Clothing>() {
        @Override
        public Clothing createFromParcel(Parcel in) {
            return new Clothing(in);
        }

        @Override
        public Clothing[] newArray(int size) {
            return new Clothing[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClothingName() {
        return this.clothingName;
    }

    public void setClothingName(String clothingName) {
        this.clothingName = clothingName;
    }

    public String getClothingColorSystem() {
        return this.clothingColorSystem;
    }

    public void setClothingColorSystem(String clothingColorSystem) {
        this.clothingColorSystem = clothingColorSystem;
    }

    public String getClothingType() {
        return this.clothingType;
    }

    public void setClothingType(String clothingType) {
        this.clothingType = clothingType;
    }

    public String getClothingOccasion() {
        return this.clothingOccasion;
    }

    public void setClothingOccasion(String clothingOccasion) {
        this.clothingOccasion = clothingOccasion;
    }

    public String getClothingWarmthLevel() {
        return this.clothingWarmthLevel;
    }

    public void setClothingWarmthLevel(String clothingWarmthLevel) {
        this.clothingWarmthLevel = clothingWarmthLevel;
    }

    public String getClothingLocation() {
        return this.clothingLocation;
    }

    public void setClothingLocation(String clothingLocation) {
        this.clothingLocation = clothingLocation;
    }

    public Long getClothingInputTime() {
        return this.clothingInputTime;
    }

    public void setClothingInputTime(Long clothingInputTime) {
        this.clothingInputTime = clothingInputTime;
    }

    public Long getClothingLocationChangeTime() {
        return this.clothingLocationChangeTime;
    }

    public void setClothingLocationChangeTime(Long clothingLocationChangeTime) {
        this.clothingLocationChangeTime = clothingLocationChangeTime;
    }

    public String getClothingImageUrl() {
        return this.clothingImageUrl;
    }

    public void setClothingImageUrl(String clothingImageUrl) {
        this.clothingImageUrl = clothingImageUrl;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "id=" + id +
                ", clothingName='" + clothingName + '\'' +
                ", clothingColorSystem='" + clothingColorSystem + '\'' +
                ", clothingType='" + clothingType + '\'' +
                ", clothingOccasion='" + clothingOccasion + '\'' +
                ", clothingWarmthLevel='" + clothingWarmthLevel + '\'' +
                ", clothingLocation='" + clothingLocation + '\'' +
                ", clothingImageUrl='" + clothingImageUrl + '\'' +
                ", clothingInputTime=" + clothingInputTime +
                ", clothingLocationChangeTime=" + clothingLocationChangeTime +
                ", clothingViewTime=" + clothingViewTime +
                ", isTakeUp=" + isTakeOut +
                '}';
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
        dest.writeString(clothingName);
        dest.writeString(clothingColorSystem);
        dest.writeString(clothingType);
        dest.writeString(clothingOccasion);
        dest.writeString(clothingWarmthLevel);
        dest.writeString(clothingLocation);
        dest.writeString(clothingImageUrl);
        if (clothingInputTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(clothingInputTime);
        }
        if (clothingLocationChangeTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(clothingLocationChangeTime);
        }
        if (clothingViewTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(clothingViewTime);
        }
        dest.writeByte((byte) (isTakeOut == null ? 0 : isTakeOut ? 1 : 2));
    }

    public Long getClothingViewTime() {
        return this.clothingViewTime;
    }

    public void setClothingViewTime(Long clothingViewTime) {
        this.clothingViewTime = clothingViewTime;
    }
}
