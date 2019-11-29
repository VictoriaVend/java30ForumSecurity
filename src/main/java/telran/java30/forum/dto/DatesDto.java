package telran.java30.forum.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(pattern = "yyyy-MM-dd", locale = "en_GB")
public class DatesDto {

	LocalDate from;
	LocalDate to;

}
