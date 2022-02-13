package com.example.alijavapta.mapper.bank;

import com.example.alijavapta.domain.Transaction;

public interface BankMapper {
    int subtract(Transaction t);
    int increase(Transaction t);
}
