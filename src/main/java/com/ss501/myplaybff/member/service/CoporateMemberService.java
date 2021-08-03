package com.ss501.myplaybff.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ss501.myplaybff.member.dto.CoporateMember;
import com.ss501.myplaybff.member.dto.CoporateMemberDto;
import com.ss501.myplaybff.member.dto.PersonalMember;
import com.ss501.myplaybff.member.dto.PersonalMemberDto;

@Service
public interface CoporateMemberService {
	
	/* 사용자 조회(ID) */
	CoporateMember findCoporateMember(Long Id);
	
	/* 사용자 조회(전체) */
	List<CoporateMember> findAllCoporateMember();
	
	/* 사용자 등록 */
	CoporateMember registerCoporateMember(CoporateMemberDto coporateMemberDto);
	
	/* 사용자 수정 */
	CoporateMember modifyCoporateMember(CoporateMemberDto coporateMemberDto);

}
