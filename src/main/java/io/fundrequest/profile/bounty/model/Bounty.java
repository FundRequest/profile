package io.fundrequest.profile.bounty.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bounties")
@DiscriminatorColumn(name = "type")
@Data
public class Bounty {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
}
