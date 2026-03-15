package data.repositories;

import data.models.Resident;

import java.util.List;

public interface ResidentRepository {
        List<Resident> findAll();
        Resident findById(String id);
        Resident save(Resident resident);
        void delete(Resident resident);
        void deleteById(String id);
        void deleteAll();


        Resident findByPhoneNumber(String phoneNumber);
}
