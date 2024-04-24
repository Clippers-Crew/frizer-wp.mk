package mk.frizer.web;

import mk.frizer.model.Tag;
import mk.frizer.model.Treatment;
import mk.frizer.model.dto.TreatmentAddDTO;
import mk.frizer.model.dto.TreatmentUpdateDTO;
import mk.frizer.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = {"localhost:3000","localhost:3001"})

public class TagRestController {
    private final TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }
    @GetMapping()
    public List<Tag> getTags() {
        return tagService.getTags();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id){
        return this.tagService.getTagById(id)
                .map(tag -> ResponseEntity.ok().body(tag))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Tag> save(@RequestParam String name) {
        return this.tagService.createTag(name)
                .map(tag -> ResponseEntity.ok().body(tag))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Tag> deleteById(@PathVariable Long id) {
        this.tagService.deleteTagById(id);
        if (this.tagService.getTagById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
