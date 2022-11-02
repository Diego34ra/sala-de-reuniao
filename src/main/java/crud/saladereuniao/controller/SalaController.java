package crud.saladereuniao.controller;

import crud.saladereuniao.exception.ResourceNotFoundException;
import crud.saladereuniao.model.Sala;
import crud.saladereuniao.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/salas")
@CrossOrigin(origins = "http://localhost:4200")
public class SalaController {

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping
    public List<Sala> getAll() {
        return salaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> getById(@PathVariable Long id)
            throws ResourceNotFoundException {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Sala não encontrada:: " + id)));
        return ResponseEntity.ok().body(sala);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sala createSala (@Valid @RequestBody Sala sala) {
        return salaRepository.save(sala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> updateSala(@PathVariable Long id, @Valid @RequestBody Sala salaCreate) throws  ResourceNotFoundException{
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Sala não encontrada:: " + id)));
        sala.setNome(salaCreate.getNome());
        sala.setData(salaCreate.getData());
        sala.setHoraInicial(salaCreate.getHoraInicial());
        sala.setHoraFinal(salaCreate.getHoraFinal());
        final Sala updateSala = salaRepository.save(sala);
        return ResponseEntity.ok(updateSala);

    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteSala(@PathVariable Long id)  throws ResourceNotFoundException {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Sala não encontrada:: " + id)));

        salaRepository.delete(sala);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;

    }

}
