package com.ss501.myplaybff.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.dto.PersonalMemberDto;

@Service
public interface PersonalMemberService {
	
	/* 사용자 조회(ID) */
	PersonalMember findPersonalMember(Long Id);
	
	/* 사용자 조회(전체) */
	List<PersonalMember> findAllPersonalMember();
	
	/* 사용자 등록 */
	PersonalMember registerPersonalMember(PersonalMemberDto personalMemberDto);
	
	/* 사용자 수정 */
	PersonalMember modifyPersonalMember(PersonalMemberDto personalMemberDto);
}