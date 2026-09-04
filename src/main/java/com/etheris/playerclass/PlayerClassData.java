package com.etheris.playerclass;

public class PlayerClassData {
    private String ownedClass = null;

    public boolean hasClass() {
        return ownedClass != null;
    }

    public String getOwnedClass() {
        return ownedClass;
    }

    public void setOwnedClass(String classId) {
        this.ownedClass = classId;
    }

    public void reset() {
        this.ownedClass = null;
    }
}