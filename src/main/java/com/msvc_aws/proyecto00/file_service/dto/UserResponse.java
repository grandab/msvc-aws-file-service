package com.msvc_aws.proyecto00.file_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {

    Integer id;

    String name;

    String email;

    String phone;
}