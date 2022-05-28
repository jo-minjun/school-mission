package jun.schoolmission.domain;

import java.util.Arrays;

public enum SchoolType {
    ELEMENTARY, MIDDLE, HIGH;

    public final static String exceptionMessage = "schoolType은 [ELEMENTARY, MIDDLE, HIGH] 만 가능합니다.";

    public static SchoolType of(String value) {
        return Arrays.stream(SchoolType.values())
                .filter(schoolType -> schoolType.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not Found SchoolType : " + value));
    }
}
