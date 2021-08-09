package com.study.cate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.contents.ContentsMapper;

@Service("com.study.cate.CateServiceImpl")
public class CateServiceImpl implements CateService {
	
	@Autowired
	private CateMapper mapper;
	
	@Autowired	
	private ContentsMapper cmapper;

	@Override
	public int create(CateVO vo) {
		// TODO Auto-generated method stub
		return mapper.create(vo);
	}

	@Override
	public CateVO read(int cateno) {
		// TODO Auto-generated method stub
		return mapper.read(cateno);
	}

	@Override
	public int update(CateVO vo) {
		// TODO Auto-generated method stub
		return mapper.update(vo);
	}

	@Override
	public int delete(int cateno) {
		
		cmapper.cdelete(cateno);//삭제할 카테고리의 컨텐트들 먼저 삭제
		return mapper.delete(cateno); //카테고리 삭제
	}

	@Override
	public List<CateVO> list_by_categrpno(int categrpno) {
		// TODO Auto-generated method stub
		return mapper.list_by_categrpno(categrpno);
	}

	@Override
	public int update_seqno_up(int cateno) {
		// TODO Auto-generated method stub
		return mapper.update_seqno_up(cateno);
	}

	@Override
	public int update_seqno_down(int cateno) {
		// TODO Auto-generated method stub
		return mapper.update_seqno_down(cateno);
	}

	@Override
	public int update_visible(CateVO vo) {

        if(vo.getVisible().toUpperCase().equals("Y")) {
        	vo.setVisible("N");
        }else {
        	vo.setVisible("Y");
        }
		return mapper.update_visible(vo);
	}

	@Override
	public int increaseCnt(int cateno) {
		// TODO Auto-generated method stub
		return mapper.increaseCnt(cateno);
	}

	@Override
	public int decreaseCnt(int cateno) {
		// TODO Auto-generated method stub
		return mapper.decreaseCnt(cateno);
	}

	@Override
	public List<Categrp_Cate_join> list_join_by_categrpno(int categrpno) {
		// TODO Auto-generated method stub
		return mapper.list_join_by_categrpno(categrpno);
	}

}
