package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.MedicineService;
import com.ubb.postuniv.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {

    @Test
    void validate() throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();


        Transaction t1 = new Transaction(-2,198,365,-4,dateTimeFormat.parse("18.02.2022 12:41"));

        Assertions.assertThrows(Exception.class, () -> transactionValidator.validate(t1, medicineRepository, clientCardRepository));
    }
}