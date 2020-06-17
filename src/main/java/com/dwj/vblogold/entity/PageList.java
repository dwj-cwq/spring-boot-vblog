package com.dwj.vblogold.entity;

import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public class PageList<T> {
    private int total;
    private List<T> rows;

    public PageList() {
    }

    public PageList(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
