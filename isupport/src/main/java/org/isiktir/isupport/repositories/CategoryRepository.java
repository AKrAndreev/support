package org.isiktir.isupport.repositories;

import org.isiktir.isupport.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {

    Category findByName(String name);
}
