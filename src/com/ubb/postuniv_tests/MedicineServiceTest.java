package com.ubb.postuniv_tests;

import com.ubb.postuniv.domain.ClientCard;
import com.ubb.postuniv.domain.ClientCardValidator;
import com.ubb.postuniv.domain.Medicine;
import com.ubb.postuniv.domain.MedicineValidator;
import com.ubb.postuniv.repository.InMemoryRepository;
import com.ubb.postuniv.repository.InterfaceRepository;
import com.ubb.postuniv.service.ClientCardService;
import com.ubb.postuniv.service.MedicineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicineServiceTest {
    public void addFromService(MedicineService medicineService) throws Exception {
        medicineService.addMedicine(1, "medicine1", "manufacturer1", 20.00f, true);
        medicineService.addMedicine(2, "medicine2", "manufacturer2", 28.99f, false);
        medicineService.addMedicine(3, "medicine3", "manufacturer3", 12.00f, true);
        medicineService.addMedicine(4, "medicine4", "manufacturer4", 68.61f, false);
        medicineService.addMedicine(5, "medicine5", "manufacturer5", 100.00f, true);
    }

    @org.junit.jupiter.api.Test
    void addValidMedicine_should_sendToRepository() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);

        this.addFromService(medicineService);
        List<Medicine> allMedicine = medicineService.showAllMedicine();
        Assertions.assertEquals(5, allMedicine.size());
    }

    @org.junit.jupiter.api.Test
    void addDuplicateIdMedicine_should_raiseException() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);

        this.addFromService(medicineService);
        Assertions.assertThrows(Exception.class, () -> medicineService.addMedicine(1, "medicine1",
                "manufacturer1", 20.00f, false));
    }

    @org.junit.jupiter.api.Test
    void updateMedicine_should_updateMedicineInfo() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        Medicine m1u = new Medicine(1, "drug1", "manufacturer1", 20.00f, true);
        Medicine m2u = new Medicine(2, "drug2", "manufacturer2", 28.99f, true);
        Medicine m3u = new Medicine(3, "drug3", "manufacturer3", 12.04f, true);

        medicineService.updateMedicine(m1u);
        medicineService.updateMedicine(m2u);
        medicineService.updateMedicine(m3u);

        List<Medicine> allMedicine = medicineService.showAllMedicine();
        Assertions.assertEquals(5, allMedicine.size());
    }
    @org.junit.jupiter.api.Test
    void updateMedicineWithNoMatchingId_should_raiseException() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        Medicine m1u = new Medicine(9547, "drug1", "manufacturer1", 20.00f, true);

        Assertions.assertThrows(Exception.class, () -> medicineService.updateMedicine(m1u));
    }

    @org.junit.jupiter.api.Test
    void deleteValidMedicine_should_deleteEntry() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        medicineService.deleteMedicine(1);
        medicineService.deleteMedicine(2);
        medicineService.deleteMedicine(3);
        medicineService.deleteMedicine(4);
        medicineService.deleteMedicine(5);

        List<Medicine> allMedicine = medicineService.showAllMedicine();
        Assertions.assertEquals(0, allMedicine.size());
    }

    @org.junit.jupiter.api.Test
    void deleteMedicineWithNoMatchingId_should_raiseException() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        Assertions.assertThrows(Exception.class, () -> medicineService.deleteMedicine(9547));
    }
    @org.junit.jupiter.api.Test
    void searchMedicine_should_returnAListWithMatchinfMedicine() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        List<Medicine> searched = medicineService.searchMedicine("true");

        Assertions.assertEquals(3, searched.size());
    }

    @org.junit.jupiter.api.Test
    void validIncreasePrice_should_raisePriceToMedicine() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        medicineService.increasePrice(15, 17);

        List<Medicine> allMedicine = medicineService.showAllMedicine();
        Assertions.assertEquals(14.04f, allMedicine.get(2).getPrice());
    }

    @org.junit.jupiter.api.Test
    void increasePriceWithNoPercentage_should_raiseException() throws Exception {
        InterfaceRepository<Medicine> medicineRepository = new InMemoryRepository<>();
        MedicineValidator medicineValidator = new MedicineValidator();
        MedicineService medicineService = new MedicineService(medicineRepository, medicineValidator);
        this.addFromService(medicineService);

        List<Medicine> allMedicine = medicineService.showAllMedicine();
        float initialPrice = allMedicine.get(2).getPrice();
        medicineService.increasePrice(15, 0);
        List<Medicine> updatedMedicine = medicineService.showAllMedicine();
        Assertions.assertFalse(initialPrice != updatedMedicine.get(2).getPrice());
    }
}