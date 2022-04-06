package com.weiwei.jixieche.page;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端 DataTable 插件数据模型
 * 
 * 资料：http://datatables.club/manual/server-side.html#returndata
 * 
 * @author YellowSponge 2017年4月17日 下午2:42:52
 */
@Data
@EqualsAndHashCode
public class DataTable {

	/**
	 * 必要。 Datatables发送的draw是多少那么服务器就返回多少。 这里注意，作者出于安全的考虑，强烈要求把这个转换为整形，即数字后再返回，
	 * 而不是纯粹的接受然后返回，这是 为了防止跨站脚本（XSS）攻击。
	 */
	private int draw;
	
	/**
	 * 必要。 即没有过滤的记录数（数据库里总共记录数）
	 */
	private int recordsTotal = 0;
	/**
	 * 必要。 过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
	 */
	private int recordsFiltered = 0;

	/**
	 * 必要。表中中需要显示的数据。这是一个对象数组，也可以只是数组，区别在于 纯数组前台就不需要用 columns绑定数据，会自动按照顺序去显示
	 * ，而对象数组则需要使用 columns绑定数据才能正常显示。
	 */
	private List data = new ArrayList();// 数据
	/**
	 * 可选。你可以定义一个错误来描述服务器出了问题后的友好提示
	 */
	private String error = "";

	public DataTable() {
		super();
	}
	
	public DataTable(int draw, int total, List data) {
		super();
		this.draw = draw;
		this.recordsTotal = total;
		this.recordsFiltered = total;
		this.data = data;
	}

	public DataTable(int draw, int recordsTotal, List data, String error) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = data.size();
		this.data = data;
		this.error = error;
	}
}
