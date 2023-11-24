package main.mappers;

import main.domain.User;
import main.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO userToDto(User user);
}
