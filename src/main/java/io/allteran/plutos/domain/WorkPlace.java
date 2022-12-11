package io.allteran.plutos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "work_place")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlace {
    @Id
    private String id;
    private String companyName;
    private String position;

}
