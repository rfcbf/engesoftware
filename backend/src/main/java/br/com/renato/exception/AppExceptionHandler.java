package br.com.renato.exception;

import br.com.renato.dto.seguranca.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ErrorDTO> gerarResposta(Exception ex, HttpStatus status){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCodigo(status.toString());
        errorDTO.setNome(ex.getMessage());
        return new ResponseEntity<>(errorDTO,status);
    }

    @ExceptionHandler(value = { RequiredFieldException.class })
    protected ResponseEntity<ErrorDTO> handleRequiredFieldException(RequiredFieldException ex, WebRequest request) {
        return gerarResposta(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<ErrorDTO> handleNotFoundExceptionException(NotFoundException ex, WebRequest request) {
        return gerarResposta(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        return gerarResposta(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
