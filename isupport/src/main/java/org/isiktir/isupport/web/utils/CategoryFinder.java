package org.isiktir.isupport.web.utils;

import org.isiktir.isupport.domain.models.view.CategoryViewModel;

import java.util.List;

public interface CategoryFinder {
    List<CategoryViewModel> findCategories(String level);
}
