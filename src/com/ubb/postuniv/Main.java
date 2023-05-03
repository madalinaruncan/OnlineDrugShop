package com.ubb.postuniv;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.MedicineService;
import com.ubb.postuniv.service.TransactionService;
import com.ubb.postuniv.userInterface.Console;

import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        InterfaceRepository<ClientCard> clientCardRepository = new InMemoryRepository<>();
        InterfaceRepository<Transaction> transactionInterfaceRepository = new InMemoryRepository<>();

        MedicineValidator medicineValidator = new MedicineValidator();
        ClientCardValidator clientCardValidator = new ClientCardValidator();
        TransactionValidator transactionValidator = new TransactionValidator();

        medicineRepository.create(new Medicine(1, "Paracetamol", "Biofarm", 23.70f, false));
        medicineRepository.create(new Medicine(2, "Nurofen", "Terapia", 38.80f, false));
        medicineRepository.create(new Medicine(3, "Ketonal", "Biofarm", 48.20f, true));
        medicineRepository.create(new Medicine(4, "Tador", "Sicomed", 27.70f, true));
        medicineRepository.create(new Medicine(5, "Panadol", "Eurofarm", 53.50f, false));

        clientCardRepository.create(new ClientCard(1, "Maria", "Popescu", "2680315652198", dateFormat.parse("15.03.1968"), dateFormat.parse("17.02.2005")));
        clientCardRepository.create(new ClientCard(2, "Ioan", "Savu", "1590823652198", dateFormat.parse("23.08.1959"), dateFormat.parse("19.07.2010")));
        clientCardRepository.create(new ClientCard(3, "Andreea", "Grigore", "2990919652191", dateFormat.parse("14.09.1999"), dateFormat.parse("19.12.2022")));
        clientCardRepository.create(new ClientCard(4, "Gheorghe", "Bucur", "1700613992783", dateFormat.parse("13.06.1970"), dateFormat.parse("31.01.2018")));
        clientCardRepository.create(new ClientCard(5, "Simona", "Boda", "2770715330184", dateFormat.parse("15.07.1977"), dateFormat.parse("18.03.2023")));

        transactionInterfaceRepository.create(new Transaction(1, 3,4,2,dateTimeFormat.parse("13.01.2023 18:22")));
        transactionInterfaceRepository.create(new Transaction(2, 1,1, 4,dateTimeFormat.parse("04.09.2021 09:06")));
        transactionInterfaceRepository.create(new Transaction(3, 2,3, 1,dateTimeFormat.parse("07.10.2020 10:48")));
        transactionInterfaceRepository.create(new Transaction(4, 5,1, 3,dateTimeFormat.parse("16.06.2021 17:34")));
        transactionInterfaceRepository.create(new Transaction(5, 1,0, 3,dateTimeFormat.parse("31.05.2021 19:32")));

        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        ClientCardService clientCardService = new ClientCardService(clientCardRepository, clientCardValidator);
        TransactionService transactionService = new TransactionService(transactionInterfaceRepository, medicineRepository, clientCardRepository, transactionValidator);

        Console console = new Console(medicineService, clientCardService, transactionService);
        console.runConsole();
    }
}