/*
package com.dev.notice_board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Dao.Notice_board_interface;
import Dto.Notice_board_dto;

@Controller
public class notice_board_controller {

	@Autowired
	public SqlSession sqlSession;
	
	@RequestMapping("/notice_board")
	public String notice_board(HttpServletRequest request) {
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		
		ArrayList<Notice_board_dto> dtos = dao.getPostList();
	
		request.setAttribute("dtos", dtos);
		
		return "notice/notice_board";
	}

	@RequestMapping("/notice_board_write")
	public String notice_board_write() {
		
		
		return "notice/notice_board_write";
	}
	
	@RequestMapping("/notice_board_save")
	public String notice_board_save(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		return "notice/notice_board";
	}
	@RequestMapping("/notice_board_insert")
	public String notice_board_insert(HttpServletRequest request, HttpServletResponse response) {
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		
		String id = request.getParameter("id");
		String pw = request.getParameter("password");

		Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("pw", pw);
			
			int notice_insert = dao.notice_insert(map);
			
		
		return "notice/notice_board_insert";
	}
	@RequestMapping("/notice_board_login")
	public String notice_board_login(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("password");
		
		session.setAttribute("sessionId", id);
		session.setAttribute("sessionpw", pw);
		session.setMaxInactiveInterval(60*60);
		
		return "notice/notice_board";
	}
	@RequestMapping("/notice_board_loginForm")
	public String notice_board_loginForm(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		return "notice/notice_board_loginForm";
	}
	@RequestMapping("/notice_board_delete")
	public String notice_board_delete(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		
		session.invalidate();
		
		return "notice/notice_board";
	}
	@RequestMapping("/notice_board_view")
	public String notice_board_view(HttpServletRequest request, HttpServletResponse response) {
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		
		String postNo = request.getParameter("postNo");
		
		Notice_board_dto dto = dao.getViewPost(postNo);
		
		request.setAttribute("dto", dto);
		
		return "notice/notice_board";
	}
}
*/