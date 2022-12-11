package io.allteran.plutos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tax")
public class Tax {
    @Id
    private String id;
    private String name;
    private Country country;
    private int taxRate;
    private float salaryFrom;
    private float salaryTo;
    private String description;
}
