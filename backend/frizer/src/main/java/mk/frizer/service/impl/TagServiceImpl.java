package mk.frizer.service.impl;

import jakarta.transaction.Transactional;
import mk.frizer.model.Tag;
import mk.frizer.model.exceptions.TagNotFoundException;
import mk.frizer.repository.TagRepository;
import mk.frizer.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(TagNotFoundException::new);
        return Optional.of(tag);
    }

    @Override
    @Transactional
    public Optional<Tag> createTag(String name) {
        return Optional.of(tagRepository.save(new Tag(name)));
    }

    @Override
    @Transactional
    public Optional<Tag> deleteTagById(Long id) {
        Tag tag = getTagById(id).get();
        tagRepository.deleteById(id);
        return Optional.of(tag);
    }
}
