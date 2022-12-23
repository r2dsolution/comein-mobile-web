package com.aws.codestar.comein.repository.jdbc;

import org.springframework.data.repository.CrudRepository;


import com.aws.codestar.comein.entity.payment.CreditCardM;

public interface CreditCardRepository extends CrudRepository<CreditCardM, Long> {

}
