package adm.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseManagement extends SQLiteOpenHelper {

	/* public attributes */
	public static final String PREFERENCES_DATABASE_INIT = "Database_Init";
	public static final String PREFERENCES_BOOL_INIT = "Database_Boolean_Init";
	public static final String DATABASE_NAME = "destinia.db";
	public static final String DATABASE_SQL_FILE = "destinia.sql";

	/* private attributes */
	private SQLiteDatabase db;
	private Context context;
	private SharedPreferences preferences;
	/* Constructors */
	public DataBaseManagement(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

		this.context = context;
		preferences = context
				.getSharedPreferences(PREFERENCES_DATABASE_INIT, 0);
	}

	public DataBaseManagement(Context context) {
		super(context, DATABASE_NAME, null, 1);

		this.context = context;
		preferences = context
				.getSharedPreferences(PREFERENCES_DATABASE_INIT, 0);
	}

	/**
	 * This Method checks if the database exists on the local device.
	 * 
	 * @return
	 */
	public boolean CheckIfDBExist() {

		boolean pref = preferences.getBoolean(PREFERENCES_BOOL_INIT, false);
		return pref;
	}

	/**
	 * This method creates the local database if not exists
	 * 
	 * @return
	 */
	public SQLiteDatabase CreateDB() {
		boolean dbExist = CheckIfDBExist();

		if (!dbExist) {
			try {

				CreateDataBase();

			} catch (IOException e) {

				Log.e("ANDROIT_ERROR",
						"Error opening database. Check if the device has free space.");
			}
		}
		return this.db;

	}

	/**
	 * This method creates the database
	 * 
	 * @throws IOException
	 */
	public void CreateDataBase() throws IOException {
		try {
			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);
			/* Start transaction */
			/* First, we load the .sql file */

			InputStream input = context.getAssets().open(DATABASE_SQL_FILE);
			BufferedReader sqlReader = new BufferedReader(
					new InputStreamReader(input));
			String line = "";

			/* While the file has lines to read... */
			while ((line = sqlReader.readLine()) != null) {
				/* ...We execute the sql orders on .sql file */
				db.execSQL(line);
			}

			/* End transaction */
		} catch (IOException e) {
			throw new IOException();
		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}

		Editor edit = preferences.edit();
		edit.putBoolean(PREFERENCES_BOOL_INIT, true);
		edit.commit();
	}

	
	/**
	 * This method deletes the database if exist
	 */
	public void borrarDB() {
		boolean dbExist = CheckIfDBExist();

		if (dbExist) {
			context.deleteDatabase(DATABASE_NAME);		
		}
	}
	
	public void createUser(String username, String password) {

		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			db.execSQL("INSERT OR REPLACE INTO User VALUES(NULL,'" + username
					+ "','"+password+"'); COMMIT;");

		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
	}

	
	public boolean checkIfUserExist(String username){
		try{

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);
			Cursor userCursor = db.rawQuery(
					"SELECT User_Id FROM User WHERE Username = ?",
					new String[]{username});
			
			if(userCursor.getCount() <= 0){
				userCursor.close();
				return false;
			}else{
				userCursor.close();
				return true;
			}
		}catch(Exception e){
			Log.e("BON_VOYAGE_DATABASE", e.toString());
		}finally{
			db.close();
		}
		return false;
	}
	public void deleteUser(String username) {
		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			SQLiteStatement statement = db
					.compileStatement("DELETE FROM User WHERE Username = ?; COMMIT;");
			statement.bindString(1, username);
			statement.execute();

		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
	}

	public void createSearch(String username, String searchType,
			String searchingWord) {

		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			Cursor userCursor = db.rawQuery(
					"SELECT User_Id, Username FROM User WHERE Username='"
							+ username + "';", null);

			userCursor.moveToFirst();
			SQLiteStatement statement = db
					.compileStatement("INSERT OR REPLACE INTO Search(User_Id, Search_Type, Searching_Word) VALUES(?,?,?)");
			statement.bindLong(1, userCursor.getLong(0));
			statement.bindString(2, searchType);
			statement.bindString(3, searchingWord);
			statement.executeInsert();
			userCursor.close();
		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
	}

	
	public String findPassword(String username){
		db = context.openOrCreateDatabase(DATABASE_NAME,
				Context.MODE_APPEND, null);

		Cursor userCursor = db.rawQuery(
				"SELECT Password FROM User WHERE Username = '"+username+"';",null);

		userCursor.moveToFirst();
		String s = userCursor.getString(0);
		userCursor.close();
		return s;
		
	}
	public List<SearchClass> getSearchBySearchType(String username,
			String searchType) {

		List<SearchClass> searchList = new ArrayList<SearchClass>();
		int userId = 0;
		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			Cursor userCursor = db.rawQuery(
					"SELECT User_Id FROM User WHERE Username = ?",
					new String[]{username});

			userCursor.moveToFirst();
			userId = userCursor.getInt(0);
			userCursor.close();
			Cursor searchCursor = db
					.rawQuery(
							"SELECT * FROM Search WHERE User_Id = ? AND Search_Type = ?",
							new String[]{String.valueOf(userId), searchType});

			/*
			 * Si hay elementos en el cursor, realizamos el bucle.
			 */
			if (searchCursor.getCount() > 0) {
				do {
					searchCursor.moveToNext();
					searchList.add(new SearchClass(searchCursor.getInt(1),
							searchCursor.getString(2), searchCursor
									.getString(3)));
				} while (!searchCursor.isLast());

			}
			searchCursor.close();

		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
		
		return searchList;
	}
	
	public void createCoordsInfo(String username, String name, String placeId, float longitude, float latitude, String description){

		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			Cursor userCursor = db.rawQuery(
					"SELECT User_Id, Username FROM User WHERE Username='"
							+ username + "';", null);

			userCursor.moveToFirst();
			SQLiteStatement statement = db
					.compileStatement("INSERT OR REPLACE INTO CoordsInfo(User_Id, Name, Id_Place, Longitude, Latitude, Description) VALUES(?,?,?,?,?,?)");
			statement.bindLong(1, userCursor.getLong(0));
			statement.bindString(2, name);	
			statement.bindString(3, placeId);
			statement.bindDouble(4, longitude);
			statement.bindDouble(5, latitude);
			statement.bindString(6, description);
			statement.executeInsert();
			userCursor.close();
		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
	}
	
	public List<CoordInfo> getCoordsInfo(String username){
		List<CoordInfo> coordsList = new ArrayList<CoordInfo>();
		int userId = 0;
		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			Cursor userCursor = db.rawQuery(
					"SELECT User_Id FROM User WHERE Username = ?",
					new String[]{username});

			userCursor.moveToFirst();
			userId = userCursor.getInt(0);
			userCursor.close();
			Cursor searchCursor = db
					.rawQuery(
							"SELECT Name, Id_Place, Longitude, Latitude, Description FROM CoordsInfo WHERE User_Id = ? ;",
							new String[]{String.valueOf(userId)});

			/*
			 * Si hay elementos en el cursor, realizamos el bucle.
			 */
			if (searchCursor.getCount() > 0) {
				do {
					searchCursor.moveToNext();
					coordsList.add(new CoordInfo(username, searchCursor.getString(0), searchCursor.getString(1),searchCursor.getFloat(2), searchCursor.getFloat(3), searchCursor.getString(4)));
				} while (!searchCursor.isLast());

			}
			searchCursor.close();

		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
		return coordsList;
	}
	public boolean ExistsCoordsInfo(String username, String idplace){
		
		Cursor userCursor = null;
		boolean ret = true;
		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);

			Cursor userIdCursor = db.rawQuery(
					"SELECT User_Id FROM User WHERE Username = ?",
					new String[]{username});

			userIdCursor.moveToFirst();
			int userId = userIdCursor.getInt(0);
			userIdCursor.close();
			
			
			userCursor = db.rawQuery(
					"SELECT Name FROM CoordsInfo WHERE User_Id = ? AND Id_Place = ?;",
					new String[]{String.valueOf(userId), idplace});
			/*
			 * Si hay elementos en el cursor, realizamos el bucle.
			 */
			
			if (userCursor.getCount() > 0) {
					userCursor.close();
					db.close();

					ret= true;
			}else{
					userCursor.close();
					db.close();

					ret= false;
			}

		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} 
		
		return ret;
	}
	public void deleteUser(String username, float longitude, float latitude) {
		try {

			db = context.openOrCreateDatabase(DATABASE_NAME,
					Context.MODE_APPEND, null);


			Cursor userCursor = db.rawQuery(
					"SELECT User_Id, Username FROM User WHERE Username='"
							+ username + "';", null);

			userCursor.moveToFirst();
			
			
			SQLiteStatement statement = db
					.compileStatement("DELETE FROM CoordsInfo WHERE User_Id = ? AND Longitude = ? AND Latitude = ?; COMMIT;");
			statement.bindLong(1, userCursor.getLong(0));
			statement.bindDouble(2, longitude);
			statement.bindDouble(3, latitude);
			statement.execute();
			userCursor.close();
		} catch (Exception e) {
			Log.e("ANDROIT_ERROR", e.toString());
		} finally {
			db.close();
		}
	}
	public void closeDatabase() {
		db.close();
	}

	public void beginTransaction() {
		db.beginTransaction();
	}
	public void endTransaction() {
		db.endTransaction();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
