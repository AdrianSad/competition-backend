package pl.adrian.competitionbackend.user.controller

import pl.adrian.competitionbackend.user.model.dto.CreateUserDto
import pl.adrian.competitionbackend.user.model.dto.LoginUserDto
import pl.adrian.competitionbackend.user.model.dto.UserDto
import pl.adrian.competitionbackend.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/user")
@Validated
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun authenticateUser(@RequestBody @Valid loginUserDto: LoginUserDto): UserDto =
            userService.signIn(loginUserDto)

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid createUserDto: CreateUserDto): UserDto =
            UserDto.fromUser(userService.signUp(createUserDto))

}