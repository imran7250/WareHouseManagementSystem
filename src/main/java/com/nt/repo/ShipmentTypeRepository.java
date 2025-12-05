package com.nt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nt.model.ShipmentType;

public interface ShipmentTypeRepository extends JpaRepository<ShipmentType, Integer> {

	 @Query("SELECT COUNT(st.shipmentCode) FROM ShipmentType st WHERE st.shipmentCode = :shipmentCode")
	Integer getShipmentTypeCodeCount(String shipmentCode);
	 
	 //shipmentMode,COUNT(shipmentMode)
	 @Query("SELECT shipmentMode,COUNT(shipmentMode) FROM ShipmentType GROUP BY shipmentMode")
	 List<Object[]> getShipmentTypeModeAndCount();
	 
	 @Query("SELECT  id,shipmentCode FROM ShipmentType WHERE enableShipment=:enable")
	 List<Object[]> getShipmentIdAndCodeByEnabled(String enable);
}
