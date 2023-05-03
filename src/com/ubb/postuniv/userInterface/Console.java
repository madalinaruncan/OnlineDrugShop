package com.ubb.postuniv.userInterface;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.MedicineService;
import com.ubb.postuniv.service.TransactionService;

import java.text.SimpleDateFormat;
import java.util.*;

public class Console {
    private MedicineService medicineService;
    private ClientCardService clientCardService;
    private TransactionService transactionService;
    private Scanner scan;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Console(MedicineService medicineService, ClientCardService clientCardService, TransactionService transactionService) {
        this.medicineService = medicineService;
        this.clientCardService = clientCardService;
        this.transactionService = transactionService;
        this.scan = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("_____________CRUD OPERATIONS____________");
        System.out.println("1. CRUD Medicine");
        System.out.println("2. CRUD Client Card");
        System.out.println("3. CRUD Transactions");
        System.out.println("__________________SEARCH________________");
        System.out.println("4. Search medicine or client cards.");
        System.out.println("____________GENERATE REPORTS____________");
        System.out.println("5. Show sold medicine report.");
        System.out.println("6. Show discounts for every client.");
        System.out.println("__________TRANSACTION OPERATIONS________");
        System.out.println("7. Show transactions between two dates.");
        System.out.println("8. Delete transaction between two dates.");
        System.out.println("_______________MODIFY PRICE_____________");
        System.out.println("9. Increase medicine price.");
        System.out.println();
        System.out.println("_______________LEAVE PROGRAM____________");
        System.out.println("0. Exit");
    }

    private void showAll(ArrayList objects) {
        for (Object obj : objects) {
            System.out.println(obj);
        }
    }

