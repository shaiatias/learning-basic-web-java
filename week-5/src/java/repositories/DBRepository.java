/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import db.utils.DBConnection;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Manage Your Trip
 */
public abstract class DBRepository<T extends Entity<ID>, ID> implements CrudRepository<T, ID> {

    private String entityName;

    public DBRepository(String entityName) {
        this.entityName = entityName;
    }
    
    protected abstract T resultSetToEntity(ResultSet rs) throws SQLException;

    @Override
    public void delete(Predicate<T> filter) {
        List<T> itemsToDelete = getAll().stream().filter(filter).collect(Collectors.toList());

        if (itemsToDelete.isEmpty()) {
            return;
        }

        try {

            String idKey = itemsToDelete.get(0).getIdKey();
            String sql = "delete from " + entityName + " where " + idKey + " in (";

            for (int i = 0; i < itemsToDelete.size(); i++) {
                sql = sql + "? ,";
            }

            sql = sql.substring(0, sql.length() - 1) + ")";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            for (int i = 1; i <= itemsToDelete.size(); i++) {
                T entity = itemsToDelete.get(i - 1);

                if (entity.getIdValue().getClass().equals(String.class)) {
                    ps.setString(i, entity.getIdValue().toString());
                } else if (entity.getIdValue().getClass().equals(Long.TYPE)) {
                    ps.setLong(i, Long.parseLong(entity.getIdValue().toString()));
                } else {
                    throw new IllegalStateException("id field must be of type String or Long, found " + entity.getIdValue().getClass().getName());
                }
            }

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("failed to delete", e);
        }
    }

    @Override
    public List<T> getAll() {

        List<T> result = new ArrayList();

        try {
            
            String sql = "select * from " + entityName;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                result.add(resultSetToEntity(resultSet));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<T> getOne(Predicate<T> filter) {
        return getAll().stream().filter(filter).findFirst();
    }

    @Override
    public void save(List<T> entities) {
        entities.forEach((t) -> save(t));
    }

    @Override
    public void save(T entity) {

        try {

            if (isEntryExist(entity)) {
                update(entity);
            } else {
                create(entity);
            }

        } catch (Exception e) {
            throw new RuntimeException("failed to save entity", e);
        }
    }

    public boolean isEntryExist(T entity) {

        try {

            String sql = "select * from " + entityName + " where " + entity.getIdKey() + " = ? ";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            if (entity.getIdValue().getClass().equals(String.class)) {
                ps.setString(1, entity.getIdValue().toString());
            } else if (entity.getIdValue().getClass().equals(Long.TYPE)) {
                ps.setLong(1, Long.parseLong(entity.getIdValue().toString()));
            } else {
                throw new IllegalStateException("id field must be of type String or Long, found " + entity.getIdValue().getClass().getName());
            }

            ResultSet resultSet = ps.executeQuery();

            return resultSet.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public CrudRepository truncateData() {

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement("delete from " + entityName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("failed to truncate entity", e);
        }

        return this;
    }

    private void create(T entity) {

        Field[] declaredFields = entity.getClass().getDeclaredFields();
        String sql = "insert into " + entityName + " (";

        for (Field f : declaredFields) {
            sql = sql + f.getName() + ",";
        }

        sql = sql.substring(0, sql.length() - 1) + ") values (";

        for (Field f : declaredFields) {
            sql = sql + "? ,";
        }

        sql = sql.substring(0, sql.length() - 1) + ")";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            for (int i = 1; i <= declaredFields.length; i++) {
                Field f = declaredFields[i - 1];

                if (f.getType().equals(String.class)) {
                    String value = f.get(entity).toString();
                    ps.setString(i, value);
                } else if (f.getType().equals(Long.TYPE)) {
                    Long value = Long.parseLong(f.get(entity).toString());
                    ps.setLong(i, value);
                } else if (f.getType().equals(Integer.TYPE)) {
                    Integer value = Integer.parseInt(f.get(entity).toString());
                    ps.setInt(i, value);
                }
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void update(T entity) {

        /**
         * UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE
         * condition;
         */
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        String sql = "update " + entityName + " set ";

        for (Field f : declaredFields) {

            if (entity.getIdKey().equals(f.getName())) {
                continue;
            }

            sql = sql + f.getName() + " = ?,";
        }

        sql = sql.substring(0, sql.length() - 1) + " where " + entity.getIdKey() + " = ? ";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            for (int i = 1, j = 1; i <= declaredFields.length; i++, j++) {
                Field f = declaredFields[i - 1];

                if (entity.getIdKey().equals(f.getName())) {
                    j--;
                    continue;
                }

                if (f.getType().equals(String.class)) {
                    String value = f.get(entity).toString();
                    ps.setString(j, value);
                } else if (f.getType().equals(Long.TYPE)) {
                    Long value = Long.parseLong(f.get(entity).toString());
                    ps.setLong(j, value);
                } else if (f.getType().equals(Integer.TYPE)) {
                    Integer value = Integer.parseInt(f.get(entity).toString());
                    ps.setInt(j, value);
                }
            }

            if (entity.getIdValue().getClass().equals(String.class)) {
                ps.setString(declaredFields.length, entity.getIdValue().toString());
            } else if (entity.getIdValue().getClass().equals(Long.TYPE)) {
                ps.setLong(declaredFields.length, Long.parseLong(entity.getIdValue().toString()));
            } else {
                throw new IllegalStateException("id field must be of type String or Long, found " + entity.getIdValue().getClass().getName());
            }

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
