package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.api.mapper.ShoppingCartMapper;
import com.ecommerce.spring5onlineshop.api.mapper.UserMapper;
import com.ecommerce.spring5onlineshop.api.model.AuthorityDTO;
import com.ecommerce.spring5onlineshop.api.model.ShoppingCartDTO;
import com.ecommerce.spring5onlineshop.api.model.UserDTO;
import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.converters.ShoppingCartToShoppingCartCommand;
import com.ecommerce.spring5onlineshop.converters.UserCommandToUser;
import com.ecommerce.spring5onlineshop.converters.UserToUserCommand;
import com.ecommerce.spring5onlineshop.model.Authority;
import com.ecommerce.spring5onlineshop.model.AuthorityType;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import com.ecommerce.spring5onlineshop.security.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;
    private final ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand;

    public UserServiceImpl(UserRepository userRepository,
                           ShoppingCartRepository shoppingCartRepository,
                           UserCommandToUser userCommandToUser,
                           UserToUserCommand userToUserCommand,
                           ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
        this.shoppingCartToShoppingCartCommand = shoppingCartToShoppingCartCommand;
    }

    @Override
    @Transactional
    public UserCommand saveUserCommand(UserCommand command) {
        User detachedUser;

        if (command.getId() == null) {
            detachedUser = userCommandToUser.convert(command);
        } else {
            // Fill the attributes in the command which are null
            // with the values from the database
            UserCommand completeCommand = fillUserCommand(command);

            detachedUser = userCommandToUser.convert(completeCommand);
        }

        assert detachedUser != null;
        User savedUser = userRepository.save(detachedUser);
        log.debug("Saved user ID: " + savedUser.getId());
        return userToUserCommand.convert(savedUser);
    }

    @Override
    @Transactional
    public UserDTO saveUserDTO(UserDTO userDTO) {
        User detachedUser;

        if (userDTO.getId() == null) {
            // If the user is being registered
            userDTO.setAuthorityDTOSet(Set.of(new AuthorityDTO(AuthorityType.ROLE_USER)));
            userDTO.setPassword(SecurityConfig.pbkdf2PasswordEncoder.encode(userDTO.getPassword()));
            userDTO.setShoppingCartDTO((new ShoppingCartDTO()));
            detachedUser = UserMapper.INSTANCE.userDTOToUser(userDTO);
        } else {
            // Fill the attributes in the command which are null
            // with the values from the database
            UserDTO completeDTO = fillUserDTO(userDTO);

            detachedUser = UserMapper.INSTANCE.userDTOToUser(completeDTO);
        }

        assert detachedUser != null;
        User savedUser = userRepository.save(detachedUser);
        log.debug("Saved user ID: " + savedUser.getId());
        return UserMapper.INSTANCE.userToUserDTO(savedUser);
    }

    @Override
    public User findById(Long l) {

        Optional<User> userOptional = userRepository.findById(l);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new RuntimeException("User not found");

    }

    @Override
    @Transactional
    public UserCommand findCommandByUsername(String username) {

        return userToUserCommand.convert(userRepository.getUserByUsername(username));
    }

    @Override
    @Transactional
    public UserDTO findUserDTOByUsername(String username) {

        return UserMapper.INSTANCE.userToUserDTO(userRepository.getUserByUsername(username));
    }

    @Override
    public UserDTO loginUserByUsername(String username, String password) {
        User user = userRepository.getUserByUsername(username);

        if (user != null && SecurityConfig.pbkdf2PasswordEncoder.matches(password, user.getPassword())) {
            return UserMapper.INSTANCE.userToUserDTO(user);
        } else {
            return new UserDTO();
        }
    }

    @Override
    public void setAuthority(UserCommand command, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String grantedAuthority = authorities.iterator().next().getAuthority();

        command.setAuthorities(Set.of(new Authority(AuthorityType.valueOf(grantedAuthority))));
    }

    @Override
    public Long findShoppingCartIdByUsername(String username) {
        User user = userRepository.getUserByUsername(username);

        Optional<ShoppingCart> shoppingCartOptional =
                shoppingCartRepository.findShoppingCartById(user.getShoppingCart().getId());

        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();

            return shoppingCart.getId();
        }

        throw new RuntimeException("Shopping cart not found");
    }

    @Override
    public void deleteUserById(Long userId) {

        log.debug("Delete user with ID: " + userId);
        userRepository.deleteById(userId);
    }

    /**
     * Used when getting the user data from the
     * front-end. If some attributes of the User command
     * object were not filled in, then fill them with the
     * values from the database.
     *
     * @param command the Command object to be completed
     * @return the filled in Command object
     */
    private UserCommand fillUserCommand(UserCommand command) {
        // Get the user with the command's ID
        Optional<User> userOptional = userRepository.findById(command.getId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // For the fields in the command which are null,
            // set them to the corresponding fields in the user
            if (command.getPassword() == null) {
                command.setPassword(user.getPassword());
            }
            if (command.getUsername() == null) {
                command.setUsername(user.getUsername());
            }
            if (command.getFirstName() == null) {
                command.setFirstName(user.getFirstName());
            }
            if (command.getLastName() == null) {
                command.setLastName(user.getLastName());
            }
            if (command.getAddress() == null) {
                command.setAddress(user.getAddress());
            }
            if (command.getBirthDate() == null) {
                command.setBirthDate(user.getBirthDate());
            }
            if (command.getEmail() == null) {
                command.setEmail(user.getEmail());
            }
            if (command.getGender() == null) {
                command.setGender(user.getGender());
            }
            if (command.getPhone() == null) {
                command.setPhone(user.getPhone());
            }
            if (command.getShoppingCart() == null) {
                command.setShoppingCart(shoppingCartToShoppingCartCommand.convert(user.getShoppingCart()));
            }
        }

        // Return the changed command
        return command;
    }

    /**
     * Used when getting the user data from the
     * front-end. If some attributes of the User DTO
     * were not filled in, then fill them with the
     * values from the database.
     *
     * @param userDTO the DTO to be completed
     * @return the filled in DTO
     */
    private UserDTO fillUserDTO(UserDTO userDTO) {
        // Get the user with the DTOs ID
        Optional<User> userOptional = userRepository.findById(userDTO.getId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // For the fields in the DTO which are null,
            // set them to the corresponding fields in the user
            if (userDTO.getPassword() == null) {
                userDTO.setPassword(user.getPassword());
            }
            if (userDTO.getUsername() == null) {
                userDTO.setUsername(user.getUsername());
            }
            if (userDTO.getFirstName() == null) {
                userDTO.setFirstName(user.getFirstName());
            }
            if (userDTO.getLastName() == null) {
                userDTO.setLastName(user.getLastName());
            }
            if (userDTO.getAddress() == null) {
                userDTO.setAddress(user.getAddress());
            }
            if (userDTO.getBirthDate() == null) {
                userDTO.setBirthDate(user.getBirthDate());
            }
            if (userDTO.getEmail() == null) {
                userDTO.setEmail(user.getEmail());
            }
            if (userDTO.getGender() == null) {
                userDTO.setGender(user.getGender());
            }
            if (userDTO.getPhone() == null) {
                userDTO.setPhone(user.getPhone());
            }
            if (userDTO.getShoppingCartDTO() == null) {
                userDTO.setShoppingCartDTO(ShoppingCartMapper.INSTANCE.shoppingCartToShoppingCartDTO(user.getShoppingCart()));
            }
        }

        // Return the changed DTO
        return userDTO;
    }
}
