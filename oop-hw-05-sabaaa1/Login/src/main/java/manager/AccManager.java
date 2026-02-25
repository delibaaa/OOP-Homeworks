package manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccManager {
    private Map<String, String> accounts;

    public AccManager(){
        accounts = new HashMap<>();
        accounts.put("Patrick", "1234");
        accounts.put("Molly", "FloPup");
        accounts.put("test", "test");
    }

    public int accNum(){
        return accounts.size();
    }

    public boolean nameUsed(String username, String password){
        return accounts.containsKey(username);
    }

    public boolean accExist(String username, String password){
        if (nameUsed(username,password)){
            return Objects.equals(password, accounts.get(username));
        }
        return false;
    }

    public boolean addAcc(String username, String password){
        if(nameUsed(username, password))  return false;
        accounts.put(username,password);
        return true;
    }
}
