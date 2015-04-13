package com.edu.udea.drai.bitacoras_vista_inicial;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.udea.drai.bitacoras_vista_inicial.R;
import com.edu.udea.drai.bitacoras_vista_inicial.bd.DBAdapter;
import com.edu.udea.drai.bitacoras_vista_inicial.model.Auxiliar;
import com.edu.udea.drai.bitacoras_vista_inicial.model.Tarea;
/**
 * MainActivity, provee un espacio para ingresar la cedula de la cual se quiere consultar
 * las tareas registradas en el sistema                        
 * @author Heinner Esteban Alvarez <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */
public class MainActivity extends Activity {

	/**
	 * Elementos de la vista
	 */
	Button boton_consulta;
	EditText edCedula;
	
	/**
	 *Objeto Adaptador de la base de datos 
	 */
	DBAdapter db = new DBAdapter(this);
	
	/**
	 * ProgressDialog que indica la carga de datos
	 */
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		boton_consulta = (Button) findViewById(R.id.botonConsulta);
		edCedula = (EditText) findViewById(R.id.editTextCedula);
		try {
			/**
			 * Proceso para copiar la base de datos
			 */
			String destPath = "/data/data/" + getPackageName() + "/databases";
			File f = new File(destPath);
			if (!f.exists()) {
				f.mkdirs();
				f.createNewFile();

				// ---copy the db from the assets folder into
				// the databases folder---

				CopyDB(getBaseContext().getAssets().open("bitacorasdb"),
						new FileOutputStream(destPath + "/bitacorasbd"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que se encarga de copiar la base de datos 
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public void CopyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// ---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];

		int length;

		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);

		}

		inputStream.close();
		outputStream.close();
	}
	

	/**
	 * Evento click del boton Consultar
	 * @param view
	 */
	public void onClickConsultar(View view) {
		pDialog = new ProgressDialog(MainActivity.this);
		pDialog.setMessage(getString(R.string.carga_tareas));			
		pDialog.setIndeterminate(false); 
		pDialog.setCancelable(false);
		pDialog.show();		
		db.open();		
		try {
			/**
			 * Consulto si existe el auxliar y retorno un aviso en caso 
			 * de que la consulta sea invalida
			 */
						
			Auxiliar aux = db.getAuxiliar(Integer.valueOf(edCedula.getText().toString()));
			
		} catch (Exception e) {
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), "No existe el usuario.  \n"
					+ "Por favor verifique la informacion ingresada",
					Toast.LENGTH_SHORT).show();
			edCedula.setText("");
			return;
		}

		if (edCedula.getText().toString() != "" ) {
			
			/**
			 * Lanzo una nueva actividad Lista_tareas
			 */
			Intent e = new Intent(
					"com.edu.udea.drai.bitacoras_vista_inicial.Lista_tareas");
			e.putExtra("cedulaAux",
					Integer.valueOf(edCedula.getText().toString()));
			edCedula.setText("");
			startActivity(e);
			pDialog.dismiss();
		} else {
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					"Ingrese alguna cedula para realizar la busqueda",
					Toast.LENGTH_SHORT).show();
		}

		db.close();

	}
	

}
