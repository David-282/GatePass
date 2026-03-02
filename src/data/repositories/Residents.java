package data.repositories;

import data.models.Resident;
import java.util.ArrayList;
import java.util.List;

public class Residents implements ResidentRepo {

    private final List<Resident> residents = new ArrayList<>();
    private int residentId = 1;

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(residents);
    }

    @Override
    public Resident findById(int id) {
        for (Resident resident : residents) {
            if (resident.getId() == id) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident save(Resident resident) {
        if (resident.getId() == 0) {
            resident.setId(residentId++);
            residents.add(resident);
        } else {
            Resident existing = findById(resident.getId());
            if (existing != null) {
                residents.set(residents.indexOf(existing), resident);
            } else {
                residents.add(resident);
            }
        }
        return resident;
    }

    @Override
    public void delete(Resident resident) {
        residents.remove(resident);
    }

    @Override
    public void deleteById(int id) {
        Resident toRemove = findById(id);
        boolean found = toRemove != null;

        if (found) {
            residents.remove(toRemove);
        }
    }

    @Override
    public void deleteByObject(Resident resident) {
        delete(resident);
    }

    @Override
    public void deleteAll() {
        residents.clear();
    }

    // Note: not declared in ResidentRepo interface — consider adding it
    public int count() {
        return residents.size();
    }
}