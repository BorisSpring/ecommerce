package main.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReviewDTO {

	private int id;
	private String userEmail;
	private String reviewContent;
	private LocalDateTime createdAt;
	private String productTitle;;


}
