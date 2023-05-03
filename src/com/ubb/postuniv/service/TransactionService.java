package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.*;
import com.ubb.postuniv.repository.InterfaceRepository;


import java.text.SimpleDateFormat;
import java.util.*;


public class TransactionService {
    private InterfaceRepository<Transaction> transactionRepository;
    private InterfaceRepository<Medicine> medicineRepository;
    private InterfaceRepository<ClientCard> clientCardRepository;
    private TransactionValidator transactionValidator;

    public TransactionService(InterfaceRepository<Transaction> transactionRepository, InterfaceRepository<Medicine> medicineRepository, InterfaceRepository<ClientCard> clientCardRepository, TransactionValidator transactionValidator) {
        this.transactionRepository = transactionRepository;
        this.medicineRepository = medicineRepository;
        this.clientCardRepository = clientCardRepository;
        this.transactionValidator = transactionValidator;
    }

    /**
     * Adds a Transaction object to repository.
     *
     * @param id                  of the transaction.
     * @param medicineId          of the sold medicine, must exist.
     * @param clientCardId        of the transaction, can be null '0', else, must be an existing id.
     * @param quantity            of the medicine sold.
     * @param transactionDateTime of the transaction.
     * @throws Exception if the ID already exist, if medicineId doesn't exist of if clientCardId isn't '0' or existing.
     */
    public void addTransaction(int id, int medicineId, int clientCardId, int quantity, Date transactionDateTime) throws Exception {
        Transaction transaction = new Transaction(id, medicineId, clientCardId, quantity, transactionDateTime);
        this.transactionValidator.validate(transaction, this.medicineRepository, this.clientCardRepository);
        this.transactionRepository.create(transaction);
    }

    /**
     * Gets a list with all Transaction objects.
     *
     * @return a list with all Transaction objects.
     */
    public List<Transaction> showAllTransactions() {
        return this.transactionRepository.readAll();
    }

    /**
     * Gets one Transaction object.
     *
     * @param id of the Transaction object.
     */
    public void showOneTransaction(int id) {
        this.transactionRepository.read(id);
    }

    /**
     * Updates a Transaction object.
     *
     * @param transaction object to update.
     * @throws Exception if the ID doesn't exist.
     */
    public void updateTransaction(Transaction transaction) throws Exception {
        this.transactionValidator.validate(transaction, this.medicineRepository, this.clientCardRepository);
        this.transactionRepository.update(transaction);
    }

    /**
     * Delete a Transaction object from repository.
     *
     * @param id of the Transaction object.
     * @throws Exception if the ID doesn't exist.
     */
    public void deleteTransaction(int id) throws Exception {
        this.transactionRepository.delete(id);
    }

