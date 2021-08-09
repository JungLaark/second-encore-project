package com.study.categrp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.study.cate.CateMapper;
import com.study.cate.CateService;
import com.study.contents.ContentsMapper;

@Service("com.study.categrp.CategrpServiceImpl")
public class CategrpServiceImpl implements CategrpService {
	@Autowired
	private CategrpMapper mapper;
	
	@Autowired
	private CateMapper catemapper;
	
	@Autowired
	private ContentsMapper contentmapper;
	
	@Autowired
	@Qualifier("com.study.cate.CateServiceImpl")
	private CateService service;
	
	@Override
	public int create(CategrpVO vo) {
		// TODO Auto-generated method stub
		return mapper.create(vo);
	}

	@Override
	public List<CategrpVO> list_seqno_asc() {
		// TODO Auto-generated method stub
		return mapper.list_seqno_asc();
	}

	@Override
	public CategrpVO read(int categrpno) {
		// TODO Auto-generated method stub
		return mapper.read(categrpno);
	}

	@Override
	public int update(CategrpVO vo) {
		// TODO Auto-generated method stub
		return mapper.update(vo);
	}

	@Override
	public int delete(int categrpno) {
		//삭제될 categrpno에 대한 cateno read
		List<Integer> list = catemapper.readCate(categrpno);
		List<Integer> list2 = null;
		//read한  cateno에 대한 contentsno read
		if(list !=null && list.size()>0) {
			list2 = contentmapper.readContents(list);			
		}
		if(list2 !=null && list2.size()>0) {
			contentmapper.deletecatelist(list2);
		}
		//cate삭제
		catemapper.catedelete(categrpno);
		//categrp 삭제
		return mapper.delete(categrpno);
		
	}

	@Override
	public int update_seqno_up(int categrpno) {
		// TODO Auto-generated method stub
		return mapper.update_seqno_up(categrpno);
	}

	@Override
	public int update_seqno_down(int categrpno) {
		// TODO Auto-generated method stub
		return mapper.update_seqno_down(categrpno);
	}

	@Override
	public int update_visible(Map map) {
		String visible = (String)map.get("visible");
		
		if(visible.toUpperCase().equals("Y")) {
			map.put("visible", "N");
		}else {
			map.put("visible", "Y");
		}
		
		return mapper.update_visible(map);
	}

}
