package com.ubb.postuniv.domain;

public class SoldMedicineDTO {
   public Medicine medicine;
   public int quantity;

   public SoldMedicineDTO(){}

    public SoldMedicineDTO(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SoldMedicineDTO{" +
                "medicine=" + medicine +
                ", quantity=" + quantity +
                '}' + "\n";
    }

    
}
