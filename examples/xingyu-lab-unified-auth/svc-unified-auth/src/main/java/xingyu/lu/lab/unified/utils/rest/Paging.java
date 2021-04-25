package xingyu.lu.lab.unified.utils.rest;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author xingyu.lu
 * @create 2018-03-22 09:33
 **/
public class Paging<T> implements Serializable {

    public static final Integer PAGE_DEFAULT_SIZE = 20;
    private static final long serialVersionUID = -5315029048023321610L;
    private List<T> data;
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 页面大小
     */

    private Integer pageSize;
    /**
     * 总页数
     */

    private Integer totalPages;
    /**
     * 总条数
     */
    private Long total;

    public static <T> Paging<T> obtainPaging(PageInfo<T> page, List<T> data) {

        Paging<T> paging = new Paging<>();
        paging.setData(data);
        paging.setPage(page.getPageNum())
                .setPageSize(page.getPageSize())
                .setTotalPages(page.getPages())
                .setTotal(page.getTotal());
        return paging;
    }


    public Integer getPage() {
        return page;
    }

    public Paging<T> setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Paging<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Paging<T> setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public Long getTotal() {
        return total;
    }

    public Paging<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public Paging<T> setData(List<T> data) {
        this.data = data;
        return this;
    }
}
