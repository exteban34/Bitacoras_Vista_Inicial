package com.edu.udea.drai.bitacoras_vista_inicial.bd;


import java.util.ArrayList;

import com.edu.udea.drai.bitacoras_vista_inicial.model.Auxiliar;
import com.edu.udea.drai.bitacoras_vista_inicial.model.Tarea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	static final String DATABASE_NOMBRE = "bitacorasbd";	
	static final int DATABASE_VERSION = 1;
	static final String TAG = "DBAdapter";
	
	static final String TABLA_AUXILIARES = "Auxiliares";	
	static final String KEY_CEDULA_AUX = "cedula";
	static final String KEY_NOMBRE_AUX = "nombre";	
	
	static final String TABLA_TAREAS = "Tareas";
	static final String KEY_ID_TAREA = "id";
	static final String KEY_AUXILIAR_TAREA = "auxiliar";	
	static final String KEY_FECHA_TAREA = "fecha";	
	static final String KEY_HORAINICIO_TAREA = "hora_inicio";	
	static final String KEY_HORAFIN_TAREA = "hora_fin";	
	static final String KEY_PERFIL_TAREA = "perfil";	
	static final String KEY_TAREA_TAREA = "tarea";
	static final String KEY_OBSERVACION_TAREA = "observacion";	
	static final String KEY_CAMBIO_TAREA = "camcio";	
		

	static final String DATABASE_CREATE = "create table Auxiliares (cedula text primary key, "
			+ "nombre text not null);"
			
			+ "create table Tareas (id integer primary key autoincrement, "
			+ "auxiliar text not null,"
			+ "fecha text not null,"
			+ "hora_inicio text not null,"
			+ "hora_fin text not null,"
			+ "perfil text not null,"
			+ "tarea text not null,"
			+ "observacion text,"
			+ "cambio text)";
	

	final Context context;

	DatabaseHelper DBHelper;
	SQLiteDatabase db;
	private Auxiliar aux=null;
	private Tarea tarea = null;
	

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
			//	db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS contacts");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---retornar todos los Auxiliares---
	public ArrayList<Auxiliar> getTodosAuxiliares() {
		Cursor c =db.query(TABLA_AUXILIARES, new String[] { KEY_CEDULA_AUX, KEY_NOMBRE_AUX,
			 }, null, null, null, null, null);
		ArrayList<Auxiliar> auxiliares = new ArrayList<Auxiliar>();
		
		if (c.moveToFirst()) {
			do {
				aux = new Auxiliar(c.getString(0),c.getString(1));
				auxiliares.add(aux);
				
			} while (c.moveToNext());
		}
		return auxiliares;
		
	}
	//retornar todas las tareas
	public ArrayList<Tarea> getTodasTareas() {
		
		Cursor c =db.query(TABLA_TAREAS, new String[] { KEY_AUXILIAR_TAREA,
				KEY_FECHA_TAREA,KEY_HORAINICIO_TAREA,KEY_HORAFIN_TAREA,
				KEY_TAREA_TAREA,KEY_PERFIL_TAREA,KEY_OBSERVACION_TAREA,KEY_ID_TAREA
		 }, null, null, null, null, null);
	ArrayList<Tarea> tareas = new ArrayList<Tarea>();
	
	if (c.moveToFirst()) {
		do {
			Auxiliar aux= getAuxiliar(Integer.valueOf(c.getString(0)));	
			tarea = new Tarea(aux,c.getString(1),c.getString(2),c.getString(3),
					c.getString(4),c.getString(5),c.getString(6));	
			tarea.setId(Integer.valueOf(c.getString(7)));
			tareas.add(tarea);
			
		} while (c.moveToNext());
	}
	return tareas;
	}

	//retorna tareas de un auxiliar
	public ArrayList<Tarea> getTareasAuxiliar(int ced) {

		Cursor c = db.query(TABLA_TAREAS, new String[] { KEY_AUXILIAR_TAREA,
				KEY_FECHA_TAREA, KEY_HORAINICIO_TAREA, KEY_HORAFIN_TAREA,
				KEY_TAREA_TAREA,KEY_PERFIL_TAREA, KEY_OBSERVACION_TAREA, KEY_ID_TAREA },
				KEY_AUXILIAR_TAREA +"="+ced, null, null, null, null);
		ArrayList<Tarea> tareas = new ArrayList<Tarea>();

		if (c.moveToFirst()) {
			do {
				Auxiliar aux = getAuxiliar(Integer.valueOf(c.getString(0)));
				tarea = new Tarea(aux, c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getString(5),
						c.getString(6));
				tarea.setId(Integer.valueOf(c.getString(7)));
				tareas.add(tarea);

			} while (c.moveToNext());
		}
		return tareas;
	}
	// ---retrieves a particular auxiliar---
	public Auxiliar getAuxiliar(int ced) throws SQLException {
		
		Cursor mCursor = db.query(true, TABLA_AUXILIARES, new String[] {
				KEY_CEDULA_AUX, KEY_NOMBRE_AUX}, KEY_CEDULA_AUX + "=" + ced,
				null, null, null, null, null);
		Auxiliar aux= null;
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		
		aux=new Auxiliar(mCursor.getString(0),mCursor.getString(1));
		return aux;
	}
	
	/*
	 * Retornar una tarea particular
	 */
	public Tarea getTarea(int id) throws SQLException {
		
		Cursor mCursor = db.query(true, TABLA_TAREAS, new String[] {
				KEY_AUXILIAR_TAREA,	KEY_FECHA_TAREA,KEY_HORAINICIO_TAREA,KEY_HORAFIN_TAREA,
				KEY_TAREA_TAREA,KEY_PERFIL_TAREA,KEY_OBSERVACION_TAREA, KEY_ID_TAREA}, KEY_ID_TAREA + "=" + id,
				null, null, null, null, null);
		Tarea tar= null;
		Auxiliar auxi;
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		auxi=getAuxiliar(Integer.valueOf(mCursor.getString(0)));		
		tar=new Tarea(auxi,mCursor.getString(1),mCursor.getString(2),mCursor.getString(3),
				mCursor.getString(4),mCursor.getString(5),mCursor.getString(6));
		tar.setId(Integer.valueOf(mCursor.getString(7)));
		return tar;
	}


	
}
