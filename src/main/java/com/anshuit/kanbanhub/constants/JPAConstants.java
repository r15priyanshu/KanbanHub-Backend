package com.anshuit.kanbanhub.constants;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class JPAConstants {
	public static final Sort SORT_PROJECT_BY_ID_DESC = Sort.by(Direction.DESC, "projectId");
}
