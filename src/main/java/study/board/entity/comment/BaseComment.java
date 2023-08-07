package study.board.entity.comment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.board.entity.DateEntity;
import study.board.entity.User;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseComment extends DateEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Boolean isRemoved;

    public BaseComment(String content, User user) {
        this.content = content;
        this.user = user;
        this.isRemoved = false;
    }

    public void remove() {
        isRemoved = true;
    }

}
