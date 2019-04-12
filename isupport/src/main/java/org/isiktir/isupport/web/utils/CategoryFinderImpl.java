package org.isiktir.isupport.web.utils;

import org.isiktir.isupport.domain.entities.Category;
import org.isiktir.isupport.domain.models.view.CategoryViewModel;
import org.isiktir.isupport.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryFinderImpl implements CategoryFinder {

    private final CategoryRepository repository;
    private final ModelMapper mapper;
    private List<Category> categories;

    public CategoryFinderImpl(CategoryRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;

    }

    @Override
    public List<CategoryViewModel> findCategories(String level) {

        categories=new ArrayList<>();

        List<CategoryViewModel> models =  findMasterCategory(level)
                .stream().map(category -> mapper.map(category,CategoryViewModel.class))
                .collect(Collectors.toList());


        return  models;
    }


    private List<Category> findMasterCategory(String name){
        Category category = repository.findByName(name);
        categories.add(category);
        List<Category> subCategories = new ArrayList<>(category.getSubcategodires());


        for (Category subCategory : subCategories) {
            extractCategories(subCategory);
        }

        return categories;
    }

    private void extractCategories(Category cat) {
        categories.add(cat);
        if (cat.getSubcategodires().size()==0){
            return;
        }else {
            for (Category category : cat.getSubcategodires()) {
                extractCategories(category);
            }
        }

    }
}
