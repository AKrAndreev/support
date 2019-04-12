package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.Category;
import org.isiktir.isupport.domain.models.service.CategoryServiceModel;
import org.isiktir.isupport.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper mapper;
    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(ModelMapper mapper, CategoryRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public CategoryServiceModel createCategory(CategoryServiceModel model) {

        Category category = mapper.map(model,Category.class);

        Category savedCategory = repository.save(category);

        savedCategory.getSubcategodires().forEach(sub -> sub.setPid(savedCategory.getId()));

        return mapper.map(repository.save(savedCategory),CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> findAll() {

        List<Category> categories = repository.findAll();
        return categories.stream()
                .map(category -> mapper.map(category,CategoryServiceModel.class))
                .collect(Collectors.toList());
    }
}
