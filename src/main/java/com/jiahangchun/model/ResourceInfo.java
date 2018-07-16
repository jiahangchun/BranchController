package com.jiahangchun.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jiahangchun
 * @Description:
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.model
 */
@Entity
@Table(name="resource_info")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ResourceInfo implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="source_url")
    private String sourceUrl;

    @Column(name="type")
    private Long type;

    @Column(name="category_id")
    private Long categoryId;

    @Column(name="catalog_entity_id")
    private Long catalogEntityId;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

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

}
