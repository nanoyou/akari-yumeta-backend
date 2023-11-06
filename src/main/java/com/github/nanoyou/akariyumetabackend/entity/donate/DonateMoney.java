package com.github.nanoyou.akariyumetabackend.entity.donate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DonateMoney 捐款关系
 * 属于: 捐助
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DonateMoney {
    /**
     * UUID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    /**
     * 捐助金额，以分为单位
     */
    @NotNull
    @Min(1)
    private Long amount;
    /**
     * 创建时间
     */
    @NotNull
    private LocalDateTime createdTime;
    /**
     * 捐助人ID
     */
    @NotNull
    @UUID
    private String donatorID;
    /**
     * 受赠人ID
     */
    @NotNull
    @UUID
    private String doneeID;
    /**
     * 留言祝福
     */
    private String wishes;
}
