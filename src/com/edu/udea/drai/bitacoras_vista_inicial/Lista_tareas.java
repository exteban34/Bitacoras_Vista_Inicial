package com.edu.udea.drai.bitacoras_vista_inicial;

import java.util.ArrayList;
import com.edu.udea.drai.bitacoras_vista_inicial.bd.DBAdapter;
import com.edu.udea.drai.bitacoras_vista_inicial.model.Auxiliar;
import com.edu.udea.drai.bitacoras_vista_inicial.model.Tarea;
import com.edu.udea.drai.bitacoras_vista_inicial.util.Lista_Adaptador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase encargada de consultar y mostrar la lista de tareas asociadas a un auxiliar.
 * @author Heinner Esteban Alvarez <exteban34@gmail.com>
 * @version 1.0 26/03/2015
 */
public class Lista_tareas extends Activity {
	
	/**
	 *Objeto ListView para deplegar la lista de tareas
	 */
	private ListView lista;
	
	/**
	 *Objeto Adaptador de la base de datos 
	 */
	private DBAdapter db = new DBAdapter(this);

	/**
	 *Int que contendra la cedula del auxiliar cuyas tareas se visualizaran 
	 */
	private int cedAuxiliar;
	
	/**
	 * ArrayList<Tarea> que contendra el listado de tareas
	 */
	ArrayList<Tarea> tareas;
	
	/**
	 * Objeto Tarea para controlar algunas funciones
	 */
	Tarea tar;
	
	/**
	 * Objeto Auxiliar para controlar algunas funciones 
	 */
	Auxiliar aux;
	
	/**
	 * TextView que contendra la bienvenida al usuario
	 */
	TextView tvBienvenida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layaout_lista_tareas);
		/**
		 * Capturo la cedula del auxiliar
		 */
		cedAuxiliar= getIntent().getIntExtra("cedulaAux", 0);
		/**
		 * asocio la vista a su variable objeto
		 */
		tvBienvenida = (TextView) findViewById(R.id.textViewBienvenida);
		db.open();
		try {
			/**
			 * Obtengo el auxiliar y su lsitado de tareas de la BD
			 */
			tareas = db.getTareasAuxiliar(cedAuxiliar);
			aux = db.getAuxiliar(cedAuxiliar);
			/**
			 * Relleno el mensaje de bienvenida con el nombre del auxiliar
			 */
			tvBienvenida.setText(tvBienvenida.getText()+"  "+aux.getNombre());

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"No se encuentran tareas asociadas a esta cedula", Toast.LENGTH_SHORT).show();
		}

		db.close();
		lista = (ListView) findViewById(R.id.listViewTareas);
		/**
		 * Agrego un adaptador de lista a la lista de tareas
		 */
		lista.setAdapter(new Lista_Adaptador(this,
				R.layout.layaout_elemento_listado, tareas) {
			/**
			 * Sobreescribo el metodo OnEntrada(), este llena las vistas de cada elemento de
			 * la lista con los valores de cada tarea
			 */
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					tar = (Tarea) entrada;
					TextView tvfecha = (TextView) view.findViewById(R.id.textViewFechaListado);
					if (tvfecha != null){
						tvfecha.setText(tar.getFecha());						
					}
					TextView tvtitulo = (TextView) view.findViewById(R.id.textViewTituloListado);
					if (tvtitulo != null){
						tvtitulo.setText(tar.getTarea());						
					}
					TextView tvhoras = (TextView) view.findViewById(R.id.textViewHorasListado);
					if (tvhoras != null){
						tvhoras.setText(tar.getHoraInicio() + "-"
								+ tar.getHoraFin());					
					}								
				}
			}
		});

		/**
		 * Agrego un evento click en un item de la lista
		 */
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,
					int posicion, long id) {
				/**
				 * Lanzo una nueva actividad Detalle_tarea
				 * 
				 */
				tar = (Tarea) pariente
						.getItemAtPosition(posicion);
				int tareaId = tar.getId();
				Intent i = new Intent("com.edu.udea.drai.bitacoras_vista_inicial.Detalle_tarea");
				i.putExtra("idTarea", tareaId);
				startActivity(i);

			}
		});
		

	}
}
