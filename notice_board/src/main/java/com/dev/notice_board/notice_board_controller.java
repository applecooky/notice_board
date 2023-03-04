package com.dev.notice_board;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import Dao.Notice_board_interface;
import Dto.Notice_board_dto;

@Controller
public class notice_board_controller {

	@Autowired
	public SqlSession sqlSession;
	
	//중복체크
	@RequestMapping("notice_idCheck")
	public void notice_idCheck(HttpServletRequest request, HttpServletResponse response) {
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		String id = request.getParameter("id");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		//로그인 테이블에 중복되는 아이디가 있는지 체크하는 메소드
		int count = dao.getCheckId(map);
		
				response.setContentType("text/html:charset=utf-8");
				try {
					PrintWriter out = response.getWriter();
					if(count == 1) out.print("사용불가");
					else out.print("사용가능");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	// 메인
	@RequestMapping("/notice_board")
	public String notice_board(HttpServletRequest request) {
		Scanner input = new Scanner(System.in);
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		String select = request.getParameter("select"); 
		String search = request.getParameter("search"); 
		String pageNo = request.getParameter("pageNo");
		if(select == null) {
			select = "id";
			search = "";
			pageNo = "1";
		}
		int count = 0;
		try {
			count = Integer.parseInt(pageNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
						
		int start = 1;
		int end = 5;
		
		if(count == 1 || count == 0) {
			start = 1; end = 5;
		}else {
			start = (count*5)-4;
			end = count*5;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", Integer.toString(start));
		map.put("end", Integer.toString(end));
		map.put("select", select);
		map.put("search", search);
		 
		//리스트 화면을 불러오는 메소드
		ArrayList<Notice_board_dto> dtos = dao.getPostList(map);
		int paging = dao.paging(map);
		//게시글을 5단위로 나눠서 페이징 수를 정한다.
		if(paging%5 == 0){
		//	System.out.println(paging+" : 입력하신 수는 5의 배수 입니다");
			paging = (int)Math.floor(paging/5);
		}else{
		//	System.out.println(paging+" : 입력하신 수는 5의 배수가 아닙니다");
			paging = (int)Math.floor(paging/5+1);
		}
		request.setAttribute("dtos", dtos);
		request.setAttribute("Paging", paging);
		request.setAttribute("select", select);
		request.setAttribute("search", search);

		return "notice/notice_board";
	}

//게시글등록 폼
	@RequestMapping("/notice_board_write")
	public String notice_board_write(HttpServletRequest request) {

		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		 
	        // 포맷팅 적용
	        String formatedNow = formatter.format(now);
	        
	        // 등록용
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	        Calendar c1 = Calendar.getInstance();

		 String strToday = sdf.format(c1.getTime());
		 String msg = "로그인을 먼저 해주세요";
		
		 request.setAttribute("msg", msg);
		 request.setAttribute("reg_date2", strToday);
		 request.setAttribute("reg_date", formatedNow);
		return "notice/notice_board_write";
	}

// 게시글 저장
	@RequestMapping("/notice_board_save")
	public String notice_board_save(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirect) {

		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String reg_date = request.getParameter("reg_date");
		String id = request.getParameter("id");
		//게시글 등록시 게시글번호 자동생성하는 메소드
		int maxPostNo = dao.maxPostNo();
			maxPostNo = maxPostNo+1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("reg_date", reg_date);
		map.put("id", id);
		map.put("postNo", maxPostNo);
		//게시글 등록하는 메소드
		int postInsert = dao.postInsert(map);
			if(postInsert==1) {
				redirect.addFlashAttribute("msg","게시글이 등록되었습니다.");
			}
		return "redirect:notice_board";
	}

// 회원가입폼
	@RequestMapping("/notice_board_insertForm")
	public String notice_board_insertForm(HttpServletRequest request, HttpServletResponse response) {

		return "notice/notice_board_insertForm";
	}

// 회원가입
	@RequestMapping("/notice_board_insert")
	public String notice_board_insert(HttpServletRequest request, HttpServletResponse response) {

		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);

		String id = request.getParameter("id");
		String pw = request.getParameter("password");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pw", pw);
		//회원가입 하는 메소드
		int notice_insert = dao.notice_insert(map);

		return "redirect:notice_board";
	}

	// 로그인 하기
	@RequestMapping("/notice_board_login")
	public String notice_board_login(HttpServletRequest request, RedirectAttributes redirect) {
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		HttpSession session = request.getSession();

		String msg = "";
		String id = request.getParameter("id");
		String pw = request.getParameter("password");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("password", pw);
		
		//로그인 테이블에 로그인 하려는 정보와 매칭되는게 있는지 찾는 메소드
		int result = dao.loginCheck(map); 
		
		if(result == 1) {

			session.setAttribute("sessionId", id);
			session.setAttribute("sessionpw", pw);
			session.setMaxInactiveInterval(60 * 60);
			redirect.addFlashAttribute("msg",id+"님 환영합니다.");
			return "redirect:notice_board";
		} else {
			
			redirect.addFlashAttribute("msg","아이디와 비밀번호를 확인해주세요");
			return "redirect:notice_board_loginForm";
		}
	}

	// 로그인 폼
	@RequestMapping("/notice_board_loginForm")
	public String notice_board_loginForm(HttpServletRequest request, HttpServletResponse response) {
		
		
		return "notice/notice_board_loginForm";
	}

	// 로그아웃
	@RequestMapping("/notice_board_logout")
	public String notice_board_delete(HttpServletRequest request, RedirectAttributes redirect) {

		HttpSession session = request.getSession();

		session.invalidate();
		redirect.addFlashAttribute("msg","로그아웃되셨습니다.");
		return "redirect:notice_board";
	}

	// 뷰
	@RequestMapping("/notice_board_view")
	public String notice_board_view(@RequestParam("postno") String postno , Model model, HttpServletRequest request, RedirectAttributes redirect) {
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		//게시글 번호를 담아서 뷰로 넘어온다.
		model.addAttribute("postno", postno);
		//게시글 번호에 해당하는 조회수를 조회하는 메소드
		int maxHit = dao.getMaxHit(postno);
			maxHit = maxHit+1;
		String Hit = Integer.toString(maxHit);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hit", Hit);
		map.put("postno", postno);
		//조회한 메소드에 +1해서 보더 테이블에 수정하는 메소드 
		int hit = dao.hitUp(map);
		//보더 테이블에서 선택한 게시글의 정보를 불러오는 메소드
		Notice_board_dto dto = dao.getViewPost(postno);
		//리플라이 테이블에서 선택한 게시글에 댓글을 불러오는 메소드
		ArrayList<Notice_board_dto> redto = dao.getViewReply(postno);
		
		request.setAttribute("dto", dto);
		request.setAttribute("redto", redto);
		request.setAttribute("model", model);
		return "notice/notice_board_view";
	}
	//게시글 수정 폼
	@RequestMapping("notice_board_updateForm")
	public String notice_board_updateForm(HttpServletRequest request) {
		
		String postno = request.getParameter("postNo");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
		 
	        // 포맷팅 적용
	        String formatedNow = formatter.format(now);
	        
	        // 등록용
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	        Calendar c1 = Calendar.getInstance();

		 String strToday = sdf.format(c1.getTime());
		 String msg = "로그인을 먼저 해주세요";
		
		 request.setAttribute("postNo", postno);
		 request.setAttribute("title", title);
		 request.setAttribute("content", content);
		 request.setAttribute("msg", msg);
		 request.setAttribute("reg_date2", strToday);
		 request.setAttribute("reg_date", formatedNow);
		 
		return "notice/notice_board_updateForm";
	}
	
//게시글 삭제
	@RequestMapping("notice_delete")
	public String notice_delete(HttpServletRequest request,RedirectAttributes redirect) {
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		String postno = request.getParameter("postNo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postno", postno);
		
		//선택한 게시글을 삭제하는 메소드
		int notice_delete = dao.notice_delete(map); 
		redirect.addFlashAttribute("msg", "삭제 되셨습니다.");
		
		return "redirect:notice_board";
	}
//게시글 수정 저장
	@RequestMapping("notice_board_update")
	public String notice_board_update(HttpServletRequest request, RedirectAttributes redirect) {
Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		
		String postno = request.getParameter("postNo");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String reg_date = request.getParameter("reg_date");
		String id = request.getParameter("id");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("reg_date", reg_date);
		map.put("id", id);
		map.put("postNo", postno);
		//수정한 게시글을 등록하는 메소드1
		int postUpdate = dao.postUpdate(map);
			if(postUpdate == 1) {
				redirect.addFlashAttribute("msg","수정되었습니다.");
			}
		
		return "redirect:notice_board";
	}

	
//댓글 등록
	@RequestMapping("notice_reply_save")
	public void notice_replay_save(HttpServletRequest request, RedirectAttributes redirect, HttpServletResponse response) {
		
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);

		String postno = request.getParameter("postNo");
		String content = request.getParameter("content");
		HttpSession session = request.getSession();
		//댓글번호를 자동 생성하는 메소드
		 int replyno = dao.getMaxReplyNo();
		 	replyno = replyno+1;
		 String id = (String)session.getAttribute("sessionId");
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("replyno", replyno);
		 map.put("content", content);
		 map.put("id", id);
		 map.put("postno", postno);
		 //선택한 게시글의 댓글의 번호를 자동 생성하는 메소드 ex)첫번째 게시글의 첫번째 댓글
		 int postreno = dao.getPostReNo(map);
		 	if(postreno == 0) {
		 		postreno = 1;
		 	} else {
		 	postreno = postreno+1;
		 	}
		 map.put("postreno", postreno);
		 int replySave = dao.replySave(map);
			response.setContentType("text/html:charset=utf-8");
			try {
				PrintWriter out = response.getWriter();
				if(replySave == 1) {
					//return "redirect:notice_board_view?postno="+postno;
					out.print(postno);
				}else {
					out.print("등록되지 않았습니다.");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
//댓글삭제
	@RequestMapping("notice_board_redelete")
	public String notice_board_redelete(HttpServletRequest request, RedirectAttributes redirect) {
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		String postReNo = request.getParameter("postReNo");
		String postNo = request.getParameter("postNo");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postReNo", postReNo);
		map.put("postNo", postNo);
		//선택한 게시글의 댓글을 삭제하는 메소드
		int redelete = dao.redelete(map);
		return "redirect:notice_board_view?postno="+postNo;
	}
//댓글 수정
	@RequestMapping("notice_board_reupdate")
	public String notice_board_reupadte(HttpServletRequest request, RedirectAttributes redirect) {
		Notice_board_interface dao = sqlSession.getMapper(Notice_board_interface.class);
		String postReNo = request.getParameter("postReNo");
		String postNo = request.getParameter("postNo");
		String content = request.getParameter("content");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postReNo", postReNo);
		map.put("postNo", postNo);
		map.put("content", content);
		
		//선택한 게시글의 댓글을 수정하는 메소드
		int reupdate = dao.reupdate(map);
		return "redirect:notice_board_view?postno="+postNo;
	}
}
