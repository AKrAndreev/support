package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel createCategory(CategoryServiceModel model);
    List<CategoryServiceModel> findAll();

}
