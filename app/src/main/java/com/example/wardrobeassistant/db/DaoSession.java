package com.example.wardrobeassistant.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.wardrobeassistant.db.entity.Clothing;
import com.example.wardrobeassistant.db.entity.Suit;

import com.example.wardrobeassistant.db.ClothingDao;
import com.example.wardrobeassistant.db.SuitDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig clothingDaoConfig;
    private final DaoConfig suitDaoConfig;

    private final ClothingDao clothingDao;
    private final SuitDao suitDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        clothingDaoConfig = daoConfigMap.get(ClothingDao.class).clone();
        clothingDaoConfig.initIdentityScope(type);

        suitDaoConfig = daoConfigMap.get(SuitDao.class).clone();
        suitDaoConfig.initIdentityScope(type);

        clothingDao = new ClothingDao(clothingDaoConfig, this);
        suitDao = new SuitDao(suitDaoConfig, this);

        registerDao(Clothing.class, clothingDao);
        registerDao(Suit.class, suitDao);
    }
    
    public void clear() {
        clothingDaoConfig.clearIdentityScope();
        suitDaoConfig.clearIdentityScope();
    }

    public ClothingDao getClothingDao() {
        return clothingDao;
    }

    public SuitDao getSuitDao() {
        return suitDao;
    }

}
