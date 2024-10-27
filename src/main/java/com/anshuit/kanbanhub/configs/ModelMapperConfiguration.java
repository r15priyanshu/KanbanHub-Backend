package com.anshuit.kanbanhub.configs;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anshuit.kanbanhub.enums.TaskStatusEnum;

@Configuration
public class ModelMapperConfiguration {

	private ModelMapper modelMapper;

	@Bean
	ModelMapper getModelMapper() {
		this.modelMapper = new ModelMapper();
		// REGISTER THE CONVERTERS FIRST
		this.modelMapper.addConverter(new TaskStatusEnumModelMapperConverter(), TaskStatusEnum.class, String.class);
		this.modelMapper.addConverter(new TaskStatusEnumModelMapperReverseConverter(), String.class,
				TaskStatusEnum.class);
		return this.modelMapper;
	}
}

//CONVERTER CLASSES BELOW
class TaskStatusEnumModelMapperConverter implements Converter<TaskStatusEnum, String> {
	@Override
	public String convert(MappingContext<TaskStatusEnum, String> context) {
		return context.getSource() != null ? context.getSource().getTaskStatusName() : null;
	}
}

class TaskStatusEnumModelMapperReverseConverter implements Converter<String, TaskStatusEnum> {
	@Override
	public TaskStatusEnum convert(MappingContext<String, TaskStatusEnum> context) {
		return context.getSource() != null ? TaskStatusEnum.getEnumTypeFromTaskStatusName(context.getSource()) : null;
	}
}