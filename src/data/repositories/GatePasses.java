package data.repositories;
import data.models.GatePass;
import data.models.Visitor;

import java.util.ArrayList;
import java.util.List;

public class GatePasses implements GatePassRepo {
    private List<GatePass> gatePasses = new ArrayList<>();
    private int gatePassId = 1;

    @Override
    public List<GatePass> findAll() {
        return new ArrayList<>(gatePasses);
    }

    @Override
    public GatePass findById(int id) {
        for (GatePass pass : gatePasses) {
            if (pass.getId() == id) {
                return pass;
            }
        }
        return null;
    }

    @Override
    public GatePass save(GatePass gatePass) {
        GatePass newPass = findById (gatePass.getId());

        if (newPass == null){
            gatePass.setId(gatePassId++);
            gatePasses.add(gatePass);
        }
        return gatePass;
    }

    @Override
    public void delete(GatePass pass) {
        gatePasses.remove(pass);
    }

    @Override
    public void deleteById(int id) {
        GatePass pass = findById(id);
        if (pass != null) {
            gatePasses.remove(pass);
        }
    }

    @Override
    public void deleteByObject(GatePass pass) {
        delete(pass);
    }

    @Override
    public void deleteAll() {
        gatePasses.clear();
    }

    public int count() {
        return gatePasses.size();
    }
}