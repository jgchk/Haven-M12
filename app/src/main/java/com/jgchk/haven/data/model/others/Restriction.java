package com.jgchk.haven.data.model.others;

public enum Restriction {
    MEN("men"), WOMEN("women"),
    CHILDREN("children"), YOUNG_ADULTS("young adults"),
    FAMILIES("families"), FAMILIES_WITH_CHILDREN("families with children"),
    VETERANS("veterans");

    private String mString;

    Restriction(String string) {
        mString = string;
    }

    @Override
    public String toString() {
        return mString;
    }

    public static Restriction fromString(String string) {
        for (Restriction restriction : Restriction.values()) {
            if (restriction.toString().equalsIgnoreCase(string)) {
                return restriction;
            }
        }
        throw new IllegalArgumentException("No constant with text " + string + " found");
    }
}
