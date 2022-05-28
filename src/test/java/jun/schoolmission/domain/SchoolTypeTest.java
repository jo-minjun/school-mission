package jun.schoolmission.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName(value = "ShcoolType 테스트")
class SchoolTypeTest {

    @ParameterizedTest(name = "string: {0}")
    @ValueSource(strings = {"ELEMENTARY", "MIDDLE", "HIGH", "elementary", "middle", "high"})
    @DisplayName(value = "String을 SchoolType으로 변환 - 성공")
    void string_to_school_type_success(String stringSchoolType) {
        // when
        SchoolType type = SchoolType.of(stringSchoolType);

        // then
        assertThat(type).isInstanceOf(SchoolType.class);
    }

    @ParameterizedTest(name = "string: {0}")
    @ValueSource(strings = {"ELEMENTARY1", "미들", "고등"})
    @DisplayName(value = "String을 SchoolType으로 변환 - 실패")
    void string_to_school_type_fail(String stringNoneSchoolType) {
        // when
        // then
        assertThatThrownBy(() -> SchoolType.of(stringNoneSchoolType))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not Found SchoolType :");

    }
}