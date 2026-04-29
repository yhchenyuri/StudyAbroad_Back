package com.example.back.converter;

import com.example.back.constant.StudentsGender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<StudentsGender, String> {

    @Override
    public String convertToDatabaseColumn(StudentsGender gender) {
        return gender == null ? null : gender.getLabel();
    }

    @Override
    public StudentsGender convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        return switch (dbData) {
            case "男" -> StudentsGender.MALE;
            case "女" -> StudentsGender.FEMALE;
            case "其他" -> StudentsGender.OTHER;
            default -> throw new IllegalArgumentException("Unknown gender: " + dbData);
        };
    }

}
