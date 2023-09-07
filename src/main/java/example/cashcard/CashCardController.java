package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("cashcards")
public class CashCardController {
    private final CashCardRepository cashCardRepository;

    public CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<CashCard> findById(@PathVariable(name = "id") Long id) {
        final Optional<CashCard> cashCard = cashCardRepository.findById(id);
        if (!cashCard.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cashCard.get());
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder ucb) {
        final CashCard savedCashCard = cashCardRepository.save(cashCard);
        final URI locationOfNewCashCard = ucb.path("cashcards/{id}").buildAndExpand(savedCashCard.id()).toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
