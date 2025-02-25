package ru.astongroup.usermanagement.controller;

import java.util.List;
import java.util.Collection;
/*
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.astongroup.usermanagement.models.Dtos.UserDto;
import ru.astongroup.usermanagement.testUtils.TestUtils;
import ru.astongroup.usermanagement.services.UserService;
import ru.astongroup.usermanagement.utils.mapper.UserMapper;
import ru.astongroup.usermanagement.utils.exceptions.UserNotFoundException;

class UserControllerTest extends TestUtils {

    @Mock
    protected UserService userService;
    @InjectMocks
    protected UserController userController;

    @Test
    public void testRegister_WhenUserCreated_ReturnsCreated() {

        when(userService.create(testUser0)).thenReturn(testUserDto0);

        ResponseEntity<?> response = userController.register(testUser0);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testUserDto0, response.getBody());
        verify(userService, times(1)).create(testUser0);
    }

    @Test
    public void testRegister_WhenUserNotCreated_ReturnsBadRequest() {

        when(userService.create(testUser)).thenReturn(null);

        ResponseEntity<?> response = userController.register(testUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).create(testUser);
    }

    @Test
    public void testGetAllUsers_WhenUsersExist_ReturnsAccepted() {

        var userDtoCollection = getTestUsersCollention().stream().map(UserMapper::mapToDto).toList();
        when(userService.getAll()).thenReturn(userDtoCollection);

        ResponseEntity<Iterable<UserDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((Collection<?>) response.getBody()).containsAll(userDtoCollection));
        verify(userService, times(1)).getAll();
    }

    @Test
    public void testGetAllUsers_WhenNoUsersExist_ReturnsAcceptedWithEmptyList() {
        // Arrange
        when(userService.getAll()).thenReturn(List.of());

        ResponseEntity<Iterable<UserDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(((Collection<?>) response.getBody()).isEmpty());
        verify(userService, times(1)).getAll();
    }

    @Test
    public void testGetUserById_WhenUserExists_ReturnsAccepted() {

        when(userService.getById(testUserDto0.getId())).thenReturn(testUserDto0);

        ResponseEntity<?> response = userController.getUserById(testUserDto0.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUserDto0, response.getBody());
        verify(userService, times(1)).getById(testUserDto0.getId());
    }

    @Test
    public void testGetUserById_WhenUserDoesNotExist_ThrowsException() {

        long userId = 999L;

        when(userService.getById(userId)).thenThrow(new UserNotFoundException("Пользователь не найден"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userController.getUserById(userId);
        });
        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userService, times(1)).getById(userId);
    }

    @Test
    public void testDeleteUserById_WhenUserDeleted_ReturnsOk() {

        long userId = 1L;

        when(userService.deleteById(userId)).thenReturn(true);

        ResponseEntity<?> response = userController.deleteUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUserById_WhenUserNotDeleted_ReturnsBadRequest() {

        long userId = 1L;

        when(userService.deleteById(userId)).thenReturn(false);

        ResponseEntity<?> response = userController.deleteUserById(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Не удалось удалить пользователя", response.getBody());
        verify(userService, times(1)).deleteById(userId);
    }

    @Test
    public void testUpdateUserById_WhenUserUpdated_ReturnsOk() {

        when(userService.updateById(testUser0.getId(), testUser0)).thenReturn(true); // Мокируем успешное обновление

        ResponseEntity<?> response = userController.updateUserById(testUser0.getId(), testUser0);

        assertEquals(HttpStatus.OK, response.getStatusCode()); // Проверяем статус 200 OK
        verify(userService, times(1)).updateById(testUser0.getId(), testUser0); // Проверяем, что метод сервиса вызван 1 раз
    }

    @Test
    public void testUpdateUserById_WhenUserNotUpdated_ReturnsBadRequest() {

        when(userService.updateById(testUser.getId(), testUser)).thenReturn(false);

        ResponseEntity<?> response = userController.updateUserById(testUser.getId(), testUser);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).updateById(testUser.getId(), testUser);
    }
}*/