package com.github.kop.bbs.module.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 版主申请表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "bbs_user_category_apply")
public class UserCategoryApply {

  /**
   * 主键
   */
  @TableId(value = "id", type = IdType.INPUT)
  private Long id;

  /**
   * 用户id
   */
  @TableField(value = "user_id")
  private Long userId;

  /**
   * 状态 0 待审核 1 审核通过 2 审核不通过
   */
  @TableField(value = "`status`")
  private Integer status;

  /**
   * 版块id
   */
  @TableField(value = "category_id")
  private Long categoryId;

  /**
   * 创建时间
   */
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updateTime;

  /**
   * 更新用户
   */
  @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
  private Long updateUserId;

  /**
   * 逻辑删除标记位
   */
  @TableField(value = "deleted")
  @TableLogic
  private Integer deleted;

  /**
   * 乐观锁
   */
  @TableField(value = "version")
  @Version
  private Long version;
}