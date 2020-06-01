package com.example.wardrobeassistant.util;

import com.example.wardrobeassistant.Constant;

public class StringUtils {
    //将服装色系 中文转成英文
    public static String colorSystemToDb(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        switch (s) {
            case Constant.CN_COLOR_COLD_SYSTEM:
                return Constant.COLOR_COLD_SYSTEM;
            case Constant.CN_COLOR_WARM_SYSTEM:
                return Constant.COLOR_WARM_SYSTEM;
            default:
                return "";
        }
    }

    //将服装色系 英文转成中文
    public static String colorSystemFromDb(String sDb) {
        if (sDb == null || sDb.isEmpty()) {
            return "";
        }
        switch (sDb) {
            case Constant.COLOR_COLD_SYSTEM:
                return Constant.CN_COLOR_COLD_SYSTEM;
            case Constant.COLOR_WARM_SYSTEM:
                return Constant.CN_COLOR_WARM_SYSTEM;
            default:
                return "";
        }
    }

    //将服装类型 中文转成英文
    public static String clothingTypeToDb(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        switch (s) {
            case Constant.CN_TYPE_OVERCOAT:
                return Constant.TYPE_OVERCOAT;
            case Constant.CN_TYPE_OUTERWEAR:
                return Constant.TYPE_OUTERWEAR;
            case Constant.CN_TYPE_TROUSERS:
                return Constant.TYPE_TROUSERS;
            case Constant.CN_TYPE_SHOES:
                return Constant.TYPE_SHOES;
            default:
                return "";
        }
    }

    //将服装类型 英文转成中文
    public static String clothingTypeFromDb(String sDb) {
        if (sDb == null || sDb.isEmpty()) {
            return "";
        }
        switch (sDb) {
            case Constant.TYPE_OVERCOAT:
                return Constant.CN_TYPE_OVERCOAT;
            case Constant.TYPE_OUTERWEAR:
                return Constant.CN_TYPE_OUTERWEAR;
            case Constant.TYPE_TROUSERS:
                return Constant.CN_TYPE_TROUSERS;
            case Constant.TYPE_SHOES:
                return Constant.CN_TYPE_SHOES;
            default:
                return "";
        }
    }

    //将服装场景 中文转成英文
    public static String occasionToDb(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        switch (s) {
            case Constant.CN_OCCASION_DAILY:
                return Constant.OCCASION_DAILY;
            case Constant.CN_OCCASION_WORK:
                return Constant.OCCASION_WORK;
            case Constant.CN_OCCASION_SPORT:
                return Constant.OCCASION_SPORT;
            default:
                return "";
        }
    }

    //将服装场景 英文转成中文
    public static String occasionFromDb(String sDb) {
        if (sDb == null || sDb.isEmpty()) {
            return "";
        }
        switch (sDb) {
            case Constant.OCCASION_DAILY:
                return Constant.CN_OCCASION_DAILY;
            case Constant.OCCASION_WORK:
                return Constant.CN_OCCASION_WORK;
            case Constant.OCCASION_SPORT:
                return Constant.CN_OCCASION_SPORT;
            default:
                return "";
        }
    }

    //将服装保暖等级 中文转成英文
    public static String warmLevelToDb(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        switch (s) {
            case Constant.CN_WARMTH_LEVEL_SPRING_AUTUMN:
                return Constant.WARMTH_LEVEL_SPRING_AUTUMN;
            case Constant.CN_WARMTH_LEVEL_SUMMER:
                return Constant.WARMTH_LEVEL_SUMMER;
            case Constant.CN_WARMTH_LEVEL_WINTER:
                return Constant.WARMTH_LEVEL_WINTER;
            default:
                return "";
        }
    }

    //将服装保暖等级 英文转成中文
    public static String warmLevelFromDb(String sDb) {
        if (sDb == null || sDb.isEmpty()) {
            return "";
        }
        switch (sDb) {
            case Constant.WARMTH_LEVEL_SPRING_AUTUMN:
                return Constant.CN_WARMTH_LEVEL_SPRING_AUTUMN;
            case Constant.WARMTH_LEVEL_SUMMER:
                return Constant.CN_WARMTH_LEVEL_SUMMER;
            case Constant.WARMTH_LEVEL_WINTER:
                return Constant.CN_WARMTH_LEVEL_WINTER;
            default:
                return "";
        }
    }
    //将套装标签 中文转为英文
    public static String suitTagToDb(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        switch (s) {
            case Constant.CN_OCCASION_DAILY:
                return Constant.OCCASION_DAILY;
            case Constant.CN_OCCASION_WORK:
                return Constant.OCCASION_WORK;
            case Constant.CN_OCCASION_SPORT:
                return Constant.OCCASION_SPORT;
            case Constant.CN_WARMTH_LEVEL_SPRING_AUTUMN:
                return Constant.WARMTH_LEVEL_SPRING_AUTUMN;
            case Constant.CN_WARMTH_LEVEL_SUMMER:
                return Constant.WARMTH_LEVEL_SUMMER;
            case Constant.CN_WARMTH_LEVEL_WINTER:
                return Constant.WARMTH_LEVEL_WINTER;
            default:
                return "";
        }
    }

    //将套装标签 英文转成中文
    public static String suitTagFromDb(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        switch (s) {
            case Constant.OCCASION_DAILY:
                return Constant.CN_OCCASION_DAILY;
            case Constant.OCCASION_WORK:
                return Constant.CN_OCCASION_WORK;
            case Constant.OCCASION_SPORT:
                return Constant.CN_OCCASION_SPORT;
            case Constant.WARMTH_LEVEL_SPRING_AUTUMN:
                return Constant.CN_WARMTH_LEVEL_SPRING_AUTUMN;
            case Constant.WARMTH_LEVEL_SUMMER:
                return Constant.CN_WARMTH_LEVEL_SUMMER;
            case Constant.WARMTH_LEVEL_WINTER:
                return Constant.CN_WARMTH_LEVEL_WINTER;
            default:
                return "";
        }
    }
}
