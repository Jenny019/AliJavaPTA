package com.example.alijavapta.mapper.alipay;

import com.example.alijavapta.domain.Transaction;

public interface AlipayMapper {
    int subtract(Transaction t);
    int increase(Transaction t);
}
