package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.Medicine;
import com.ubb.postuniv.domain.MedicineValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicineValidatorTest {

    @Test
    void validate() {
        MedicineValidator medicineValidator = new MedicineValidator();

        Medicine m1 = new Medicine(-1, "", "",-22f,true);

        Assertions.assertThrows(Exception.class, () -> medicineValidator.validate(m1));
    }
}