package org.unibl.etf.springlearning.models.dto;

import lombok.Data;

@Data
public class GitHubUserEmail {
    String email;
    Boolean primary;
    Boolean verified;
    String visibility;
}
