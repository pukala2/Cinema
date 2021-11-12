package com.cinema.reservation.utils;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Component;

@Component
public class ReservationCodeGenerator implements CodeGenerator{
    @Override
    public String generateCode() {
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        return  randomStringGenerator.generate(8);
    }
}
