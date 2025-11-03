package br.com.faculdade.atv2_iec.controller;

import br.com.faculdade.atv2_iec.model.Disciplina;
import br.com.faculdade.atv2_iec.service.DisciplinaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

	private final DisciplinaService disciplinaService;

	public DisciplinaController(DisciplinaService disciplinaService) {
		this.disciplinaService = disciplinaService;
	}

	@GetMapping
	public ResponseEntity<List<Disciplina>> listarTodas() {
		return ResponseEntity.ok(disciplinaService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Disciplina> buscarPorId(@PathVariable Long id) {
		return disciplinaService.findById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping
	public ResponseEntity<Disciplina> criar(@RequestBody Disciplina disciplina) {
		Disciplina criada = disciplinaService.create(disciplina);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(criada.getId())
				.toUri();
		return ResponseEntity.created(location).body(criada);
	}
}


