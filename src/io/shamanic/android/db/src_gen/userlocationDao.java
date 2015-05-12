package io.shamanic.android.db.src_gen;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import io.shamanic.android.db.src_gen.userlocation;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table USERLOCATION.
*/
public class userlocationDao extends AbstractDao<userlocation, Long> {

    public static final String TABLENAME = "USERLOCATION";

    /**
     * Properties of entity userlocation.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Latitude = new Property(1, Double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(2, Double.class, "longitude", false, "LONGITUDE");
        public final static Property LocationString = new Property(3, String.class, "locationString", false, "LOCATION_STRING");
        public final static Property Timestamp = new Property(4, java.util.Date.class, "timestamp", false, "TIMESTAMP");
        public final static Property UserId = new Property(5, Long.class, "userId", false, "USER_ID");
        public final static Property UserLocationId = new Property(6, Long.class, "userLocationId", false, "USER_LOCATION_ID");
    };

    private DaoSession daoSession;

    private Query<userlocation> user_UserlocationListQuery;
    private Query<userlocation> hull_UserlocationListQuery;

    public userlocationDao(DaoConfig config) {
        super(config);
    }
    
    public userlocationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'USERLOCATION' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'LATITUDE' REAL," + // 1: latitude
                "'LONGITUDE' REAL," + // 2: longitude
                "'LOCATION_STRING' TEXT," + // 3: locationString
                "'TIMESTAMP' INTEGER," + // 4: timestamp
                "'USER_ID' INTEGER," + // 5: userId
                "'USER_LOCATION_ID' INTEGER);"); // 6: userLocationId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USERLOCATION'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, userlocation entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Double latitude = entity.getLatitude();
        if (latitude != null) {
            stmt.bindDouble(2, latitude);
        }
 
        Double longitude = entity.getLongitude();
        if (longitude != null) {
            stmt.bindDouble(3, longitude);
        }
 
        String locationString = entity.getLocationString();
        if (locationString != null) {
            stmt.bindString(4, locationString);
        }
 
        java.util.Date timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(5, timestamp.getTime());
        }
 
        Long userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(6, userId);
        }
    }

    @Override
    protected void attachEntity(userlocation entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public userlocation readEntity(Cursor cursor, int offset) {
        userlocation entity = new userlocation( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1), // latitude
            cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2), // longitude
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // locationString
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // timestamp
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5) // userId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, userlocation entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLatitude(cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1));
        entity.setLongitude(cursor.isNull(offset + 2) ? null : cursor.getDouble(offset + 2));
        entity.setLocationString(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTimestamp(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setUserId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(userlocation entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(userlocation entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "userlocationList" to-many relationship of user. */
    public List<userlocation> _queryUser_UserlocationList(Long userLocationId) {
        synchronized (this) {
            if (user_UserlocationListQuery == null) {
                QueryBuilder<userlocation> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserLocationId.eq(null));
                user_UserlocationListQuery = queryBuilder.build();
            }
        }
        Query<userlocation> query = user_UserlocationListQuery.forCurrentThread();
        query.setParameter(0, userLocationId);
        return query.list();
    }

    /** Internal query to resolve the "userlocationList" to-many relationship of hull. */
    public List<userlocation> _queryHull_UserlocationList(Long userId) {
        synchronized (this) {
            if (hull_UserlocationListQuery == null) {
                QueryBuilder<userlocation> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.UserId.eq(null));
                hull_UserlocationListQuery = queryBuilder.build();
            }
        }
        Query<userlocation> query = hull_UserlocationListQuery.forCurrentThread();
        query.setParameter(0, userId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T1", daoSession.getHullDao().getAllColumns());
            builder.append(" FROM USERLOCATION T");
            builder.append(" LEFT JOIN USER T0 ON T.'USER_ID'=T0.'_id'");
            builder.append(" LEFT JOIN HULL T1 ON T.'USER_ID'=T1.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected userlocation loadCurrentDeep(Cursor cursor, boolean lock) {
        userlocation entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        user user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
        entity.setUser(user);
        offset += daoSession.getUserDao().getAllColumns().length;

        hull hull = loadCurrentOther(daoSession.getHullDao(), cursor, offset);
        entity.setHull(hull);

        return entity;    
    }

    public userlocation loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<userlocation> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<userlocation> list = new ArrayList<userlocation>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<userlocation> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<userlocation> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}