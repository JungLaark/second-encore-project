package com.study.cate;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.study.categrp.CategrpService;
import com.study.categrp.CategrpVO;

@Controller
public class CateCont {

	@Autowired
	@Qualifier("com.study.cate.CateServiceImpl")
	private CateService service;

	@Autowired
	@Qualifier("com.study.categrp.CategrpServiceImpl")
	private CategrpService gservice;

	public CateCont() {
		System.out.println("--> CateCont created.");
	}

	@RequestMapping(value = "/cate/msg", method = RequestMethod.GET)
	public ModelAndView msg(String url) {
		ModelAndView mav = new ModelAndView();

		mav.setViewName(url);

		return mav;
	}

	@RequestMapping(value = "/cate/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/cate/create");

		return mav;
	}

	@RequestMapping(value = "/cate/create", method = RequestMethod.POST)
	public ModelAndView create(CateVO cateVO) {
		ModelAndView mav = new ModelAndView();

		int cnt = this.service.create(cateVO);
		mav.addObject("cnt", cnt);
		mav.addObject("categrpno", cateVO.getCategrpno());
		mav.addObject("url", "/cate/create_msg");

		mav.setViewName("redirect:/cate/msg");

		return mav;
	}

	@RequestMapping(value = "/cate/list", method = RequestMethod.GET)
	public ModelAndView list_join_by_categrpno(int categrpno) {
		ModelAndView mav = new ModelAndView();

		CategrpVO categrpVO = this.gservice.read(categrpno);
		mav.addObject("categrpVO", categrpVO);

		List<Categrp_Cate_join> list = this.service.list_join_by_categrpno(categrpno);
		mav.addObject("list", list);

		mav.setViewName("/cate/list_join_by_categrpno");
		return mav;
	}

	@RequestMapping(value = "/cate/read_update", method = RequestMethod.GET)
	public ModelAndView read_update(int cateno, int categrpno) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/cate/read_update"); // read_update.jsp

		CateVO cateVO = this.service.read(cateno);
		mav.addObject("cateVO", cateVO);

		List<CateVO> list = this.service.list_by_categrpno(categrpno);
		mav.addObject("list", list);

		return mav;
	}

	@RequestMapping(value = "/cate/update", method = RequestMethod.POST)
	public ModelAndView update(CateVO cateVO) {
		ModelAndView mav = new ModelAndView();

		int cnt = this.service.update(cateVO);
		mav.addObject("cnt", cnt);
		mav.addObject("categrpno", cateVO.getCategrpno());
		mav.addObject("url", "/cate/update_msg");

		mav.setViewName("redirect:/cate/msg");

		return mav;
	}

	@RequestMapping(value = "/cate/read_delete", method = RequestMethod.GET)
	public ModelAndView read_delete(int cateno, int categrpno) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/cate/read_delete"); // read_delete.jsp

		CateVO cateVO = this.service.read(cateno);
		mav.addObject("cateVO", cateVO);

		List<CateVO> list = this.service.list_by_categrpno(categrpno);
		mav.addObject("list", list);

		return mav;
	}

	@RequestMapping(value = "/cate/delete", method = RequestMethod.POST)
	public ModelAndView delete(int cateno, int categrpno) {
		ModelAndView mav = new ModelAndView();

		int cnt = this.service.delete(cateno);
		mav.addObject("cnt", cnt);
		mav.addObject("categrpno", categrpno);
		mav.addObject("url", "/cate/delete_msg");

		mav.setViewName("redirect:/cate/msg");

		return mav;
	}

	@RequestMapping(value = "/cate/update_seqno_up", method = RequestMethod.GET)
	public ModelAndView update_seqno_up(int cateno, int categrpno) {
		ModelAndView mav = new ModelAndView();

		int cnt = this.service.update_seqno_up(cateno);

		mav.setViewName("redirect:/cate/list?categrpno=" + categrpno);

		return mav;
	}

	@RequestMapping(value = "/cate/update_seqno_down", method = RequestMethod.GET)
	public ModelAndView update_seqno_down(int cateno, int categrpno) {
		ModelAndView mav = new ModelAndView();

		int cnt = this.service.update_seqno_down(cateno);

		mav.setViewName("redirect:/cate/list?categrpno=" + categrpno); //

		return mav;
	}

	@RequestMapping(value = "/cate/update_visible", method = RequestMethod.GET)
	public ModelAndView update_visible(CateVO cateVO) {
		ModelAndView mav = new ModelAndView();

		int cnt = this.service.update_visible(cateVO);
		mav.addObject("cnt", cnt);

		if (cnt == 1) {
			mav.setViewName("redirect:/cate/list?categrpno=" + cateVO.getCategrpno());
		} else {
			CateVO vo = this.service.read(cateVO.getCateno());
			String name = vo.getName();
			mav.addObject("name", name);
			mav.setViewName("/cate/update_visible_msg"); // /cate/update_visible_msg.jsp
		}
		return mav;
	}

	@RequestMapping(value = "/cate/list_index_left", method = RequestMethod.GET)
	public ModelAndView list_index_left(HttpServletRequest request) {
		// System.out.println("--> list_index() GET called.");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/cate/list_index_left");

		List<CategrpVO> categrp_list = gservice.list_seqno_asc();

		ArrayList<String> name_title_list = new ArrayList<String>();

		StringBuffer url = new StringBuffer();

		for (int index = 0; index < categrp_list.size(); index++) {
			CategrpVO categrpVO = categrp_list.get(index);

			name_title_list.add("<LI class='categrp_name'>" + categrpVO.getName() + "</LI>");

			List<Categrp_Cate_join> cate_list = service.list_join_by_categrpno(categrpVO.getCategrpno());

			for (int j = 0; j < cate_list.size(); j++) {
				Categrp_Cate_join categrp_Cate_join = cate_list.get(j);

				String name = categrp_Cate_join.getName();
				int cnt = categrp_Cate_join.getCnt();

				url.append("<LI class='cate_name'>");
				url.append("  <A href='" + request.getContextPath() + "/contents/list?cateno="
						+ categrp_Cate_join.getCateno() + "'>");
				url.append(name);
				url.append("  </A>");
				url.append("  <span style='font-size: 0.9em; color: #555555;'>(" + cnt + ")</span>");
				url.append("</LI>");

				name_title_list.add(url.toString());

				url.delete(0, url.toString().length());

			}
		}

		mav.addObject("name_title_list", name_title_list);

		return mav;
	}

}
