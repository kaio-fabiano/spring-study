package com.shop.userapi.user;

import com.shop.userapi.user.dtos.CreateUserDTO;
import com.shop.userapi.user.dtos.UpdateUserDTO;
import com.shop.userapi.user.dtos.UserDTO;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper {

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  UserDTO userToUserDto(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  List<UserDTO> usersToUsersDto(List<User> users);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User createUserDtoToUser(CreateUserDTO createUserDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User updateUserDtoToUser(UpdateUserDTO updateUserDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  User mergeUser(User user, @MappingTarget User userToUpdate);
}
