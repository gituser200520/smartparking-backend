package com.parking.smartparking.exception;

import com.parking.smartparking.dto.ParkingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<ParkingResponse> handleParkingException(ParkingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ParkingResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ParkingResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ParkingResponse.error("An unexpected error occurred: " + ex.getMessage()));
    }
}
