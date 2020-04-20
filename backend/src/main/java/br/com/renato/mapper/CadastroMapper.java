package br.com.renato.mapper;

import br.com.renato.domain.Cadastro;
import br.com.renato.dto.CadastroDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel="spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {LocalDateToStringMapper.class, LocalDateTimeToStringMapper.class})
public interface CadastroMapper extends BaseMapper<Cadastro, CadastroDTO> {

}
