package com.vishal.journalbackend.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.vishal.journalbackend.entity.User;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("45g5frgdg45tfxgfgrdght95#01762").password("professorX").build()),
                Arguments.of(User.builder().username("545gftg4gdxtfrfrggdhg95#051762").password("").build()));
    }

}
