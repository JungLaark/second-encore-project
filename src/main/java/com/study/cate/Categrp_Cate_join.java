package com.study.cate;

import lombok.Data;

@Data
public class Categrp_Cate_join {
	/** categrp table **/
	private int 	r_categrpno;
	private String  r_name;
	/** cate table **/
	private int 	cateno;
	private int 	categrpno;
	private String  name;
	private int		seqno;
	private String 	visible;
	private String 	rdate;
	private int		cnt;
}
