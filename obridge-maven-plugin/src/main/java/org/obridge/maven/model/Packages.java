package org.obridge.maven.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Packages {

    String entityObjects;
    String converterObjects;
    String procedureContextObjects;
    String packageObjects;

}
