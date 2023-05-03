package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.MedicineService;
import com.ubb.postuniv.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public void addFromService(TransactionService transactionService, MedicineService medicineService, ClientCardService clientCardService) throws Exception{

        medicineService.addMedicine(1, "medicine1", "manufacturer1", 20.00f, true);
        medicineService.addMedicine(2, "medicine2", "manufacturer2", 28.99f, false);
        medicineService.addMedicine(3, "medicine3", "manufacturer3", 12.00f, true);
        medicineService.addMedicine(4, "medicine4", "manufacturer4", 68.61f, false);
        medicineService.addMedicine(5, "medicine5", "manufacturer5", 100.00f, true);

        clientCardService.addClientCard(1, "firstname1", "lastname1",
                "2970114372193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));
        clientCardService.addClientCard(2, "firstname2", "lastname2",
                "2970114398793", dateFormat.parse("24.09.1980"), dateFormat.parse("12.10.2020"));
        clientCardService.addClientCard(3, "firstname3", "lastname3",
                "2987114330193", dateFormat.parse("16.08.2003"), dateFormat.parse("01.12.1998"));
        clientCardService.addClientCard(4, "firstname4", "lastname4",
                "2657114330193", dateFormat.parse("05.01.2000"), dateFormat.parse("31.05.2018"));
        clientCardService.addClientCard(5, "firstname1", "lastname1",
                "2970114330193", dateFormat.parse("24.01.1997"), dateFormat.parse("28.03.2023"));

        transactionService.addTransaction(1,1,1,2,dateTimeFormat.parse("20.07.2020 10:43"));
        transactionService.addTransaction(2,1,4,1,dateTimeFormat.parse("01.01.2018 19:59"));
        transactionService.addTransaction(3,2,1,3,dateTimeFormat.parse("31.05.2022 08:15"));
        transactionService.addTransaction(4,3,2,6,dateTimeFormat.parse("08.12.2020 14:04"));
        transactionService.addTransaction(5,5,1,4,dateTimeFormat.parse("29.03.2023 19:14"));
    }
    @org.junit.jupiter.api.Test
    void addValidTransaction_should_sendToRepository() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        List<Transaction> allTransactions = transactionService.showAllTransactions();
        Assertions.assertEquals(5, allTransactions.size());
    }

    @org.junit.jupiter.api.Test
    void addTransactionWithDuplicateId_should_raiseException() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        Assertions.assertThrows(Exception.class, () -> transactionService.addTransaction(1,1,1,
                2,dateTimeFormat.parse("20.07.2020 10:43")));
    }

    @org.junit.jupiter.api.Test
    void updateValidTransaction_should_updateToRepository() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        Transaction t1u = new Transaction(1,1,1,2,dateTimeFormat.parse("18.02.2021 10:43"));
        Transaction t2u = new Transaction(2,4,2,4,dateTimeFormat.parse("20.07.2020 19:13"));
        Transaction t3u = new Transaction(3,2,3,6,dateTimeFormat.parse("29.09.2022 17:06"));

        transactionService.updateTransaction(t1u);
        transactionService.updateTransaction(t2u);
        transactionService.updateTransaction(t3u);

        List<Transaction> allTransactions = transactionService.showAllTransactions();
        Assertions.assertEquals(5, allTransactions.size());
    }

    @org.junit.jupiter.api.Test
    void updateTransactionWithDuplicateId_should_raiseException() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        Assertions.assertThrows(Exception.class, () -> transactionService.addTransaction(1,1,1,
                2,dateTimeFormat.parse("20.07.2020 10:43")));
    }
    @org.junit.jupiter.api.Test
    void deleteValidTransaction_should_deleteEntry() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);

        transactionService.deleteTransaction(1);
        transactionService.deleteTransaction(2);
        transactionService.deleteTransaction(3);
        transactionService.deleteTransaction(4);
        transactionService.deleteTransaction(5);

        List<Transaction> allTransactions = transactionService.showAllTransactions();
        Assertions.assertEquals(0, allTransactions.size());
    }

    @org.junit.jupiter.api.Test
    void deleteTransactionWithNoMatchingId_should_raiseException() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);

        Assertions.assertThrows(Exception.class, () -> transactionService.deleteTransaction(88));
    }

    @org.junit.jupiter.api.Test
    void getBetweenDateTransactionWithValidDates_should_returnAListWithTheTransactionThatMatch() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);

        List <Transaction> searchedTransactions = transactionService.getBetweenDateTransaction(dateFormat.parse("01.12.2019"), dateFormat.parse("31.12.2020"));
        Assertions.assertEquals(2, searchedTransactions.size());
    }

    @org.junit.jupiter.api.Test
    void getBetweenDateTransactionWithoutValidDates_should_raiseException() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);

        Assertions.assertThrows(Exception.class, () -> transactionService.getBetweenDateTransaction(dateFormat.parse("01.12.1800"), dateFormat.parse("31.12.1801")));
    }

    @org.junit.jupiter.api.Test
    void deleteBetweenDateTransactionWithValidDates_should_removeMatchingTransaction() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);

        transactionService.deleteBetweenDateTransaction(dateFormat.parse("01.12.2019"), dateFormat.parse("31.12.2020"));

        List<Transaction> allTransactions = transactionService.showAllTransactions();
        Assertions.assertEquals(3, allTransactions.size());
    }

    @org.junit.jupiter.api.Test
    void deleteBetweenDateTransactionWithoutValidDates_should_raiseException() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);

        Assertions.assertThrows(Exception.class, () -> transactionService.deleteBetweenDateTransaction(dateFormat.parse("01.12.1800"), dateFormat.parse("31.12.1801")));
    }
    @org.junit.jupiter.api.Test
    void totalPriceOfValidTransaction_should_returnTheValueOfATransaction() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        List<Transaction> allTransaction = transactionService.showAllTransactions();

        float price = transactionService.totalPrice(allTransaction.get(0));

        Assertions.assertEquals(40f, price);
    }

    @org.junit.jupiter.api.Test
    void findDiscountForAPrescriptedMedicine_should_apply15percent() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        List<Transaction> allTransaction = transactionService.showAllTransactions();

        float discount = transactionService.findDiscount(allTransaction.get(0));

        Assertions.assertEquals(6f, discount);
    }

    @org.junit.jupiter.api.Test
    void findDiscountForANonPrescriptedMedicine_should_apply10percent() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        List<Transaction> allTransaction = transactionService.showAllTransactions();

        float discount = transactionService.findDiscount(allTransaction.get(2));

        Assertions.assertEquals(8.6970005f, discount);
    }

    @org.junit.jupiter.api.Test
    void getMedicineSoldQuantity() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        List<SoldMedicineDTO> soldMedicine = transactionService.getMedicineSoldQuantity();

        Assertions.assertEquals(4, soldMedicine.size());
    }

    @org.junit.jupiter.api.Test
    void getClientCardsDiscount() throws Exception {
        InterfaceRepository<Transaction> transactionRepository = new InMemoryRepository<>();
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        TransactionValidator transactionValidator = new TransactionValidator();
        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionService transactionService = new TransactionService(transactionRepository, medicineRepository,
                clientCardRepository,transactionValidator);
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);

        this.addFromService(transactionService, medicineService, clientCardService);
        List<ClientCardDiscountDTO> clientCardDiscount = transactionService.getClientCardsDiscount();

        Assertions.assertEquals(3, clientCardDiscount.size());
    }
}