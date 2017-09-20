package com.cclogic.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by Nishant on 9/19/2017.
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ExceptionResponse {

    private int errorCode;
    private String errorMessage;
}
