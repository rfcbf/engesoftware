package br.com.renato.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public class LocalDateToStringMapper {

    public String asString(LocalDate data) {
        return !StringUtils.isEmpty(data) ? DateTimeFormatter.ofPattern( "yyyy-MM-dd" ).format( data ) : null;
    }

    public LocalDate asDate(String data) {
        return !StringUtils.isEmpty(data) ? LocalDate.parse( data, DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) ) : null;
    }
}