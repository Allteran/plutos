package io.allteran.plutos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    private String id;
    private String name;
    private String description;
    //consider that for now it can be only two types of company: AGENCY and WAREHOUSE
    private String type;

}
