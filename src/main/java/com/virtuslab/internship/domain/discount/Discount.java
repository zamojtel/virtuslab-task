package com.virtuslab.internship.domain.discount;

import com.virtuslab.internship.domain.receipt.Receipt;


public interface Discount {

    Receipt apply(Receipt receipt);
}
