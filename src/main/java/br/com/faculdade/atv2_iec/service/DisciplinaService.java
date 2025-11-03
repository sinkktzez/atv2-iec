package br.com.faculdade.atv2_iec.service;

import br.com.faculdade.atv2_iec.model.Disciplina;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DisciplinaService {

	private final Map<Long, Disciplina> idToDisciplina = new ConcurrentHashMap<>();
	private final AtomicLong idGenerator = new AtomicLong(0L);

	public DisciplinaService() {
		// Seed with two DSM disciplines
		create(new Disciplina(null, "Integração e Entrega Contínua"));
		create(new Disciplina(null, "Programação para Dispositivos Móveis I"));
	}

	public List<Disciplina> findAll() {
		Collection<Disciplina> values = idToDisciplina.values();
		return Collections.unmodifiableList(new ArrayList<>(values));
	}

	public Optional<Disciplina> findById(Long id) {
		return Optional.ofNullable(idToDisciplina.get(id));
	}

	public Disciplina create(Disciplina disciplina) {
		long id = idGenerator.incrementAndGet();
		disciplina.setId(id);
		idToDisciplina.put(id, disciplina);
		return disciplina;
	}
}


