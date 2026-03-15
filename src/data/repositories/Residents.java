package data.repositories;

import data.models.Resident;
import utils.RandomCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class Residents implements ResidentRepository {

    private  static List<Resident> residents = new ArrayList<>();

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(residents);
    }



    @Override
    public Resident findById(String id) {
        for (Resident resident : residents) {
            if (resident.getId().equals(id)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident save(Resident resident) {
        Resident newResident = findById(resident.getId());

        if (newResident == null){
            resident.setId(RandomCodeGenerator.codeGenerator());
            residents.add(resident);
        }
        return resident;
    }



    @Override
    public void deleteById(String id) {
        Resident toRemove = findById(id);
        boolean found = toRemove != null;

        if (found) {
            residents.remove(toRemove);
        }
    }


    @Override
    public void delete(Resident resident) {

        residents.remove(resident);
    }

    @Override
    public void deleteAll() {
        residents.clear();
    }

    @Override
    public Resident findByPhoneNumber(String phoneNumber) {
        for (Resident resident: residents){
            if (resident.getPhoneNumber().equals(phoneNumber)){
                return resident;
            }
        }
        return null;
    }

    public int count() {
        return residents.size();
    }
}