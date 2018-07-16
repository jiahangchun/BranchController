package com.jiahangchun.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.model
 */
@Entity
@Table(name="exception_info")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ExceptionInfo {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="delete_mark")
    private Long deleteMark;

    @Column(name="gmt_create")
    @CreatedDate
    private Date gmtCreate;

    @Column(name="gmt_delete")
    private Date gmtDelete;

    @Column(name="gmt_update")
    @LastModifiedDate
    private Date gmtUpdate;

    @Column(name="reason")
    private String reason;

    @Column(name="type")
    private long type;

    @Column(name="check_status")
    private Long checkStatus;

    @Column(name="param")
    private String param;
}
