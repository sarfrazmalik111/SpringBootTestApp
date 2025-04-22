package com.test.service;

import com.test.modal.CompanyDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class CompanyArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(new CompanyDto(3l,3l,"Adams", "Naagal", LocalDateTime.now(), "")),
                Arguments.of(new CompanyDto(4l,4l,"Tommy", "Naagal", LocalDateTime.now(), ""))
        );
    }

    public static CompanyDto getCompanyFake() {
        return new CompanyDto(0l,0l,"ABC", "XYZ", LocalDateTime.now(), "");
    }

    public static CompanyDto getCompanyWipro() {
        return new CompanyDto(2l,2l,"WIPRO", "Delhi", LocalDateTime.now(), "");
    }
}
