package com.study.cate;

import java.util.List;
import java.util.Map;

public interface CateMapper {
	int create(CateVO vo);
	CateVO read(int cateno);
	int update(CateVO vo);
	int delete(int cateno);
	List<CateVO> list_by_categrpno(int categrpno);
	int update_seqno_up(int cateno);
	int update_seqno_down(int cateno);
	int update_visible(CateVO vo);
	int increaseCnt(int cateno);
	int decreaseCnt(int cateno);
	List<Categrp_Cate_join> list_join_by_categrpno(int categrpno);
	//categrp 삭제시 필요
	int catedelete(int categrpno);
	List<Integer> readCate(int categrpno);
	
}
