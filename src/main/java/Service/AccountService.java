package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        if(!account.getUsername().isBlank()){ // not blank
            Account test_account = accountDAO.getAccountByUsername(account.getUsername());
            if(test_account == null){ // username is not already used -> it can be used
                if(account.getPassword().length() > 3){
                    return accountDAO.insertAccount(account);
                }                
            }
        }
        return null;
    }

    public Account loginAccount(Account account) {
        if(!account.getUsername().isBlank()){ // not blank
            Account test_account = accountDAO.getAccountByUsername(account.getUsername());
            if(test_account != null){ // username exists
                if(account.getPassword().equals(test_account.getPassword())){
                    return accountDAO.getAccountByUsername(account.getUsername());
                }                
            }
        }
        return null;
    }

    public boolean accountExist(int account_id) {
        if(account_id >= 0){ // valid id
            Account test_account = accountDAO.getAccountById(account_id);
            return test_account != null;
        }
        return false;
    }
}
