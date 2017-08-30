package com.chinaredstar.core.cache.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by hairui.xiang on 2017/8/30.
 */

public interface IBaseService<T, ID> {
    /**
     * Retrieves an object associated with a specific ID.
     *
     * @param id Identifier that matches a specific row in the database to find and return.
     * @return The object that has the ID field which equals id or null if no matches.
     * @throws SQLException on any SQL problems or if more than 1 item with the id are found in the database.
     */
    public T queryForId(ID id);


    /**
     * Query for all of the items in the object table. For medium sized or large tables, this may load a lot of objects
     * into memory so you should consider using the {@link #iterator()} method instead.
     *
     * @return A list of all of the objects in the table.
     * @throws SQLException on any SQL problems.
     */
    public List<T> queryForAll();

    /**
     * Query for the items in the object table that match a simple where with a single field = value type of WHERE
     * clause. This is a convenience method for calling queryBuilder().where().eq(fieldName, value).query().
     *
     * @return A list of the objects in the table that match the fieldName = value;
     * @throws SQLException on any SQL problems.
     */
    public List<T> queryForEq(String fieldName, Object value);

    /**
     * Query for the rows in the database that match the object passed in as a parameter. Any fields in the matching
     * object that are not the default value (null, false, 0, 0.0, etc.) are used as the matching parameters with AND.
     * If you are worried about SQL quote escaping, you should use {@link #queryForMatchingArgs(Object)}.
     */
    public List<T> queryForMatching(T matchObj);

    /**
     * Same as {@link #queryForMatching(Object)} but this uses {@link SelectArg} and SQL ? arguments. This is slightly
     * more expensive but you don't have to worry about SQL quote escaping.
     */
    public List<T> queryForMatchingArgs(T matchObj);

    /**
     * Query for the rows in the database that matches all of the field to value entries from the map passed in. If you
     * are worried about SQL quote escaping, you should use {@link #queryForFieldValuesArgs(Map)}.
     */
    public List<T> queryForFieldValues(Map<String, Object> fieldValues);

    /**
     * Same as {@link #queryForFieldValues(Map)} but this uses {@link SelectArg} and SQL ? arguments. This is slightly
     * more expensive but you don't have to worry about SQL quote escaping.
     */
    public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues);

    /**
     * Query for a data item in the table that has the same id as the data parameter.
     */
    public T queryForSameId(T data);


    /**
     * Create a new row in the database from an object. If the object being created uses
     * {@link DatabaseField#generatedId()} then the data parameter will be modified and set with the corresponding id
     * from the database.
     *
     * @param data The data item that we are creating in the database.
     * @return The number of rows updated in the database. This should be 1.
     */
    public int create(T data);

    /**
     * Just like {@link #create(Object)} but with a collection of objects. This will wrap the creates using the same
     * mechanism as {@link #callBatchTasks(Callable)}.
     *
     * @param datas The collection of data items that we are creating in the database.
     * @return The number of rows updated in the database.
     */
    public int create(Collection<T> datas);

    /**
     * This is a convenience method to creating a data item but only if the ID does not already exist in the table. This
     * extracts the id from the data parameter, does a {@link #queryForId(Object)} on it, returning the data if it
     * exists. If it does not exist {@link #create(Object)} will be called with the parameter.
     *
     * @return Either the data parameter if it was inserted (now with the ID field set via the create method) or the
     * data element that existed already in the database.
     */
    public T createIfNotExists(T data);

    /**
     * This is a convenience method for creating an item in the database if it does not exist. The id is extracted from
     * the data parameter and a query-by-id is made on the database. If a row in the database with the same id exists
     * then all of the columns in the database will be updated from the fields in the data parameter. If the id is null
     * (or 0 or some other default value) or doesn't exist in the database then the object will be created in the
     * database. This also means that your data item <i>must</i> have an id field defined.
     *
     * @return Status object with the number of rows changed and whether an insert or update was performed.
     */
    public Dao.CreateOrUpdateStatus createOrUpdate(T data);

    /**
     * Store the fields from an object to the database row corresponding to the id from the data parameter. If you have
     * made changes to an object, this is how you persist those changes to the database. You cannot use this method to
     * update the id field -- see {@link #updateId} .
     * <p>
     * <p>
     * NOTE: This will not save changes made to foreign objects or to foreign collections.
     * </p>
     *
     * @param data The data item that we are updating in the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException             on any SQL problems.
     * @throws IllegalArgumentException If there is only an ID field in the object. See the {@link #updateId} method.
     */
    public int update(T data);

    /**
     * Update the data parameter in the database to change its id to the newId parameter. The data <i>must</i> have its
     * current (old) id set. If the id field has already changed then it cannot be updated. After the id has been
     * updated in the database, the id field of the data parameter will also be changed.
     * <p>
     * <p>
     * <b>NOTE:</b> Depending on the database type and the id type, you may be unable to change the id of the field.
     * </p>
     *
     * @param data  The data item that we are updating in the database with the current id.
     * @param newId The <i>new</i> id that you want to update the data with.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException on any SQL problems.
     */
    public int updateId(T data, ID newId);


    /**
     * Does a query for the data parameter's id and copies in each of the field values from the database to refresh the
     * data parameter. Any local object changes to persisted fields will be overwritten. If the database has been
     * updated this brings your local object up to date.
     *
     * @param data The data item that we are refreshing with fields from the database.
     * @return The number of rows found in the database that correspond to the data id. This should be 1.
     * @throws SQLException on any SQL problems or if the data item is not found in the table or if more than 1 item is found
     *                      with data's id.
     */
    public int refresh(T data);

    /**
     * Delete the database row corresponding to the id from the data parameter.
     *
     * @param data The data item that we are deleting from the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException on any SQL problems.
     */
    public int delete(T data);

    /**
     * Delete an object from the database that has an id.
     *
     * @param id The id of the item that we are deleting from the database.
     * @return The number of rows updated in the database. This should be 1.
     * @throws SQLException on any SQL problems.
     */
    public int deleteById(ID id);

    /**
     * Delete a collection of objects from the database using an IN SQL clause. The ids are extracted from the datas
     * parameter and used to remove the corresponding rows in the database with those ids.
     *
     * @param datas A collection of data items to be deleted.
     * @return The number of rows updated in the database. This should be the size() of the collection.
     * @throws SQLException on any SQL problems.
     */
    public int delete(Collection<T> datas);

    /**
     * Delete the objects that match the collection of ids from the database using an IN SQL clause.
     *
     * @param ids A collection of data ids to be deleted.
     * @return The number of rows updated in the database. This should be the size() of the collection.
     * @throws SQLException on any SQL problems.
     */
    public int deleteIds(Collection<ID> ids);


    /**
     * Returns true if an object exists that matches this ID otherwise false.
     */
    public boolean idExists(ID id);
}
