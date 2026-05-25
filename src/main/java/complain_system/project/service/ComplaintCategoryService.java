package complain_system.project.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import complain_system.project.dto.ComplaintCategoryRequest;
import complain_system.project.dto.ComplaintCategoryResponse;
import complain_system.project.model.ComplaintCategory;
import complain_system.project.repository.ComplaintCategoryRepository;

@Service
public class ComplaintCategoryService {

    private final ComplaintCategoryRepository complaintCategoryRepository;

    public ComplaintCategoryService(ComplaintCategoryRepository complaintCategoryRepository) {
        this.complaintCategoryRepository = complaintCategoryRepository;
    }

    public ComplaintCategoryResponse createCategory(ComplaintCategoryRequest request) {
        String categoryName = ComplaintCategory.normalize(request.getName());
        if (complaintCategoryRepository.existsByName(categoryName)) {
            throw new IllegalArgumentException("Complaint category already exists");
        }

        ComplaintCategory category = new ComplaintCategory(categoryName, request.getDescription());
        ComplaintCategory savedCategory = complaintCategoryRepository.save(category);
        return toResponse(savedCategory);
    }

    public Set<ComplaintCategoryResponse> getCategories() {
        return complaintCategoryRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private ComplaintCategoryResponse toResponse(ComplaintCategory category) {
        return new ComplaintCategoryResponse(category.getCategoryId(), category.getName(), category.getDescription());
    }
}