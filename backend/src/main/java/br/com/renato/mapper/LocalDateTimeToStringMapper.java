package br.com.renato.mapper;

import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public class LocalDateTimeToStringMapper {

    public String asString(LocalDateTime data) {
        return !StringUtils.isEmpty(data) ? DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( data ) : null;
    }

    public LocalDateTime asDate(String data) {
        return !StringUtils.isEmpty(data) ? LocalDateTime.parse( data, DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ) ) : null;
    }
}
