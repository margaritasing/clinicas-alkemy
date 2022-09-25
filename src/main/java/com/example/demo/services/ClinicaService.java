package com.example.demo.services;

import java.util.Date;
import java.util.List;

import com.example.demo.entities.Clinica;
import com.example.demo.entities.DiaSemanaEnum;
import com.example.demo.entities.Medico;
import com.example.demo.entities.Paciente;

public interface ClinicaService {

	Paciente guardarPaciente(Paciente paciente);

	Medico guardarMedico(Medico medico);

	Clinica guardarClinica(Clinica clinica);

	List<Paciente> getPacientesPorMedicoYFecha(long medicoId, Date fecha);

	List<Paciente> getHistorialPacientesMedico(long medicoId);

	List<Medico> getHistorialAtencionesPaciente(long pacienteId);

	List<Medico> getMedicosQueTrabajanDia(DiaSemanaEnum diaSemana);

	int getCantidadPacientesClinicaFecha(Date fecha);

	double getPromedioPacientesAtendidosPorMedico(long medicoId);

	List<Integer> getCantidadPacientesPorCategoria();

	List<Paciente> getPacientesConEmailMalFormateado();

	List<Paciente> getPacientesEntreFechas(Date fechaDesde, Date fechaHasta, long medicoId);

	List<Medico> getMedicosQueTrabajanDiasNoLaborables();

	List<Medico> getMedicosQueTrabajanEnUnaClinica(int clinicaId);

}
