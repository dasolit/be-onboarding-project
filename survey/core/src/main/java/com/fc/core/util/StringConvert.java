package com.fc.core.util;


import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class StringConvert implements AttributeConverter<List<String>, String> {

  private static final String DELIMITER = ",";
  @Override
  public String convertToDatabaseColumn(List<String> strings) {
    if(CollectionUtils.isEmpty(strings)) {
      return null;
    }
    return String.join(DELIMITER, strings);
  }

  @Override
  public List<String> convertToEntityAttribute(String s) {
    if (s.isEmpty()){
      return List.of();
    }
    return Arrays.stream(s.split(DELIMITER)).map(String::trim).collect(Collectors.toList());
  }
}
