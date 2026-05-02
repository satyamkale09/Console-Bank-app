package service.impl;

import domain.Account;
import repository.AccountRepository;
import service.BankService;

import java.util.UUID;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();

        //AC%06d -> AC<06> --> AC000001, -------, AC000010
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber, accountType, (double) 0, customerId);
        //SAVE
        accountRepository.save(account);

        return accountNumber;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size();
        String accountNumber = String.format("AC%06d", size);
        return accountNumber;
    }
}
