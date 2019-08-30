package com.lescure.toulousesacchiens.model;

import java.io.Serializable;

public class RecordBean implements Serializable {

    private static final long serialVersionUID = 1154631989972199604L;
    private DogBagPlaceBean fields;

    public DogBagPlaceBean getFields() {
        return fields;
    }

    public void setFields(DogBagPlaceBean fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "ClassPojo [fields = " + fields + "]";
    }
}
