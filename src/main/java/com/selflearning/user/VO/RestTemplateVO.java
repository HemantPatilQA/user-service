package com.selflearning.user.VO;

import com.selflearning.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestTemplateVO {
    private User user;
    private Department department;
}
