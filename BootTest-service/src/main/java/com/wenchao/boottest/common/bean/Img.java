package com.wenchao.boottest.common.bean;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Img {
	@NotNull(message = "img id 不能为null")
    private Long id;

    @NotBlank(message = "img name 不能为空")
    private String name;
}
