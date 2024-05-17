package dev.codescreen.controller;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.IOException;

import dev.codescreen.repository.EventPersistRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.catalina.connector.RequestFacade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import dev.codescreen.Util.*;
import dev.codescreen.entity.Persistence;
import dev.codescreen.entity.Request;
import dev.codescreen.exception.TransactionServiceException;
import dev.codescreen.service.AuthorizationLoadService;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ControllerTest {

    @Autowired
    TransactionController controller;

    @InjectMocks
    AuthorizationLoadService service;

    @Mock
    EventPersistRepo eventPersistRepo;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void validateReq() throws IOException, TransactionServiceException {
        HttpServletRequest httpServletReq = Mockito.mock(RequestFacade.class);
        Mockito.when(httpServletReq.getRequestURI()).thenReturn("load");
        Request req = CommonUtil.getReqObjFromFile("LoadReq.json");
        Persistence eventperPersistence = new Persistence();
        controller.validateReq(req, httpServletReq, eventperPersistence);

        assertThatNoException();
    }

    @Test
    public void validateBadReq() throws IOException {

        HttpServletRequest httpServletReq = Mockito.mock(RequestFacade.class);
        Mockito.when(httpServletReq.getRequestURI()).thenReturn("load");
        Request req = CommonUtil.getReqObjFromFile("LoadReq.json");
        req.getTransactionAmount().setCurrency("XYZ");
        Persistence eventperPersistence = new Persistence();
        Exception e = assertThrows(TransactionServiceException.class, () -> {
            controller.validateReq(req, httpServletReq, eventperPersistence);
        });

        assertTrue(e.getMessage().contains("Bad Request"));

    }

    @Test
    public void authorizationTest() throws Exception {
        Persistence eventPersist = new Persistence();

        this.service = Mockito.mock(AuthorizationLoadService.class);
        Mockito.when(service.doAuthorization(Mockito.any(), Mockito.any())).thenReturn(null);

        HttpServletRequest httpServletReq = Mockito.mock(RequestFacade.class);
        Mockito.when(httpServletReq.getRequestURI()).thenReturn("authorization");

        Request req = CommonUtil.getReqObjFromFile("AuthorizationReq.json");

        Mockito.when(eventPersistRepo.save(Mockito.any(Persistence.class))).thenReturn(null);
        controller.authorization("", req, httpServletReq);

        assertThatNoException();

    }

    @Test
    public void loadTest() throws Exception {
        Persistence eventPersist = new Persistence();

        this.service = Mockito.mock(AuthorizationLoadService.class);
        Mockito.when(service.doLoad(Mockito.any(), Mockito.any())).thenReturn(null);

        HttpServletRequest httpServletReq = Mockito.mock(RequestFacade.class);
        Mockito.when(httpServletReq.getRequestURI()).thenReturn("load");

        Request req = CommonUtil.getReqObjFromFile("LoadReq.json");

        Mockito.when(eventPersistRepo.save(Mockito.any(Persistence.class))).thenReturn(null);
        controller.load("", req, httpServletReq);

        assertThatNoException();

    }

}
