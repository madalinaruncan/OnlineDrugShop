package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.domain.ClientCardValidator;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import com.ubb.postuniv.service.ClientCardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class ClientCardValidatorTest {

    @org.junit.jupiter.api.Test
    void validate() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        ClientCardValidator clientCardValidator = new ClientCardValidator();

        ClientCard c1 = new ClientCard(-1,"","",
                "",dateFormat.parse("04.01.2024"),dateFormat.parse("01.02.1900"));

        ClientCard c2 = new ClientCard(-3,"","",
                "",dateFormat.parse("04.01.1900"),dateFormat.parse("01.02.2354"));

        Assertions.assertThrows(Exception.class, () -> clientCardValidator.validate(c1));
        Assertions.assertThrows(Exception.class, () -> clientCardValidator.validate(c2));
    }
}