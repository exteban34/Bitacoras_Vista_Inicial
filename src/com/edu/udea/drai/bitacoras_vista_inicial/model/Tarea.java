package com.edu.udea.drai.bitacoras_vista_inicial.model;

/**
 * Clase para el tranporte de datos de Tareas en el sistema
 * @author Heinner Esteban Alvarez Rivas <exteban34@gmail.com>
 * @version 1.0 25/02/2015
 *
 */
public class Tarea {
	
	/**
	 * Id de identificacion de la tarea
	 */
	private int id;
	/**
	 * Auxiliar uqe realiza la tarea
	 */
	private Auxiliar auxiliar;
	
	/**
	 * Fecha de realizacion de la tarea
	 */
	private String fecha;
	
	/**
	 * Hora de inicio de la tarea
	 */
	private String horaInicio;
	
	/**
	 * Hora de fin de la tarea
	 */
	private String horaFin;
	
	/**
	 * Perfil que ejerce el auxiliar en la tarea
	 */
	private String perfil;
	
	/**
	 * Tarea que se realizara
	 */
	private String tarea;
	
	/**
	 * Observacion que lleva la tarea
	 */
	private String observacion;
	
	/**
	 * Cedula del auxiliar con quien se realizo un cambio
	 */
	private String CedulaCambio;
	
	
	public Tarea(Auxiliar auxiliar, String fecha,String horaInicio, String horaFin, String tarea,
			String perfil, String observacion) {
		super();
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.perfil = perfil;
		this.tarea = tarea;
		this.observacion = observacion;
		this.fecha = fecha;
		this.auxiliar = auxiliar;
		auxiliar.getCedula();
		auxiliar.getNombre();
		
	}
	
	public Auxiliar getAuxiliar() {
		return auxiliar;
	}
	public void setAuxiliar(Auxiliar auxiliar) {
		this.auxiliar = auxiliar;		
	}
	public String getCedulaCambio() {
		return CedulaCambio;
	}
	public void setCedulaCambio(String cedulaCambio) {
		CedulaCambio = cedulaCambio;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getTarea() {
		return tarea;
	}
	public void setTarea(String tarea) {
		this.tarea = tarea;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	
	

	
	
	
}
