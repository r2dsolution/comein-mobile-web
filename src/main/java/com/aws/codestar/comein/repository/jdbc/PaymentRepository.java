package com.aws.codestar.comein.repository.jdbc;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aws.codestar.comein.entity.PaymentM;


public interface PaymentRepository extends CrudRepository<PaymentM, Long> {

    PaymentM findByGatewayRef(String ref) throws Exception;
}