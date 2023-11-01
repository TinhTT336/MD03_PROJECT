package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Entity <ID extends Number> implements Serializable {
    private static final long serialVersionUID = 1L;
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}
