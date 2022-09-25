package com.example.demo.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Clinica;
import com.example.demo.entities.DiaSemanaEnum;
import com.example.demo.entities.Medico;
import com.example.demo.entities.Paciente;
import com.example.demo.repositories.ClinicaRepository;
import com.example.demo.repositories.MedicoRepository;
import com.example.demo.repositories.PacienteRepository;
import com.example.demo.utils.Utils;

@Service
public class ClinicaServiceImpl implements ClinicaService {
	
	private static final int CATEGORIA_HASTA_DOCE = 0;
	private static final int CATEGORIA_HASTA_VENCIN = 1;
	private static final int CATEGORIA_HASTA_SESCIN = 2;
	private static final int CATEGORIA_MAS_SESCIN = 3;
	private static final String ARROBA = "@";
	private static final String PUNTO_COM = ".com";
	
	@Autowired
	private ClinicaRepository clinicaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;

	/**
	 * Usado para crear un nuevo paciente en la tabla de pacientes
	 * @param nuevoPaciente
	 * @return
	 */
	public Paciente guardarPaciente(Paciente nuevoPaciente) {
		Paciente pacienteGuardado = pacienteRepository.save(nuevoPaciente);
		return pacienteGuardado;
	}

	/**
	 * Usado para crear un nuevo medico en la tabla de medicos
	 * @param nuevoPaciente
	 * @return
	 */
	public Medico guardarMedico(Medico nuevoMedico) {
		Medico medicoGuardado = medicoRepository.save(nuevoMedico);
		return medicoGuardado;
	}

	/**
	 * Usado para crear una nueva clinica en la tabla de clinicas
	 * @param nuevoPaciente
	 * @return
	 */
	public Clinica guardarClinica(Clinica nuevaClinica) {
		Clinica clinicaGuardada = clinicaRepository.save(nuevaClinica);
		return clinicaGuardada;
	}
	
	@Override
	public List<Paciente> getPacientesPorMedicoYFecha(long medicoId, Date fecha) {

		List<Paciente> pacientes = obtenerPacientes();
		List<Paciente> pacientesPorMedicoYFecha = new ArrayList<>();
		
		for (Paciente paciente : pacientes) {
			
			if(Utils.sonElMismoDia(paciente.getFechaTurnoConMedico(), fecha)) {
				
				boolean seAtendioConElMedico = pacienteSeAtendioConMedico(medicoId, paciente);
						
				if(seAtendioConElMedico) {
					pacientesPorMedicoYFecha.add(paciente);
				}
			}
		}
		
		return pacientesPorMedicoYFecha;
	}

	@Override
	public List<Paciente> getHistorialPacientesMedico(long medicoId) {
		
		List<Paciente> pacientes = obtenerPacientes();
		List<Paciente> pacientesPorMedicoYFecha = new ArrayList<>();
		
		for (Paciente paciente : pacientes) {
				
			boolean seAtendioConElMedico = pacienteSeAtendioConMedico(medicoId, paciente);
					
			if(seAtendioConElMedico) {
				pacientesPorMedicoYFecha.add(paciente);
			}
		}
		
		return pacientesPorMedicoYFecha;
	}

	private boolean pacienteSeAtendioConMedico(long medicoId, Paciente paciente) {
		boolean seAtendioConElMedico = paciente
				.getMedicos()
				.stream()
				.filter(medico -> medico.getMedicoId() == medicoId)
				.collect(Collectors.toList())
				.size() > 0;
		return seAtendioConElMedico;
	}

	@Override
	public List<Medico> getHistorialAtencionesPaciente(long pacienteId) {
		
		Paciente paciente = obtenerPacientes().stream().filter(pac -> pac.getPacienteId() == pacienteId).findFirst().orElse(null);
		List<Medico> medicosDePaciente = new ArrayList<Medico>();
		
		if(paciente != null) {
			medicosDePaciente.addAll(paciente.getMedicos());
		}
		
		return medicosDePaciente;
	}

	@Override
	public List<Medico> getMedicosQueTrabajanDia(DiaSemanaEnum diaSemana) {

		List<Medico> medicosQueTrabajanEnElDia = obtenerMedicos()
				.stream()
				.filter(medico -> diaSemana.equals(medico.getDiaSemanaDisponible()))
				.collect(Collectors.toList());
		return medicosQueTrabajanEnElDia;
	}

