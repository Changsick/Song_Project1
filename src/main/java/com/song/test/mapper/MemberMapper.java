package com.song.test.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.song.test.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	void joinUser(MemberDTO dto);
	MemberDTO findByID(String id);
	MemberDTO validateMember(HashMap<String, String> map);
}
