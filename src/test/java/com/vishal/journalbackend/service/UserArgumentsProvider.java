package com.vishal.journalbackend.service;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.vishal.journalbackend.entity.User;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("newadmin1234567").password("newadmin")
                        .roles(Arrays.asList("USER", "ADMIN")).build()),
                Arguments.of(User.builder().username("frodo1234567").password("frodo").roles(Arrays.asList("USER")).build()));
    }

}
