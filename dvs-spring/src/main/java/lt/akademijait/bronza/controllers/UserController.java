package lt.akademijait.bronza.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lt.akademijait.bronza.dto.user.UserAddToGroupCommand;
import lt.akademijait.bronza.dto.user.UserCreateCommand;
import lt.akademijait.bronza.dto.user.UserGetCommand;
import lt.akademijait.bronza.dto.user.UserUpdateCommand;
import lt.akademijait.bronza.services.DocumentService;
import lt.akademijait.bronza.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Api(value = "users")
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private DocumentService documentService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get all users", notes = "Returns all users")
    public List<UserGetCommand> getAllUsers(){
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new user", notes = "Creates new user")
    public void createUser (@ApiParam(value = "User data", required = true)
                                @Valid @RequestBody final UserCreateCommand ucc){
        userService.createNewUser(ucc);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(path = "/{username}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Delete user", notes = "Deletes an existing user")
    public void deleteUserByUsername(@ApiParam(value = "User username", required = true) @PathVariable final String username){
        userService.deleteUser(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(path = "/{username}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update user", notes = "Updates user's data")
    public void updateUserData(
            @ApiParam(value = "Username", required = true) @PathVariable final String username,
            @Valid @ApiParam(value =  "new data", required = true) @RequestBody UserUpdateCommand uuc){
        userService.updateUsersData(username, uuc);
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.GET)
    @ApiOperation(value = "Get user by username", notes = "Returns certain user by username")
    public UserGetCommand getUserByUsername(
            @ApiParam(value = "username", required = true) @Valid @PathVariable final String username){
        return userService.getUserByUsername(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(path = "/groups/{username}", method = RequestMethod.PUT)
    @ApiOperation(value = "Add user to userGroup", notes = "Adds user to new userGroup")
    public void addUserToUserGroup(
            @ApiParam(value = "Username", required = true) @PathVariable final String username,
            @ApiParam(value = "New usergroup", required = true) @RequestBody UserAddToGroupCommand uagc){
        userService.addUserToNewUserGroup(username, uagc);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(path = "/groups/{username}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Remove user from group", notes = "Removes user from a certain user group")
    public void removeUserFromUserGroup(
            @ApiParam(value = "User username", required = true) @PathVariable final String username,
            @ApiParam(value = "UserGroup", required = true) @RequestBody UserAddToGroupCommand uagc){
        userService.removeUserFromUserGroup(username, uagc);
    }

    @RequestMapping(path = "/groups/{usergroup}", method = RequestMethod.GET)
    @ApiOperation(value = "Get users from group", notes = "Returns users who belong to a certain user group")
    public List<UserGetCommand> getUsersByUserGroup(
            @ApiParam(value = "usergroup", required = true) @Valid @PathVariable final String usergroup){
        return userService.getUsersByGroup(usergroup);
    }


}
