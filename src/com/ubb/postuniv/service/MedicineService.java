package com.ubb.postuniv.service;

import com.ubb.postuniv.domain.Medicine;
import com.ubb.postuniv.domain.MedicineValidator;
import com.ubb.postuniv.repository.InterfaceRepository;

import java.util.ArrayList;
import java.util.List;

public class MedicineService {
    private InterfaceRepository<Medicine> medicineRepository;
    private MedicineValidator medicineValidator;

    public MedicineService(InterfaceRepository<Medicine> medicineRepository, MedicineValidator medicineValidator) {
        this.medicineRepository = medicineRepository;
        this.medicineValidator = medicineValidator;
    }

    /**
     * Adds a Medicine object to repository.
     *
     * @param id           of the medicine.
     * @param name         of the medicine.
     * @param manufacturer of the medicine.
     * @param price        of the medicine.
     * @param prescription of the medicine.
     * @throws Exception if the ID already exist.
     */
    public void addMedicine(int id, String name, String manufacturer, float price, boolean prescription) throws Exception {
        Medicine medicine = new Medicine(id, name, manufacturer, price, prescription);
        this.medicineValidator.validate(medicine);
        this.medicineRepository.create(medicine);
    }

    /**
     * Gets a list of all Medicine objects.
     *
     * @return a list of all Medicine objects.
     */
    public List<Medicine> showAllMedicine() {
        return this.medicineRepository.readAll();
    }

    /**
     * Gets one Medicine object.
     *
     * @param id of the Medicine object.
     */
    public void showOneMedicine(int id) {
        this.medicineRepository.read(id);
    }

    /**
     * Updates a Medicine object's information.
     *
     * @param medicine to update.
     * @throws Exception if the ID doesn't exist.
     */
    public void updateMedicine(Medicine medicine) throws Exception {
        this.medicineValidator.validate(medicine);
        this.medicineRepository.update(medicine);
    }

    /**
     * Delete a Medicine object from repository.
     *
     * @param id of the Medicine to delete.
     * @throws Exception if the ID doesn't exist.
     */
    public void deleteMedicine(int id) throws Exception {
        this.medicineRepository.delete(id);
    }

    /**
     * Search Medicine object that match with user's input.
     *
     * @param text to match with Medicine object's data.
     * @return a list with the objects that have matching content.
     */
    public List<Medicine> searchMedicine(String text) {
        text = text.toLowerCase();
        List<Medicine> stock = this.showAllMedicine();
        List<Medicine> searched = new ArrayList<>();
        for (Medicine medicine : stock) {
            String id = String.valueOf(medicine.getId());
            String name = medicine.getName().toLowerCase();
            String manufacturer = medicine.getManufacturer().toLowerCase();
            String price = String.valueOf(medicine.getPrice());
            String prescription = String.valueOf(medicine.isPrescription());
            if (id.contains(text) || name.contains(text) || manufacturer.contains(text) || price.contains(text) || prescription.contains(text)) {
                searched.add(medicine);
            }
        }
        return searched;
    }

    /**
     * Increases the price value with a given percent for all Medicine objects that have price value under a given value.
     *
     * @param price   under witch the price increase will be applied.
     * @param percent the percentage of price increase.
     */
    public void increasePrice(float price, int percent) {
        List<Medicine> medicines = this.medicineRepository.readAll();
        try {
            for (int i = 0; i <= medicines.size() - 1; i++) {
                if (medicines.get(i).getPrice() < price) {
                    float percentage = percent / 100f * medicines.get(i).getPrice();
                    float medicinePrice = medicines.get(i).getPrice() + percentage;
                    int id = medicines.get(i).getId();
                    String name = medicines.get(i).getName();
                    String manufacturer = medicines.get(i).getManufacturer();
                    boolean prescription = medicines.get(i).isPrescription();
                    Medicine med = new Medicine(id, name, manufacturer, medicinePrice, prescription);
                    this.medicineRepository.update(med);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
