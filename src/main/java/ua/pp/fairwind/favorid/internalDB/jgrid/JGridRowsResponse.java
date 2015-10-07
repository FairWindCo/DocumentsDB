package ua.pp.fairwind.favorid.internalDB.jgrid;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Сергей on 07.10.2015.
 */
public class JGridRowsResponse<T> {
    final Long records;
    final Integer page;
    final Integer total;
    final List<T> rows;

    public JGridRowsResponse(Page<T> page) {
        this.records=page.getTotalElements();
        this.page=page.getNumber();
        this.total=page.getTotalPages();
        this.rows=page.getContent();
    }

    public JGridRowsResponse(List<T> list) {
        if(list!=null) {
            this.records = (long)list.size();
        } else {
            this.records = 0L;
        }
        this.page = null;
        this.total = null;
        this.rows=list;
    }

    public Long getRecords() {
        return records;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotal() {
        return total;
    }

    public List<T> getRows() {
        return rows;
    }
}
