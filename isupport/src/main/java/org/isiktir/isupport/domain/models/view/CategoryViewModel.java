package org.isiktir.isupport.domain.models.view;

import org.isiktir.isupport.domain.entities.Category;

import java.util.List;
import java.util.Set;

public class CategoryViewModel {

    private String id;
    private String name;
    private boolean hasChild;
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public CategoryViewModel() {
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

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }


}
