package br.com.renato.mapper;

import br.com.renato.domain.Usuario;
import br.com.renato.dto.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {LocalDateToStringMapper.class, LocalDateTimeToStringMapper.class})
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

}
