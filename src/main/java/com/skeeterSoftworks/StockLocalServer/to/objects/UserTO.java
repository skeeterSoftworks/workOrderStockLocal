package com.skeeterSoftworks.StockLocalServer.to.objects;

import com.skeeterSoftworks.StockLocalServer.to.enums.ERole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class UserTO {

	private String name;
	private String surname;
	private List<ERole> roles = new ArrayList<>();
	private String qrCode;
	private String email;
	private long id;

	private LocalDateTime createdDate;
}
