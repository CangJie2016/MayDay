package com.anye.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.cangjie.data.entity.Bill;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BILL".
*/
public class BillDao extends AbstractDao<Bill, Long> {

    public static final String TABLENAME = "BILL";

    /**
     * Properties of entity Bill.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Money = new Property(1, Double.class, "money", false, "MONEY");
        public final static Property BillType = new Property(2, Long.class, "billType", false, "BILL_TYPE");
        public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
        public final static Property IsDele = new Property(4, int.class, "isDele", false, "IS_DELE");
        public final static Property Remarks = new Property(5, String.class, "remarks", false, "REMARKS");
    }


    public BillDao(DaoConfig config) {
        super(config);
    }
    
    public BillDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BILL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MONEY\" REAL," + // 1: money
                "\"BILL_TYPE\" INTEGER," + // 2: billType
                "\"DATE\" INTEGER," + // 3: date
                "\"IS_DELE\" INTEGER NOT NULL ," + // 4: isDele
                "\"REMARKS\" TEXT);"); // 5: remarks
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BILL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Bill entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Double money = entity.getMoney();
        if (money != null) {
            stmt.bindDouble(2, money);
        }
 
        Long billType = entity.getBillType();
        if (billType != null) {
            stmt.bindLong(3, billType);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(4, date.getTime());
        }
        stmt.bindLong(5, entity.getIsDele());
 
        String remarks = entity.getRemarks();
        if (remarks != null) {
            stmt.bindString(6, remarks);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Bill entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Double money = entity.getMoney();
        if (money != null) {
            stmt.bindDouble(2, money);
        }
 
        Long billType = entity.getBillType();
        if (billType != null) {
            stmt.bindLong(3, billType);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(4, date.getTime());
        }
        stmt.bindLong(5, entity.getIsDele());
 
        String remarks = entity.getRemarks();
        if (remarks != null) {
            stmt.bindString(6, remarks);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Bill readEntity(Cursor cursor, int offset) {
        Bill entity = new Bill( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1), // money
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // billType
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // date
            cursor.getInt(offset + 4), // isDele
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // remarks
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Bill entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMoney(cursor.isNull(offset + 1) ? null : cursor.getDouble(offset + 1));
        entity.setBillType(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setDate(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setIsDele(cursor.getInt(offset + 4));
        entity.setRemarks(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Bill entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Bill entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Bill entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
