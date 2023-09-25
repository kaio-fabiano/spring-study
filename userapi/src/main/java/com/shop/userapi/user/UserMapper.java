package com.shop.userapi.user;

import com.shop.userapi.user.dto.CreateUserDTO;
import com.shop.userapi.user.dto.UpdateUserDTO;
import com.shop.userapi.user.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper {

  UserDTO userToUserDto(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User createUserDtoToUser(CreateUserDTO createUserDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User updateUserDtoToUser(UpdateUserDTO updateUserDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User mergeUser(User user, @MappingTarget User userToUpdate);
}
