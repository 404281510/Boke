package edu.ahpu.boke.util;

import java.util.List;

public class PageBean {
    private int rowCount; // �ܼ�¼��
    private int currentPage; // ��ǰҳ
    private int pageSize; // ÿҳ��¼��
    private int pageCount; // ��ҳ��
    private int offset; // ��ǰҳ������¼��λ��
    private int startPage; // ��ҳ������ť����ʼҳ��
    private int endPage; // ��ҳ������ť�Ľ���ҳ��
    private List<?> contents; // ��ǰҳ������

    /**
     * ���췽��
     * 
     * @param rowCount         �ܼ�¼��
     * @param page             ��ǰҳ��
     * @param pageSize         ÿҳ��¼��
     * @param pageButtonSize   ÿҳ�ķ�ҳ������ť��
     */
    public PageBean(int rowCount, int page, int pageSize, int pageButtonSize) {
        this.rowCount = rowCount;
        this.pageSize = pageSize;

        /**** ��ʼ�����ֶΣ�ע�⴦����ֱ߽��������û������п��ܳ��ֵķǷ����ݡ� ****/
        if (rowCount == 0) { // �ܼ�¼��Ϊ0
            this.pageCount = 1; // ��1ҳ
        } else if (rowCount % pageSize == 0) {// ��30/10=3ҳ
            this.pageCount = rowCount / pageSize;
        } else {// ��32/10=4ҳ
            this.pageCount = rowCount / pageSize + 1;
        }

        // ҳ�������п���û�е�ǰҳ����Ϣ����Ϊ������
        if (page <= 0) {
            this.currentPage = 1;
        } else if (page > pageCount) {// ��ǰҳ��������ҳ��
            this.currentPage = pageCount;
        } else {
            this.currentPage = page;
        }

        // ���㵱ǰҳ������¼��λ��
        this.offset = pageSize * (this.currentPage - 1);

        // �����ҳ���е����һ����ҳ������ť�����30ҳ����ÿҳ10����ҳ��ť����
        if (this.currentPage % pageButtonSize == 0) {
            // ��ʼ��ҳ��ť��Ϊ22
            this.startPage = (this.currentPage / pageButtonSize - 1) * 10 + 2;
            // ������ҳ��ť��Ϊ31
            this.endPage = this.currentPage / 10 * 10 + 1;
        } else { // ����Ĳ������һ����ҳ��ť�����24ҳ��
            // ��ʼ��ҳ��ť��Ϊ21
            this.startPage = (this.currentPage / pageButtonSize * 10) + 1;
            // ������ҳ��ť��Ϊ30
            this.endPage = (this.currentPage / 10 + 1) * 10;
        }

        if (startPage < 1) { // �������ʼ��ҳ��ť��Ϊ0����
            startPage = 1;
        }

        if (pageCount == 0) {// ��ҳ��Ϊ0ʱ
            endPage = 1;
        } else if (endPage > pageCount) {// ������ҳ��ť�ų�����ҳ��
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