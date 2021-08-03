package com.ss501.myplaybff.reservation.service;

import java.util.List;

import com.ss501.myplaybff.reservation.domain.dto.Reservation;
import com.ss501.myplaybff.reservation.domain.dto.ReservationDto;

public interface ReservationService {

	/**
	 * 시설 예약 요청 
	 * @param reservationDto
	 * @return
	 */
	public ReservationDto reserve(ReservationDto reservationDto);
	
	/**
	 * 신청자 ID로 예약 정보 조회
	 * @param id
	 * @return
	 */
	public List<Reservation> getAllReservationByApplicantId(String id);
	
	/**
	 * 예약 ID로 예약 정보 조회
	 * @param id
	 * @return
	 */
	public Reservation getReservationByReservationId(String id);
	
	/**
	 * 법인 ID로 예약 정보 조회
	 * @param id
	 * @return
	 */
	public List<Reservation> getAllReservationByCorporateId(String id);
	
	/**
	 * 예약 정보 업데이트
	 * @param reservationDto
	 * @return
	 */
	public Reservation update(String id, ReservationDto reservationDto);
}
