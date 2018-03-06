package io.fundrequest.profile.survey.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "survey")
@Getter
public class Survey {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    private SurveyStatus status;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    Survey() {
    }

    @Builder
    Survey(String userId, SurveyStatus status, LocalDateTime completionDate) {
        this.userId = userId;
        this.status = status;
        this.completionDate = completionDate;
    }
}
