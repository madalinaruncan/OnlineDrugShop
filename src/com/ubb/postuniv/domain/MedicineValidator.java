package com.ubb.postuniv.domain;

public class MedicineValidator {
    public void validate(Medicine medicine) throws MedicineValidatorException {
        StringBuilder sb = new StringBuilder();

        if(medicine.getName().equals("")){
            sb.append("The name field cannot be empty!\n");
        }

        if(medicine.getManufacturer().equals("")){
            sb.append("The manufacturer field cannot be empty!\n");
        }

        if(medicine.getPrice() < 0) {
            sb.append("The price cannot be negative!\n");
        }

        if (sb.length() > 0) {
            throw new MedicineValidatorException(sb.toString());
        }
    }
}
