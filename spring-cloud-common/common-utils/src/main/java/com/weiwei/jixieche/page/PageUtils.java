package com.weiwei.jixieche.page;

import com.github.pagehelper.PageInfo;

/**
 * @author houji
 * @create 2017-09-08 15:13
 * @desc 适配jquery dataTable的分页数据
 **/
public class PageUtils<T> {

    private PageViewDatatable<T> pv = null;
    private PageInfo<T> pi = null;
    private DataTable dataTable = null;

    public PageUtils(PageInfo<T> pi) {
        this.pi = pi;
        pv = new PageViewDatatable<>();
        pv.setPageNo(pi.getPageNum());
        pv.setPageSize(pi.getPageSize());
        pv.setQueryResult(pi.getList());
        pv.setRowCount((int) pi.getTotal());
        pv.setPageCount(pi.getPages());
        // pv.setStartRowNo();
        //pv.setEndRowNo();
        //pv.setiDisplayLength();
        //pv.setiDisplayStart();
        //pv.setJqueryMode();

    }

    public PageUtils(int draw, PageInfo<T> pi) {
        this.pi = pi;
        dataTable = new DataTable(draw,(int)pi.getTotal(),pi.getList());
//        dataTable.setDraw(draw);
//        dataTable.setData(pi.getList());
//        dataTable.setRecordsTotal((int)pi.getTotal());
//        dataTable.setRecordsFiltered((int)pi.getTotal());
    }

    public PageViewDatatable<T> getPageViewDatatable() {
        return pv;
    }

    public DataTable getDataTable(){
        return dataTable;
    }
}
