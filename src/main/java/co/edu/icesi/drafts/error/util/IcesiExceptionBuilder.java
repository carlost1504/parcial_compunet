package co.edu.icesi.drafts.error.util;

import co.edu.icesi.drafts.error.exception.*;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.function.Supplier;

public class IcesiExceptionBuilder {

    public static Supplier<IcesiException> createIcesiException(String message, DetailBuilder... details) {
        return () -> new IcesiException(message, createIcesiError(message, HttpStatus.BAD_REQUEST, details));
    }

    public static Supplier<IcesiException> createIcesiException(String message, HttpStatus httpStatus,DetailBuilder... details) {
        return () -> new IcesiException(message, createIcesiError(message, httpStatus, details));
    }

    public static IcesiError createIcesiError(String message, HttpStatus httpStatus,DetailBuilder... details){
        return IcesiError.builder().status(httpStatus)
                .details(
                        Arrays.stream(details)
                                .map(IcesiExceptionBuilder::mapToIcesiErrorDetail)
                                .toList()
                ).build();
    }

    public static IcesiErrorDetail mapToIcesiErrorDetail(DetailBuilder detailBuilder) {
        return IcesiErrorDetail.builder()
                .errorCode(detailBuilder.getErrorCode().getCode())
                .errorMessage(detailBuilder.getErrorCode().getMessage().formatted(detailBuilder.getFields()))
                .build();

    }

}
