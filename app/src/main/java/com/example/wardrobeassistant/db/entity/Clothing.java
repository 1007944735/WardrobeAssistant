package com.example.wardrobeassistant.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.sql.Date;

import org.greenrobot.greendao.annotation.Generated;

//服装
@Entity
public class Clothing {
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
    //放入时间
    private Long clothingInputTime;
    //位置变更时间
    private Long clothingLocationChangeTime;

    @Generated(hash = 583858491)
    public Clothing(Long id, String clothingName, String clothingColorSystem,
                    String clothingType, String clothingOccasion,
                    String clothingWarmthLevel, String clothingLocation,
                    Long clothingInputTime, Long clothingLocationChangeTime) {
        this.id = id;
        this.clothingName = clothingName;
        this.clothingColorSystem = clothingColorSystem;
        this.clothingType = clothingType;
        this.clothingOccasion = clothingOccasion;
        this.clothingWarmthLevel = clothingWarmthLevel;
        this.clothingLocation = clothingLocation;
        this.clothingInputTime = clothingInputTime;
        this.clothingLocationChangeTime = clothingLocationChangeTime;
    }

    @Generated(hash = 1131756184)
    public Clothing() {
    }

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
                ", clothingInputTime=" + clothingInputTime +
                ", clothingLocationChangeTime=" + clothingLocationChangeTime +
                '}';
    }
}
