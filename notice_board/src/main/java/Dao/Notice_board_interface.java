package Dao;

import java.util.ArrayList;
import java.util.Map;

import Dto.Notice_board_dto;

public interface Notice_board_interface {
	public ArrayList<Notice_board_dto>getPostList(Map<String, Object> map);

	public int paging(Map<String, Object> map);
	
	public int notice_insert(Map<String, Object> map);
	
	public Notice_board_dto getViewPost(String postNo);
	
	public int loginCheck (Map<String, Object> map);
	
	public int postInsert (Map<String, Object> map);
	
	public int maxPostNo ();

	public int getMaxHit (String postno);
	
	public int hitUp (Map<String, Object> map);

	public int getCheckId(Map<String, Object> map);

	public int notice_delete(Map<String, Object> map);

	public int getMaxReplyNo();

	public int replySave(Map<String, Object> map);

	public int getPostReNo(Map<String, Object> map);

	public int postUpdate(Map<String, Object> map);

	public ArrayList<Notice_board_dto> getViewReply(String postno);

	public int redelete(Map<String, Object> map);

	public int reupdate(Map<String, Object> map);
}
