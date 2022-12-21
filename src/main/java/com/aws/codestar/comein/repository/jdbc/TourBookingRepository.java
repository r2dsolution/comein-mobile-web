package com.aws.codestar.comein.repository.jdbc;

import java.util.Date;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.aws.codestar.comein.entity.PaymentM;
import com.aws.codestar.comein.entity.TourBookingM;

public interface TourBookingRepository  extends Repository<TourBookingM, String> {
	
	@Modifying
	@Query("update tour_booking set gateway_ref = :p_ref, updated_date = :p_date, updated_by = :p_user where booking_code = :p_code")
	int updateGatewayRef(@Param("p_code")String bookingCode,@Param("p_ref")String gatewayRef,@Param("p_date")Date updateDate,@Param("p_user") String updateUser);

}
