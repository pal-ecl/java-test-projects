package com.lescure.toulousesacchiens.model;

import java.io.Serializable;

public class DogBagPlaceBean implements Serializable {

    private static final long serialVersionUID = -6774576412482697852L;
    private String numero;

    private double[] geo_point_2d;

    private String intitule;

    public DogBagPlaceBean(String numero, double[] geo_point_2d, String intitule) {
        this.numero = numero;
        this.geo_point_2d = geo_point_2d;
        this.intitule = intitule;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double[] getGeo_point_2d() {
        return geo_point_2d;
    }

    public void setGeo_point_2d(double[] geo_point_2d) {
        this.geo_point_2d = geo_point_2d;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    @Override
    public String toString() {
        return "ClassPojo [numero = " + numero + ", latitude = " + geo_point_2d[1] +
                ", longitude = " + geo_point_2d[0] + ", intitule = " + intitule + "]";
    }
}
