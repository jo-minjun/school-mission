package jun.schoolmission.domain;

import java.util.Arrays;

public enum SchoolType {
    ELEMENTARY, MIDDLE, HIGH;

    public static SchoolType of(String value) {
        return Arrays.stream(SchoolType.values())
                .filter(schoolType -> schoolType.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not Found SchoolType : " + value));
    }
}
