package com.example.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Paciente {

	@Id
	@GeneratedValue
	private long pacienteId;

	private String nombre;
	private String apellido;
	private String email;
	private int edad;
	private Date fechaNacimiento;
	private String dni;
	private String telefono;
	private Date fechaTurnoConMedico;

	@ManyToMany(mappedBy="pacientes")
	@JsonIgnore
	private List<Medico> medicos;

	public long getPacienteId() {
		return pacienteId;	
	}

	public void setPacienteId(long pacienteId) {
		this.pacienteId = pacienteId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaTurnoConMedico() {
		return fechaTurnoConMedico;
	}

	public void setFechaTurnoConMedico(Date fechaTurnoConMedico) {
		this.fechaTurnoConMedico = fechaTurnoConMedico;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}
}
