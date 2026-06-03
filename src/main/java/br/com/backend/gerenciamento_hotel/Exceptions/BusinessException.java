package br.com.backend.gerenciamento_hotel.Exceptions;
import java.util.UUID;
import java.time.LocalDate;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
