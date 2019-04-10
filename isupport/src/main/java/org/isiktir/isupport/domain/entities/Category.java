package org.isiktir.isupport.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "categories")
public class Category extends BaseEntity {

    private String name;
    private Set<Category> subcategodires;
    private boolean isSubdirectory;
    public Category() {
    }

    @Column(name = "name",nullable = false)
    @NotNull
    @Size(min = 3,max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(targetEntity = Category.class)
    @JoinTable(name = "categories_subcategories",
            joinColumns = @JoinColumn(name = "category_id"
            ,referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "subcategory_id",referencedColumnName = "id"))
    public Set<Category> getSubcategodires() {
        return subcategodires;
    }

    public void setSubcategodires(Set<Category> subcategodires) {
        this.subcategodires = subcategodires;
    }

    public boolean isSubdirectory() {
        return isSubdirectory;
    }

    public void setSubdirectory(boolean subdirectory) {
        isSubdirectory = subdirectory;
    }
}
