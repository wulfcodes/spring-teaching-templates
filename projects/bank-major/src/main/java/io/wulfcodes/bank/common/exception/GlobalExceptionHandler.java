package io.wulfcodes.bank.common.exception;

import io.wulfcodes.bank.common.Constants;
import io.wulfcodes.bank.model.ro.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global Exception Handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex, HttpServletRequest request) {
        String correlationId = MDC.get(Constants.MDC_CORRELATION_ID);
        logger.error("Unhandled exception occurred. CorrelationId: {}", correlationId, ex);
        return new ResponseEntity<>(ApiResponse.error("An internal server error occurred.", correlationId),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String correlationId = MDC.get(Constants.MDC_CORRELATION_ID);
        logger.warn("Illegal argument exception. CorrelationId: {}", correlationId, ex);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage(), correlationId), HttpStatus.BAD_REQUEST);
    }

    // Add more exception handlers as needed
}