    /**
     * Gets a list of all transaction that have transactionDateTime value between two given dates.
     *
     * @param startDate from which the search will begin.
     * @param endDate   when the search will stop.
     * @return a list of all transaction that have transactionDateTime between startDate and endDate.
     * @throws Exception there are no transactions between startDate and endDate.
     */
    public List<Transaction> getBetweenDateTransaction(Date startDate, Date endDate) throws Exception {
        List<Transaction> allTransactions = this.showAllTransactions();
        List<Transaction> betweenDatesTransaction = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            Date date = transaction.getTransactionDateTime();
            if (date.after(startDate) && date.before(endDate)) {
                betweenDatesTransaction.add(transaction);
            }
        }
        if (betweenDatesTransaction.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            throw new Exception("Sorry, there were no transactions between " + dateFormat.format(startDate) + " and " + dateFormat.format(endDate));
        }
        return betweenDatesTransaction;
    }

    /**
     * Deletes transactions that have transactionDateTime value between two given dates.
     *
     * @param startDate from which the transaction will be deleted.
     * @param endDate   untill which the transaction will be deleted.
     * @throws Exception if there is no transaction between startDate and emdDate.
     */
    public void deleteBetweenDateTransaction(Date startDate, Date endDate) throws Exception {
        List<Transaction> allTransactions = this.showAllTransactions();
        List<Transaction> cloned = new ArrayList<>(allTransactions);
        List<Transaction> betweenDatesTransaction = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            Date date = transaction.getTransactionDateTime();
            if (date.after(startDate) && date.before(endDate)) {
                betweenDatesTransaction.add(transaction);
            }
        }
        if (betweenDatesTransaction.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            throw new Exception("Sorry, there were no transactions between " + dateFormat.format(startDate) + " and " + dateFormat.format(endDate));
        }
        for (Transaction transaction : allTransactions) {
            if (betweenDatesTransaction.contains(transaction)) {
                transactionRepository.delete(transaction.getId());
            }
        }
    }

    /**
     * Gets total price of a transaction.
     *
     * @param transaction which total price is needed.
     * @return the total price on that transaction.
     */
    public float totalPrice(Transaction transaction) {
        float total = transaction.getQuantity() * medicineRepository.read(transaction.getMedicineId()).getPrice();
        return total;
    }

    /**
     * Calculates the discount that should be applied to every transaction. None if there is no clientCard id (equals '0'),
     * 10% if the client has a clientCard but the medicine doesn't need prescription,
     * 15% if the client has a clientCard and the medicine need prescription.
     *
     * @param transaction which discount value is needed.
     * @return the discount value on that transaction.
     */
    public float findDiscount(Transaction transaction) {
        float discount;
        int clientCardId = transaction.getClientCardId();
        boolean prescripted = medicineRepository.read(transaction.getMedicineId()).isPrescription();
        float price = medicineRepository.read(transaction.getMedicineId()).getPrice();
        int quantity = transaction.getQuantity();
        if (clientCardId != 0) {
            if (prescripted) {
                discount = 0.15f * (price * (float) quantity);
                return discount;
            } else {
                discount = 0.1f * (price * (float) quantity);
                return discount;
            }
        }
        return 0;
    }

    /**
     * Gets a list with the Medicine objects and the total sold quantity, ordered descending by quantity.
     *
     * @return a list with the Medicine objects and the total sold quantity, ordered descending by quantity.
     */
    public List<SoldMedicineDTO> getMedicineSoldQuantity() {
        List<Transaction> transactions = this.showAllTransactions();
        Map<Medicine, Integer> soldMedicineMap = new HashMap<>();
        List<SoldMedicineDTO> soldMedicineList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Medicine medicine = medicineRepository.read(transaction.getMedicineId());

            if (soldMedicineMap.containsKey(medicine)) {
                int quantity = soldMedicineMap.get(medicine);
                quantity += transaction.getQuantity();
                soldMedicineMap.put(medicine, quantity);
            } else {
                soldMedicineMap.put(medicine, transaction.getQuantity());
            }
        }
        for (Medicine key : soldMedicineMap.keySet()) {
            int value = soldMedicineMap.get(key);
            SoldMedicineDTO soldMedicineDTO = new SoldMedicineDTO(key, value);
            soldMedicineList.add(soldMedicineDTO);
        }
        for (int i = 0; i < soldMedicineList.size(); ++i) {
            for (int j = i + 1; j < soldMedicineList.size(); ++j) {
                if (soldMedicineList.get(i).quantity < soldMedicineList.get(j).quantity) {
                    SoldMedicineDTO t = soldMedicineList.get(i);
                    soldMedicineList.set(i, soldMedicineList.get(j));
                    soldMedicineList.set(j, t);
                }
            }
        }

        return soldMedicineList;
    }

    /**
     * Gets a list with the ClientCard object and the total discount value, ordered descending by discount value.
     *
     * @return a list with the ClientCard object and the total discount value, ordered descending by discount value.
     */
    public List<ClientCardDiscountDTO> getClientCardsDiscount() {
        List<Transaction> transactions = this.showAllTransactions();
        Map<ClientCard, Float> discountsMap = new HashMap<>();
        List<ClientCardDiscountDTO> discountsList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            ClientCard clientCard = null;
            if (transaction.getClientCardId() != 0) {
                clientCard = clientCardRepository.read(transaction.getClientCardId());
            }
            if (discountsMap.containsKey(clientCard)) {
                float discount = discountsMap.get(clientCard);
                discount += this.findDiscount(transaction);
                discountsMap.put(clientCard, discount);
            } else {
                discountsMap.put(clientCard, this.findDiscount(transaction));
            }
        }
        for (ClientCard key : discountsMap.keySet()) {
            float value = discountsMap.get(key);
            if (key != null) {
                ClientCardDiscountDTO clientCardDiscountDTO = new ClientCardDiscountDTO(key, value);
                discountsList.add(clientCardDiscountDTO);
            }
        }

        for (int i = 0; i < discountsList.size(); ++i) {
            for (int j = i + 1; j < discountsList.size(); ++j) {
                if (discountsList.get(i).discount < discountsList.get(j).discount) {
                    ClientCardDiscountDTO t = discountsList.get(i);
                    discountsList.set(i, discountsList.get(j));
                    discountsList.set(j, t);
                }
            }
        }
        return discountsList;
    }
}
