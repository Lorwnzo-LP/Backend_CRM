package com.VastaImoveis.CRM.Exception;

import com.VastaImoveis.CRM.shared.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);

    }
}
