package ru.btule.shorturl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.btule.shorturl.dto.ExceptionResponseDTO;

@ControllerAdvice(annotations = CustomExceptionHandler.class)
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadUrlException.class)
    public ResponseEntity<ExceptionResponseDTO> handleBadUrlException(BadUrlException e) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(e.getMessage());
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNotFoundException(NotFoundException e) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(e.getMessage());
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> exceptionMethod(Exception e) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO("Ой, что-то пошло не так");
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