    public void runConsole() throws Exception {
        while (true) {
            this.showMenu();
            try {
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        this.runSubmenuCRUDMedicine();
                        break;
                    case 2:
                        this.runSubmenuCRUDClientCard();
                        break;
                    case 3:
                        this.runSubmenuCRUDTransactions();
                        break;
                    case 4:
                        this.searchMedicineOrClientCards();
                        break;
                    case 5:
                        this.showSoldMedicineReportChoice();
                        break;
                    case 6:
                        this.showDiscountsReportChoice();
                        break;
                    case 7:
                        this.showTransactionsBetweenTwoDatesChoice();
                        this.showAll((ArrayList) transactionService.showAllTransactions());
                        break;
                    case 8:
                        this.deleteBetweenDatesChoice();
                        this.showAll((ArrayList) transactionService.showAllTransactions());
                        break;
                    case 9:
                        this.increasePriceChoice();
                        this.showAll((ArrayList) medicineService.showAllMedicine());
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void runSubmenuCRUDMedicine() throws Exception {
        while (true) {
            System.out.println("1. Add medicine");
            System.out.println("2. Show all medicine");
            System.out.println("3. Update medicine");
            System.out.println("4. Delete medicine");
            System.out.println("0. Back");
            try {
                int option = scan.nextInt();

                switch (option) {
                    case 1:
                        this.addMedicineChoice();
                        break;
                    case 2:
                        this.showAll((ArrayList) this.medicineService.showAllMedicine());
                        break;
                    case 3:
                        this.updateMedicineChoice();
                        break;
                    case 4:
                        this.deleteMedicineChoice();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option.");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addMedicineChoice() {
        try {
            System.out.print("Enter medicine id: ");
            int id = scan.nextInt();
            System.out.print("Enter medicine name: ");
            String name = scan.next();
            System.out.print("Enter medicine's manufacturer: ");
            String manufacturer = scan.next();
            System.out.print("Enter medicine's price: ");
            float price = scan.nextFloat();
            System.out.print("The client should have a prescription? Enter true or false! ");
            boolean prescription = scan.nextBoolean();
            this.medicineService.addMedicine(id, name, manufacturer, price, prescription);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateMedicineChoice() {
        try {
            System.out.print("Enter medicine id: ");
            int id = scan.nextInt();
            System.out.print("Enter medicine name: ");
            String name = scan.next();
            System.out.print("Enter medicine's manufacturer: ");
            String manufacturer = scan.next();
            System.out.print("Enter medicine's price: ");
            float price = scan.nextFloat();
            System.out.print("The client should have a prescription? ");
            boolean prescription = scan.nextBoolean();
            Medicine updatedMedicine = new Medicine(id, name, manufacturer, price, prescription);
            this.medicineService.updateMedicine(updatedMedicine);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteMedicineChoice() throws Exception {
        System.out.println("Enter the id of the medicine you want to delete: ");
        int id = scan.nextInt();
        this.medicineService.deleteMedicine(id);
    }

    private void runSubmenuCRUDClientCard() throws Exception {
        while (true) {
            System.out.println("1. Add client card");
            System.out.println("2. Show all client card");
            System.out.println("3. Update client card");
            System.out.println("4. Delete client card");
            System.out.println("0. Back");

            int option = scan.nextInt();
            try {
                switch (option) {
                    case 1:
                        this.addClientCardChoice();
                        break;
                    case 2:
                        this.showAll((ArrayList) this.clientCardService.showAllClientCards());
                        break;
                    case 3:
                        this.updateClientCardChoice();
                        break;
                    case 4:
                        this.deleteClientCardChoice();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option.");

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addClientCardChoice() {
        try {
            System.out.print("Enter client card id: ");
            int id = scan.nextInt();
            System.out.print("Enter client's first name: ");
            String firstName = scan.next();
            System.out.print("Enter client's last name: ");
            String lastName = scan.next();
            System.out.print("Enter client's PIN (Personal Identification Number): ");
            String pin = scan.next();
            System.out.println("Enter client's birth date: ");
            String birthDateString = scan.next();
            Date birthDate = dateFormat.parse(birthDateString);
            System.out.print("Enter card's registration date: ");
            String registrationDateString = scan.next();
            Date registrationDate = dateFormat.parse(registrationDateString);
            this.clientCardService.addClientCard(id, firstName, lastName, pin, birthDate, registrationDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateClientCardChoice() {
        try {
            System.out.print("Enter client card id: ");
            int id = scan.nextInt();
            System.out.print("Enter client's first name: ");
            String firstName = scan.next();
            System.out.print("Enter client's last name: ");
            String lastName = scan.next();
            System.out.print("Enter client's PIN (Personal Identification Number): ");
            String pin = scan.next();
            System.out.println("Enter client's birth date: ");
            String birthDateString = scan.next();
            Date birthDate = dateFormat.parse(birthDateString);
            System.out.print("Enter card's registration date: ");
            String registrationDateString = scan.next();
            Date registrationDate = dateFormat.parse(registrationDateString);
            ClientCard updatedClientCard = new ClientCard(id, firstName, lastName, pin, birthDate, registrationDate);
            this.clientCardService.updateClientCard(updatedClientCard);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteClientCardChoice() throws Exception {
        System.out.println("Enter the id of the client card you want to delete: ");
        int id = scan.nextInt();
        this.clientCardService.deleteClientCard(id);
    }

    private void runSubmenuCRUDTransactions() throws Exception {
        while (true) {
            System.out.println("1. Add transaction");
            System.out.println("2. Show all transactions");
            System.out.println("3. Update transaction");
            System.out.println("4. Delete transaction");
            System.out.println("0. Back");

            int option = scan.nextInt();

            switch (option) {
                case 1:
                    this.addTransactionChoice();
                    break;
                case 2:
                    this.showAll((ArrayList) this.transactionService.showAllTransactions());
                    break;
                case 3:
                    this.updateTransactionChoice();
                    break;
                case 4:
                    this.deleteTransactionChoice();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option.");

            }
        }
    }

    private void addTransactionChoice() {
        try {
            System.out.print("Enter transaction id: ");
            int id = scan.nextInt();
            System.out.print("Enter sold medicine's id: ");
            int medicineId = scan.nextInt();
            System.out.print("Enter client card's id (enter '0' if the client doesn't have a card): ");
            int clientCardId = scan.nextInt();
            System.out.print("Enter sold quantity: ");
            int quantity = scan.nextInt();
            System.out.println("Enter transaction's date and time (dd.mm.yyyy hh:mm): ");
            scan.nextLine();
            String transactionDateTimeString = scan.nextLine();
            Date transactionDateTime = dateTimeFormat.parse(transactionDateTimeString);
            this.transactionService.addTransaction(id, medicineId, clientCardId, quantity, transactionDateTime);
            Transaction transaction = new Transaction(id, medicineId, clientCardId, quantity, transactionDateTime);
            float transactionPrice = transactionService.totalPrice(transaction);
            float transactionDiscount = transactionService.findDiscount(transaction);
            float totalPaid = transactionPrice - transactionDiscount;
            System.out.println("Total value of reciept: " + transactionPrice + "\nTotal value of discount: " + transactionDiscount + "\nPaid at this transaction: " + totalPaid);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void updateTransactionChoice() {
        try {
            System.out.print("Enter transaction id: ");
            int id = scan.nextInt();
            System.out.print("Enter sold medicine's id: ");
            int medicineId = scan.nextInt();
            System.out.print("Enter client card's id (type '0' if the client doesn't have a card): ");
            int clientCardId = scan.nextInt();
            System.out.print("Enter sold quantity: ");
            int quantity = scan.nextInt();
            System.out.println("Enter transaction's date and time: ");
            String transactionDateTimeString = scan.next();
            Date transactionDateTime = dateTimeFormat.parse(transactionDateTimeString);

            Transaction updatedTransaction = new Transaction(id, medicineId, clientCardId, quantity, transactionDateTime);
            this.transactionService.updateTransaction(updatedTransaction);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void deleteTransactionChoice() throws Exception {
        System.out.println("Enter the id of the transaction you want to delete: ");
        int id = scan.nextInt();
        this.transactionService.deleteTransaction(id);
    }

    private void searchMedicineOrClientCards() throws Exception {
        try {
            System.out.println("Enter one or many key words:");
            scan.nextLine();
            String searchedText = scan.nextLine();
            List<Medicine> medicineResult = this.medicineService.searchMedicine(searchedText);
            List<ClientCard> clientCardresult = this.clientCardService.searchClientCard(searchedText);
            if (!medicineResult.isEmpty()) {
                for (Medicine medicine : medicineResult) {
                    System.out.println(medicine);
                }
            }
            if (!clientCardresult.isEmpty()) {
                for (ClientCard client : clientCardresult) {
                    System.out.println(client);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showSoldMedicineReportChoice() {
        List<SoldMedicineDTO> soldMedicine = transactionService.getMedicineSoldQuantity();
        System.out.println(soldMedicine);
    }

    private void showDiscountsReportChoice() {
        List<ClientCardDiscountDTO> clientCards = transactionService.getClientCardsDiscount();
        System.out.println(clientCards);
    }

    private void showTransactionsBetweenTwoDatesChoice() throws Exception {
        try {
            System.out.println("Please insert start date (dd.mm.yyyy): ");
            String startDateString = scan.next();
            Date startDate = dateFormat.parse(startDateString);
            System.out.println("Please insert end date (dd.mm.yyyy)");
            String endDateString = scan.next();
            Date endDate = dateFormat.parse(endDateString);
            List<Transaction> betweenDateTransactions = this.transactionService.getBetweenDateTransaction(startDate, endDate);
            for (Transaction transaction : betweenDateTransactions) {
                System.out.println(transaction);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteBetweenDatesChoice() {
        try {
            System.out.println("Please insert start date (dd.mm.yyyy): ");
            String startDateString = scan.next();
            Date startDate = dateFormat.parse(startDateString);
            System.out.println("Please insert end date (dd.mm.yyyy)");
            String endDateString = scan.next();
            Date endDate = dateFormat.parse(endDateString);
            this.transactionService.deleteBetweenDateTransaction(startDate, endDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void increasePriceChoice() {
        System.out.println("Enter the price above which the increase will be applied: ");
        float price = scan.nextFloat();
        System.out.println("Enter the increase percentage: ");
        int percentage = scan.nextInt();
        this.medicineService.increasePrice(price, percentage);
    }
}
