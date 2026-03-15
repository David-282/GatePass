package data.repositories;
import data.models.GatePass;
import utils.RandomCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class GatePasses implements GatePassRepository {
    private static List<GatePass> gatePasses = new ArrayList<>();


    @Override
    public List<GatePass> findAll() {
        return new ArrayList<>(gatePasses);
    }

    @Override
    public GatePass findById(String id) {
        for (GatePass pass : gatePasses) {
            if (pass.getId().equals(id)) {
                return pass;
            }
        }
        return null;
    }

    @Override
    public GatePass save(GatePass gatePass) {
        GatePass newPass = findById (gatePass.getId());

        if (newPass == null){
            gatePass.setId(RandomCodeGenerator.codeGenerator());
            gatePasses.add(gatePass);
        }
        return gatePass;
    }

    @Override
    public void delete(GatePass pass) {
        gatePasses.remove(pass);
    }

    @Override
    public void deleteById(String id) {

        GatePass pass = findById(id);
        if (pass != null) {
            gatePasses.remove(pass);
        }

    }

    @Override
    public void deleteAll() {
        gatePasses.clear();
    }

    @Override
    public GatePass findByCode(String code) {
        for(GatePass gatePass : gatePasses){
            if (gatePass.getCode().equals(code)){
                return  gatePass;
            }
        }
        return null;
    }

//    @Override
//    public GatePass findByPhoneNumber(String phoneNumber) {
//
//        for (GatePass pass : gatePasses) {
//            if (pass.getId().equals(phoneNumber)) {
//                return pass;
//            }
//        }
//        return null;
//    }

    public int count() {
        return gatePasses.size();
    }
}