package com.lescure.testAndroid;

class EleveBean {

    private String name;
    private String firstName;

    public EleveBean() {

    }

    public EleveBean(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
