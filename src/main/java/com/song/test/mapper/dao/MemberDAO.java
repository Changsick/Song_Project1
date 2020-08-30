package com.song.test.mapper.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.song.test.dto.MemberDTO;
import com.song.test.dto.MemberDetail;
import com.song.test.mapper.MemberMapper;

import lombok.AllArgsConstructor;

@Repository
public class MemberDAO implements MemberMapper{
	
	@Autowired
	SqlSessionTemplate template;

	@Override
	public void joinUser(MemberDTO dto) {
		template.insert("com.song.test.mapper.MemberMapper.joinUser", dto);
	}

	public MemberDTO findByID(String id) {
		// TODO Auto-generated method stub
		return template.selectOne("com.song.test.mapper.MemberMapper.findByID", id);
	}

	@Override
	public MemberDTO validateMember(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return template.selectOne("com.song.test.mapper.MemberMapper.validateMember", map);
	}

	public int updateRole(MemberDetail member) {
		// TODO Auto-generated method stub
		return template.update("com.song.test.mapper.MemberMapper.updateRole", member);
	}

}
