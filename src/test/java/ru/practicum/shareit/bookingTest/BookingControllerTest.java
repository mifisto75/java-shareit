package ru.practicum.shareit.bookingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void preset() throws Exception {
        mockMvc.perform(
                post("/users")
                        .content("{\n" +
                                "    \"name\": \"user\",\n" +
                                "    \"email\": \"user@user.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        mockMvc.perform(
                post("/users")
                        .content("{\n" +
                                "    \"name\": \"user2\",\n" +
                                "    \"email\": \"user2@user.com\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        mockMvc.perform(
                post("/items")
                        .content("{\n" +
                                "    \"name\": \"Дрель\",\n" +
                                "    \"description\": \"Простая дрель\",\n" +
                                "    \"available\": true\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
        ).andExpect(status().isOk());
    }

    @Test
    void positiveAddBooking() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 1,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().isOk());
    }

    @Test
    void negativeAddBooking() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 2,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void positiveResponseToRequest() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 1,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
                patch("/bookings/1?approved=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
        ).andExpect(status().isOk());
    }

    @Test
    void negativeResponseToRequest() throws Exception {
        mockMvc.perform(
                patch("/bookings/1?approved=true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void positiveGetInfoBooking() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 1,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
                get("/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
        ).andExpect(status().isOk());

    }

    @Test
    void negativeGetInfoBooking() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 1,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
                get("/bookings/12")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().is4xxClientError());
    }

    @Test
    void positiveGetAllBookingOneUser() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 1,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
                get("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
        ).andExpect(status().is2xxSuccessful());
    }


    @Test
    void positiveGetAllBookingOneOwner() throws Exception {
        mockMvc.perform(
                post("/bookings")
                        .content("{\n" +
                                "    \"itemId\": 1,\n" +
                                "    \"start\": \"2023-11-11T11:11:01\",\n" +
                                "    \"end\": \"2023-11-11T11:11:02\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 2)
        ).andExpect(status().isOk());

        mockMvc.perform(
                get("/bookings/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 1)
        ).andExpect(status().is2xxSuccessful());
    }


}
