package edu.ahpu.boke.util;

import java.util.List;

public class PageBean {
    private int rowCount; // 总记录数
    private int currentPage; // 当前页
    private int pageSize; // 每页记录数
    private int pageCount; // 总页数
    private int offset; // 当前页首条记录的位置
    private int startPage; // 分页导航按钮的起始页号
    private int endPage; // 分页导航按钮的结束页号
    private List<?> contents; // 当前页的内容

    /**
     * 构造方法
     * 
     * @param rowCount         总记录数
     * @param page             当前页号
     * @param pageSize         每页记录数
     * @param pageButtonSize   每页的分页导航按钮数
     */
    public PageBean(int rowCount, int page, int pageSize, int pageButtonSize) {
        this.rowCount = rowCount;
        this.pageSize = pageSize;

        /**** 初始化各字段，注意处理各种边界条件和用户请求中可能出现的非法数据。 ****/
        if (rowCount == 0) { // 总记录数为0
            this.pageCount = 1; // 共1页
        } else if (rowCount % pageSize == 0) {// 如30/10=3页
            this.pageCount = rowCount / pageSize;
        } else {// 如32/10=4页
            this.pageCount = rowCount / pageSize + 1;
        }

        // 页面请求中可能没有当前页的信息（或为负数）
        if (page <= 0) {
            this.currentPage = 1;
        } else if (page > pageCount) {// 当前页超过了总页数
            this.currentPage = pageCount;
        } else {
            this.currentPage = page;
        }

        // 计算当前页首条记录的位置
        this.offset = pageSize * (this.currentPage - 1);

        // 点击了页面中的最后一个分页导航按钮，如第30页（设每页10个分页按钮）。
        if (this.currentPage % pageButtonSize == 0) {
            // 起始分页按钮号为22
            this.startPage = (this.currentPage / pageButtonSize - 1) * 10 + 2;
            // 结束分页按钮号为31
            this.endPage = this.currentPage / 10 * 10 + 1;
        } else { // 点击的不是最后一个分页按钮，如第24页。
            // 起始分页按钮号为21
            this.startPage = (this.currentPage / pageButtonSize * 10) + 1;
            // 结束分页按钮号为30
            this.endPage = (this.currentPage / 10 + 1) * 10;
        }

        if (startPage < 1) { // 计算的起始分页按钮号为0或负数
            startPage = 1;
        }

        if (pageCount == 0) {// 总页数为0时
            endPage = 1;
        } else if (endPage > pageCount) {// 结束分页按钮号超过总页数
            endPage = pageCount;
        }
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getOffset() {
        return offset;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public List<?> getContents() {
        return contents;
    }

    public void setContents(List<?> contents) {
        this.contents = contents;
    }
}