package com.springboot.lolcommunity.board.entity;

import com.springboot.lolcommunity.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Entity
@Table(name="post")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_num", nullable=false)
    private long pno;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;

}
