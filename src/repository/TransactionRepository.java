package repository;

import domain.Transaction;

import java.util.*;

public class TransactionRepository {

    private final Map<String, List<Transaction>> txByAccount = new HashMap<>();

    public void add(Transaction transaction) {
        //computes if key is absent (i.e, no transaction)
        List<Transaction> list = txByAccount.computeIfAbsent(transaction.getAccountNumber(),
                k -> new ArrayList<>());
        list.add(transaction);
    }

    public List<Transaction> findByAccount(String accountNumber) {
        return new ArrayList<>(txByAccount.getOrDefault(accountNumber, Collections.emptyList()));
    }


}
