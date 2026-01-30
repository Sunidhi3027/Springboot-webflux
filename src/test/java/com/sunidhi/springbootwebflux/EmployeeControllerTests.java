package com.sunidhi.springbootwebflux;

import com.sunidhi.springbootwebflux.controller.EmployeeController;
import com.sunidhi.springbootwebflux.dto.EmployeeDto;
import com.sunidhi.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() {

        // Given - pre-conditions or set up

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Sunidhi");
        employeeDto.setLastName("Suman");
        employeeDto.setEmail("sunidhi@gmail.com");

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

        // When - action or behaviour

        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();


        // Then - verify the result or output

        response.expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void givenEmployeeId_whenGetEmployee_thenReturnEmployeeObject() {

        // Given - pre-conditions or set up

        String employeeId = "123";

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Sunidhi");
        employeeDto.setLastName("Suman");
        employeeDto.setEmail("sunidhi@gmail.com");
        BDDMockito.given(employeeService.getEmployeeById(employeeId))
                .willReturn(Mono.just(employeeDto));

         // When - action  or behaviour

        WebTestClient.ResponseSpec response = webTestClient.get().
                uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // Then - verify the result or output

        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() {
        // This test can be implemented similarly by mocking the service method
        // and verifying the response for a GET request to /api/employees

        // Given - pre-conditions or set up
        List<EmployeeDto> list= new ArrayList<>();
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setFirstName("Sunidhi");
        employeeDto1.setLastName("Suman");
        employeeDto1.setEmail("sunidhi@gmail.com");
        list.add(employeeDto1);

        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto2.setFirstName("John");
        employeeDto2.setLastName("Doe");
        employeeDto2.setEmail("john@gmail.com");
        list.add(employeeDto2);

        Flux<EmployeeDto> employeeFlux = Flux.fromIterable(list);
        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employeeFlux);

        // When - action or behaviour

        WebTestClient.ResponseSpec response = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();


        // Then - verify the result or output
        response.expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        // This test can be implemented similarly by mocking the service method
        // and verifying the response for a PUT request to /api/employees/{id}

        // Given - pre-conditions or set up
        String employeeId = "123";

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Suman");
        employeeDto.setLastName("Sunidhi");
        employeeDto.setEmail("singh@gmail.com");

        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(EmployeeDto.class),
                ArgumentMatchers.any(String.class)))
                .willReturn(Mono.just(employeeDto));

        // When - action or behaviour

        WebTestClient.ResponseSpec response = webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        // Then - Verify the result or output

        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing() {

        // Given - pre-conditions or set up

        String employeeId = "123";
        Mono<Void> voidMono = Mono.empty();
        BDDMockito.given(employeeService.deleteEmployee(employeeId))
                .willReturn(voidMono);

        // When - action or behaviour

        WebTestClient.ResponseSpec response = webTestClient.delete().
                uri("/api/employees/{id}", Collections.singletonMap("id", employeeId))
                .exchange();

        // Then - verify the result or output

        response.expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
