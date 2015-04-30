package com.edu.udea.drai.bitacoras_vista_inicial;

import com.edu.udea.drai.bitacoras_vista_inicial.bd.DBAdapter;
import com.edu.udea.drai.bitacoras_vista_inicial.model.Tarea;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

/**
 * Actividad encargada de desplegar la vista del detalle de una tarea
 * @author Heinner Esteban Alvarez Rivas <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */

public class Detalle_tarea extends Activity{

	/**
	 * TextView's de la vista
	 */
	TextView tvfecha;
	TextView tvhoras;
	TextView tvtarea;
	TextView tvperfil;
	TextView tvobservacion;
	
	//EditText edCambio;
	//Button btRegistrar;
	/**
	 * Int que contendra id de la tarea a consultar
	 */
	int idTarea;
	
	/**
	 *Objeto Adaptador de la base de datos 
	 */
	DBAdapter db = new DBAdapter(this);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layaout_detalle_tarea);	
		/**
		 *capturo el id de la tarea
		 */
		idTarea = getIntent().getIntExtra("idTarea", 0);
		/**
		 * Asocio las vistas a sus variables objeto
		 */
		tvfecha=(TextView) findViewById(R.id.textViewFecha);
		tvhoras=(TextView) findViewById(R.id.textViewHoras);
		tvtarea=(TextView) findViewById(R.id.textViewTarea);
		tvperfil=(TextView) findViewById(R.id.TextViewPerfil);
		tvobservacion=(TextView) findViewById(R.id.textViewObservacion);
		/*btRegistrar = (Button) findViewById(R.id.buttonRegistrar);
		edCambio= (EditText) findViewById(R.id.editTextCambio);
		edCambio.setEnabled(false); 
		btRegistrar.setEnabled(false);		
		*/
		llenar_Campos();
		
		
	}
	

	/**
	 * Metodo encargado de consultar la tarea y rellenar los campos
	 * de la vista con la informacion recuperada
	 */
	public void llenar_Campos(){
		try{
			
		db.open();
		/**
		 * Consulto la tarea en la BD y relleno los campos 
		 */
		Tarea t1 =db.getTarea(idTarea);		
		tvfecha.setText("  "+t1.getFecha());
		tvhoras.setText("  "+t1.getHoraInicio()+" - "+t1.getHoraFin());
		tvtarea.setText("  "+t1.getTarea());
		tvperfil.setText("  "+t1.getPerfil());
		if(t1.getObservacion()==null){
			tvobservacion.setText(" No Aplica ");
			}else{
				tvobservacion.setText("  "+t1.getObservacion());
		}
		
		/*if(t1.getCedulaCambio()==null){
			edCambio.setEnabled(true);
			btRegistrar.setEnabled(true);
		}else{
			edCambio.setText(t1.getCedulaCambio());
			btRegistrar.setVisibility(0);
		}*/
		db.close();
			
		}catch(Exception e){
			Toast.makeText(
					this,
					"La tarea no se encuentra en el sistema", Toast.LENGTH_LONG)
					.show();
			db.close();
			onBackPressed();
			
			
		}
		
	}
/*
	public void registrar(View view){
		db.open();			
		Tarea t1 =db.getTarea(idTarea);		
		t1.setCedulaCambio(edCambio.getText().toString());
		edCambio.setEnabled(false);
		btRegistrar.setEnabled(false);
		//db.agregarCedCambio(t1, edCambio.getText().toString());		
		db.close();
		
		
	}*/
	
	/**
	 * Evento click del boton Regresar
	 * @param view
	 */
	public void regresar(View view){
		
		onBackPressed();
	}
}