	@Override
	public int getCantidadPacientesClinicaFecha(Date fecha) {

		List<Medico> medicos = obtenerMedicos();
		List<Paciente> pacientes = new ArrayList<>();
		
		for (Medico medico : medicos) {
			pacientes.addAll(medico.getPacientes().stream().filter(pac -> Utils.sonElMismoDia(pac.getFechaTurnoConMedico(), fecha)).collect(Collectors.toList()));
		}
		return pacientes.size();
	}

	@Override
	public double getPromedioPacientesAtendidosPorMedico(long medicoId) {

		
		List<Medico> medicos = obtenerMedicos();
		int cantidadPacientesPorMedico = 0;
		for (Medico medico : medicos) {
			cantidadPacientesPorMedico += medico.getPacientes().size();
		}
		
		return cantidadPacientesPorMedico / medicos.size();
	}

	@Override
	public List<Integer> getCantidadPacientesPorCategoria() {
		
		// Inicializo un array para almacenar la cantidad de pacientes por categoria
		int cantPorCat [] = new int[4];
		
		// Asigno un cero a cada posicion del array
		Arrays.fill(cantPorCat, 0);
		
		List<Paciente> pacientes = obtenerPacientes();
		int numeroCategoria = 0;
		
		for (Paciente paciente : pacientes) {
			
			int edad = paciente.getEdad();
			
			if(edad <= 12) {
				numeroCategoria = CATEGORIA_HASTA_DOCE;
			} else if(edad <= 25) {
				numeroCategoria = CATEGORIA_HASTA_VENCIN;
			} else if(edad <= 65) {
				numeroCategoria = CATEGORIA_HASTA_SESCIN;
			} else {
				numeroCategoria = CATEGORIA_MAS_SESCIN;
			}
			
			cantPorCat[numeroCategoria]++;
		}
		
		// Retorno el array transformado en List
		return Arrays.stream(cantPorCat).boxed().collect(Collectors.toList());
	}

	@Override
	public List<Paciente> getPacientesConEmailMalFormateado() {

		List<Paciente> pacientes = obtenerPacientes();
		return pacientes
					.stream()
					.filter(paciente -> !paciente.getEmail().contains(ARROBA) || !paciente.getEmail().contains(PUNTO_COM))
					.collect(Collectors.toList());
	}

	@Override
	public List<Paciente> getPacientesEntreFechas(Date fechaDesde, Date fechaHasta, long medicoId) {

		List<Paciente> pacientesAtendidosEnRangoFechas = obtenerPacientes()
				.stream()
				.filter(paciente -> paciente.getFechaTurnoConMedico().compareTo(fechaDesde) >= 0 && paciente.getFechaTurnoConMedico().compareTo(fechaHasta) <= 0)
				.collect(Collectors.toList());
		
		List<Paciente> pacientesAtendidosEnRangoFechasPorMedico = new ArrayList<>();
		for (Paciente pacienteAtendidoEnRangoFechas : pacientesAtendidosEnRangoFechas) {
			
			if(pacienteSeAtendioConMedico(medicoId, pacienteAtendidoEnRangoFechas)) {
				pacientesAtendidosEnRangoFechasPorMedico.add(pacienteAtendidoEnRangoFechas);
			}
			
		}
		
		return pacientesAtendidosEnRangoFechasPorMedico;
	}

	@Override
	public List<Medico> getMedicosQueTrabajanDiasNoLaborables() {

		List<Medico> medicosQueTrabajanEnDiasNoLaborables = obtenerMedicos()
				.stream()
				.filter(medico -> medico.isTrabajaFinesSemanasYFeriados())
				.collect(Collectors.toList());
		return medicosQueTrabajanEnDiasNoLaborables;
	}

	@Override
	public List<Medico> getMedicosQueTrabajanEnUnaClinica(int clinicaId) {

		List<Medico> medicosQueTrabajanEnDiasNoLaborables = obtenerMedicos()
				.stream()
				.filter(medico -> medico.getClinicaDondeTrabaja().getClinicaId() == clinicaId)
				.collect(Collectors.toList());
		return medicosQueTrabajanEnDiasNoLaborables;
	}
	
	/**
	 * Usado para obtener una lista de pacientes de la tabla de pacientes
	 * @return Lista de pacientes
	 */
	private List<Paciente> obtenerPacientes() {
		return pacienteRepository.findAll();
	}

	/**
	 * Usado para obtener una lista de medicos de la tabla de medicos
	 * @return Lista de medicos
	 */
	private List<Medico> obtenerMedicos() {
		return medicoRepository.findAll();
	}

}
