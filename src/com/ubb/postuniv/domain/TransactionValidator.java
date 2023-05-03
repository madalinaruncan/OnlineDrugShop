package com.ubb.postuniv.domain;

import com.ubb.postuniv.repository.InterfaceRepository;

public class TransactionValidator {

    public void validate(Transaction transaction, InterfaceRepository<Medicine> medicineRepository, InterfaceRepository<ClientCard> clientCardRepository) throws TransactionValidatorException {
        StringBuilder sb = new StringBuilder();
        if (medicineRepository.read(transaction.getMedicineId()) == null) {
            sb.append("The medicine with the id: " + transaction.getMedicineId() + " does not exist!");
        }

        if (clientCardRepository.read(transaction.getClientCardId()) == null) {
            sb.append("The client card with the id: " + transaction.getClientCardId() + " does not exist!");
        }

        if (transaction.getQuantity() < 0) {
            sb.append("The quantity must be greater or equal than 1!\n");
        }

        if(transaction.getTransactionDateTime().equals("")){
            sb.append("You must enter the registration date!");
        }

        if (sb.length() > 0) {
            throw new TransactionValidatorException(sb.toString());
        }
    }
}
