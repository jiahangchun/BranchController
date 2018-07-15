package com.jiahangchun.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: jiahangchun
 * @Description: 相当于目录的作用
 * @Date: 2018/7/15
 * @Location: BranchController com.jiahangchun.model
 */
@Entity
@Table(name="catalog_entity")
@Data
public class CatalogEntity  implements Serializable {
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

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

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
