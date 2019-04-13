package org.isiktir.isupport.domain.models.service;

import org.isiktir.isupport.domain.entities.Category;

import java.util.Set;

public class CategoryServiceModel {

    private String id;
    private String name;
    private Set<Category> subcategodires;
    private boolean hasChild;
    private String pid;

    public CategoryServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getSubcategodires() {
        return subcategodires;
    }

    public void setSubcategodires(Set<Category> subcategodires) {
        this.subcategodires = subcategodires;
    }


    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
