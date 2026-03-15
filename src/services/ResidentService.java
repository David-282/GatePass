package services;

import data.models.Resident;
import data.repositories.ResidentRepository;
import data.repositories.Residents;
import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import utils.Mapper;
import utils.RandomCodeGenerator;

import java.time.LocalDateTime;
import java.util.List;

import static utils.Mapper.map;

public class ResidentService {

    private ResidentRepository residentRepository = new Residents();



    public OnboardResidentResponse onboardingResident(OnboardResidentRequest onboardResidentRequest) {
        Resident exsitingResident = residentRepository.findByPhoneNumber(onboardResidentRequest.getPhoneNumber());

        validationForDuplicate(exsitingResident);

        Resident resident = map(onboardResidentRequest);

        residentRepository.save(resident);


        return map(resident);
    }

    public String disableResident (String phoneNumber){
      Resident resident = residentRepository.findByPhoneNumber(phoneNumber);

        if (resident == null){
            throw new ResidentDoesNotExistException("Resident Does not exist.");
        }

      resident.setEnabled(false);

      return (resident.getName())+" Has Successfully been Disabled Until further Notice";
    }

    public String deleteResident (String phoneNumber){
        Resident resident = residentRepository.findByPhoneNumber(phoneNumber);

        if (resident == null){
            throw new ResidentDoesNotExistException("Resident Does not exist.");
        }

        residentRepository.delete(resident);

        return "Resident Has been Deleted.";
    }

    public List<Resident> veiwResident (){
        return residentRepository.findAll();
    }

    private void validationForDuplicate(Resident resident){
        if (resident != null)
            throw new ResidentAlreadyRegisteredException("Resident Is Already Registered");

    }


}
