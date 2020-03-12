package com.example.api.services;

import com.example.api.domain.RegisterUser;
import com.example.api.domain.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(MockitoJUnitRunner.class)
public class RegistryServiceTest {

    @Mock
    private RegistryImplementation RegistryImplementation;
    private Response Response = new Response();
    private RegisterUser registerUser = new RegisterUser();



    @Test
    public void create() {
        Mockito.when(RegistryImplementation.create(Mockito.any(RegisterUser.class))).thenReturn(Response);
        Object data = RegistryImplementation.create(registerUser);
        assertNotNull(data);
        Assert.assertEquals(Response, data);
        verify(RegistryImplementation).create(registerUser);
        verifyNoMoreInteractions(RegistryImplementation);

    }

}